import com.example.ourmobile.CelestialElysiaInterpreter
import com.example.ourmobile.EqualsToken
import com.example.ourmobile.ExpressionToken
import com.example.ourmobile.IToken
import java.util.HashMap

//TODO: сделать поддержку нормальных массивов и выражений со строками
class Expression {
    public fun toReversePolishNotation(expression: String, variables: Map<String, Any>, program:CelestialElysiaInterpreter): String {
        val stack = mutableListOf<String>()
        val output = mutableListOf<String>()
        val operators = setOf("+", "-", "*", "/")

        val arrayRegex = Regex("^\\w+\\[.+]$")
        val arrayNameRegex = Regex("^\\w+(?=\\[)")
        val arrayExpressionRegex = Regex("(?<=(\\[)).+(?=]$)")

        val functionRegex = Regex("^\\w+\\(.+\\)$")
        val functionNameRegex = Regex("^\\w+(?=\\()")
        val arrayArgumentsRegex = Regex("(?<=(\\w\\()).+(?=\\)$)")

        expression.split(" ").forEach { token ->
            if (token in operators) {
                while (stack.isNotEmpty() && stack.last() in operators && precedence(stack.last()) >= precedence(token)) {
                    output.add(stack.removeLast())
                }
                stack.add(token)
            } else if (token == "(") {
                stack.add(token)
            } else if (token == ")") {
                while (stack.isNotEmpty() && stack.last() != "(") {
                    output.add(stack.removeLast())
                }
                stack.removeLast()
            } else {
                if(token.toDoubleOrNull()!=null){
                    output.add(token)
                }else if(token.matches(arrayRegex)){
                    val expression = Expression()
                    var arrayIndex = expression.evaluateReversePolishNotation(expression.toReversePolishNotation(arrayExpressionRegex.find(token)!!.value,variables, program)).toInt()
                    var arrayName = arrayNameRegex.find(token)!!.value
                    var arrayToken = arrayName+"["+arrayIndex+"]"

                    /*
                    val value = variables[arrayToken] ?: throw IllegalArgumentException("Unknown variable: $token")
                    output.add(value.toString())
                     */
                    val array = variables.get(arrayName)

                    if(array is IntArray){
                        val typedArray = array as IntArray
                        val value = typedArray[arrayIndex]
                        output.add(value.toString())
                    }
                    else{
                        val typedArray = array as DoubleArray
                        val value = typedArray[arrayIndex]
                        output.add(value.toString())
                    }
                }else if(token.matches(functionRegex)){
                    var functionName = functionNameRegex.find(token)!!.value
                    var functionProgram = program.functionHashMap[functionName]
                    var functionArguments = arrayArgumentsRegex.find(token)!!.value.split("|")

                    for(n in functionArguments){
                        val nameAndValue = n.split(":")
                        val name = nameAndValue[0]
                        var value = nameAndValue[1]

                        val expressionToken = EqualsToken()

                        if(functionProgram!!.varHashMap[name]!!::class.java.simpleName=="Integer" ||
                            functionProgram!!.varHashMap[name]!!::class.java.simpleName=="Double") {
                            expressionToken.command("<expression:" + value + ">", program)
                            functionProgram!!.varHashMap.put(name, program.stack.removeFirst())
                        }
                        else{
                            functionProgram!!.varHashMap.put(name,program.varHashMap[value]!!)
                        }
                    }
                    functionProgram!!.interprete()
                    output.add(functionProgram.returnValue.toString())
                }else{
                    val value = variables[token] ?: throw IllegalArgumentException("Unknown variable: $token")
                    output.add(value.toString())
                }

            }
        }

        while (stack.isNotEmpty()) {
            output.add(stack.removeLast())
        }

        return output.joinToString(" ")
    }

    public fun evaluateReversePolishNotation(rpn: String): Double {
        val stack = mutableListOf<Double>()

        rpn.split(" ").forEach { token ->
            when (token) {
                "+", "-", "*", "/" -> {
                    val b = stack.removeLast()
                    val a = stack.removeLast()
                    val result = when (token) {
                        "+" -> a + b
                        "-" -> a - b
                        "*" -> a * b
                        "/" -> a / b
                        else -> throw IllegalArgumentException("Unknown operator: $token")
                    }
                    stack.add(result)
                }
                else -> {
                    val operand = token.toDoubleOrNull() ?: throw IllegalArgumentException("Invalid token: $token")
                    stack.add(operand)
                }
            }
        }

        if (stack.size != 1) {
            throw IllegalArgumentException("Invalid RPN expression: $rpn")
        }

        return stack[0].toDouble()
    }

    private fun precedence(operator: String): Int {
        return when (operator) {
            "+", "-" -> 1
            "*", "/" -> 2
            else -> 0
        }
    }
}
fun boolExpression(expression1: String, expression2: String, operand: String,
                   program: CelestialElysiaInterpreter):Boolean{
    var tokenRegex = Regex("<\\w+")
    var token1Name = tokenRegex.find(expression1)!!.value
    var token1Type = program.tokenHashMap.get(token1Name)
    var token1Object = token1Type?.java?.newInstance() as? IToken ?: throw IllegalArgumentException("Invalid token type")
    token1Object.command(expression1,program)

    var token2Name = tokenRegex.find(expression2)!!.value
    var token2Type = program.tokenHashMap.get(token2Name)
    var token2Object = token2Type?.java?.newInstance() as? IToken ?: throw IllegalArgumentException("Invalid token type")
    token2Object.command(expression2,program)

    var value2 = program.stack.removeFirst()
    var value1 = program.stack.removeFirst()
    var boolValue: Boolean = true
    when (operand) {
        "==" -> boolValue = value1 == value2
        "!=" -> boolValue = value1 != value2
        ">" -> boolValue = value1 > value2
        "<" -> boolValue = value1 < value2
        ">=" -> boolValue = value1 >= value2
        "<=" -> boolValue = value1 <= value2
    }
    return boolValue
}

