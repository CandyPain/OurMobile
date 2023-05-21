import com.example.ourmobile.ExpressionToken
import java.util.HashMap

class Expression {
    public fun toReversePolishNotation(expression: String, variables: Map<String, Any>): String {
        val stack = mutableListOf<String>()
        val output = mutableListOf<String>()
        val operators = setOf("+", "-", "*", "/")
        val arrayRegex = Regex("^\\w+\\[.+]$")
        val arrayNameRegex = Regex("^\\w+(?=\\[)")
        val arrayExpressionRegex = Regex("(?<=(\\[)).+(?=]$)")

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
                    var arrayIndex = expression.evaluateReversePolishNotation(expression.toReversePolishNotation(arrayExpressionRegex.find(token)!!.value,variables)).toInt().toString()
                    var arrayName = arrayNameRegex.find(token)!!.value
                    var arrayToken = arrayName+"["+arrayIndex+"]"
                    val value = variables[arrayToken] ?: throw IllegalArgumentException("Unknown variable: $token")
                    output.add(value.toString())
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

