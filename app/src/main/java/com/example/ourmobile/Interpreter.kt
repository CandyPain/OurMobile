import com.example.ourmobile.CelestialElysiaInterpreter
import com.example.ourmobile.EqualsToken
import com.example.ourmobile.ExpressionToken
import com.example.ourmobile.StringExpressionToken
import com.example.ourmobile.IToken
import java.util.HashMap

class Expression {
    public fun toReversePolishNotation(expressionString: String, variables: Map<String, Any>,
                                       program:CelestialElysiaInterpreter): String {
        val stack = mutableListOf<String>()
        val output = mutableListOf<String>()
        val operators = setOf("+", "-", "*", "/")

        val expression = expressionString.replace("\\s".toRegex(), "")

        val tokenRegex = Regex("(((?<=[+\\-\\/*]))-\\d+(\\.\\d+)?|[A-Za-z]+\\w*(\\[.+\\])|"+
                "[+\\-\\/*)(]|\\w+<[\\w:+\\-\\/*\\|\\.]+>|[a-zA-Z]\\w*\\.[a-zA-Z]\\w*\\[.+\\]|"+
                "[a-zA-Z]\\w*\\.[a-zA-Z]\\w*|\\w+(\\.\\d+)?)")

        val arrayRegex = Regex("^\\w+\\[.+]$")

        val functionRegex = Regex("^\\w+<.+>$")

        val structRegex = Regex("^\\w+\\.\\w+(\\[.+\\])?$")

        val matches = tokenRegex.findAll(expression)

        for(match in matches) {
            val token = match.value
            if (token in operators) {
                while (stack.isNotEmpty() && stack.last() in operators &&
                    precedence(stack.last()) >= precedence(token)) {
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
                    output.add(processArray(token, variables, program))
                }else if(token.matches(functionRegex)){
                    output.add(processFunction(token, variables, program))
                }else if(token.matches(structRegex)){
                    output.add(processStruct(token, variables, program))
                }
                else{
                    val value = variables[token] ?: "0"
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
    val tokenRegex = Regex("<\\w+")
    val token1Name = tokenRegex.find(expression1)!!.value
    val token1Type = program.tokenHashMap.get(token1Name)
    val token1Object = token1Type?.java?.newInstance() as? IToken ?: throw IllegalArgumentException("Invalid token type")
    token1Object.command(expression1,program)

    val token2Name = tokenRegex.find(expression2)!!.value
    val token2Type = program.tokenHashMap.get(token2Name)
    val token2Object = token2Type?.java?.newInstance() as? IToken ?: throw IllegalArgumentException("Invalid token type")
    token2Object.command(expression2,program)

    val value2 = program.stack.removeFirst()
    val value1 = program.stack.removeFirst()
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

fun processArray(token: String, variables: Map<String, Any>, program:CelestialElysiaInterpreter)
: String{
    val arrayNameRegex = Regex("^\\w+(?=\\[)")
    val arrayExpressionRegex = Regex("(?<=(\\[)).+(?=]$)")

    val expression = Expression()

    val arrayIndex = expression.evaluateReversePolishNotation(expression.toReversePolishNotation(
        arrayExpressionRegex.find(token)!!.value,variables, program)).toInt()
    val arrayName = arrayNameRegex.find(token)!!.value

    val array = variables.get(arrayName) ?: IntArray(arrayIndex) {0}

    if(array is IntArray){
        val typedArray = array as IntArray
        val value = typedArray[arrayIndex]
        return value.toString()
    }
    else if(array is DoubleArray){
        val typedArray = array as DoubleArray
        return typedArray[arrayIndex].toString()
    }
    else{
        val array = program.varHashMap.get(arrayName) as Array<String>
        return array[arrayIndex]
    }
}

fun processFunction(token: String, variables: Map<String, Any>, program:CelestialElysiaInterpreter)
: String{
    val functionNameRegex = Regex("^\\w+(?=<)")
    val functionArgumentsRegex = Regex("(?<=(\\w<)).+(?=>$)")

    val functionName = functionNameRegex.find(token)!!.value
    val functionProgram = program.functionHashMap[functionName]
    val functionArguments = functionArgumentsRegex.find(token)!!.value.split("|")

    for(n in functionArguments){
        val nameAndValue = n.split(":")
        val name = nameAndValue[0]
        val value = nameAndValue[1]

        val expressionToken = ExpressionToken()
        val stringExpressionToken = StringExpressionToken()

        if(functionProgram!!.varHashMap[name]!!::class.java.simpleName=="Integer") {
            expressionToken.command("<expression:" + value + ">", program)
            functionProgram.varHashMap.put(name, program.stack.removeFirst().toInt())
        }
        else if(functionProgram.varHashMap[name]!!::class.java.simpleName=="Double"){
            expressionToken.command("<expression:" + value + ">", program)
            functionProgram.varHashMap.put(name, program.stack.removeFirst())
        }
        else if(functionProgram.varHashMap[name]!!::class.java.simpleName=="String"){
            stringExpressionToken.command("<stringexpression:" + value + ">", program)
            functionProgram.varHashMap.put(name, program.stringStack.removeFirst())
        }
        else{
            functionProgram.varHashMap.put(name,program.varHashMap[value]!!)
        }
    }
    functionProgram!!.interprete()
    return functionProgram.returnValue.toString()
}
fun processStruct(token: String, variables: Map<String, Any>, program:CelestialElysiaInterpreter)
: String{
    val arrayExpressionRegex = Regex("(?<=(\\[)).+(?=]$)")

    val structNameRegex = Regex("^\\w+(?=\\.)")
    val structVarRegex = Regex("(?<=(\\.))\\w+")
    val structArrayRegex = Regex("^\\w+\\.\\w+\\[.+]$")

    if(token.matches(structArrayRegex)){
        val structName = structNameRegex.find(token)!!.value
        val structVarName = structVarRegex.find(token)!!.value

        val structHashMap = variables.get(structName) as HashMap<String, Any>
        val array = structHashMap.get(structVarName)

        val expression = Expression()
        val arrayIndex = expression.evaluateReversePolishNotation(expression.toReversePolishNotation
            (arrayExpressionRegex.find(token)!!.value,variables, program)).toInt()

        if(array is IntArray){
            val typedArray = array as IntArray
            return typedArray[arrayIndex].toString()
        }
        else if(array is DoubleArray){
            val typedArray = array as DoubleArray
            return typedArray[arrayIndex].toString()
        }
        else{
            val typedArray = array as Array<String>
            return typedArray[arrayIndex].toString()
        }
    }else {
        val structName = structNameRegex.find(token)!!.value
        val structVarName = structVarRegex.find(token)!!.value

        val structHashMap = variables.get(structName) as HashMap<String, Any>
        return structHashMap[structVarName].toString()
    }
}

