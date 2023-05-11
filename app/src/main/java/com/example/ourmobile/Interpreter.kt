import java.util.HashMap

class Expression {
    public fun toReversePolishNotation(expression: String, variables: Map<String, Int>): String {
        val stack = mutableListOf<String>()
        val output = mutableListOf<String>()
        val operators = setOf("+", "-", "*", "/")

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
                if(token.toIntOrNull()!=null){
                    output.add(token)
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

    public fun evaluateReversePolishNotation(rpn: String): Int {
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

        return stack[0].toInt()
    }

    private fun precedence(operator: String): Int {
        return when (operator) {
            "+", "-" -> 1
            "*", "/" -> 2
            else -> 0
        }
    }
}

class Interpreter(private val commandList: MutableList<String>){
    val hashMap = HashMap<String, Int>()
    val outputList = mutableListOf<Int?>()
    val expressionHandler = Expression();

    public fun interprete(): MutableList<Int?>{
        val varRegex = Regex("^var \\w+")
        val expressionRegex = Regex("^\\w+ = .+")
        for(command in commandList){
            if(varRegex.matches(command)){
                hashMap.put(command.substring(4), 0)
            }
            else if(expressionRegex.matches(command)){
                val parts = command.split("=")
                hashMap.put(parts[0].substring(0, parts[0].length - 1), expressionHandler.evaluateReversePolishNotation(expressionHandler.toReversePolishNotation(parts[1].substring(1), hashMap)))
            }
            else{
                var cout: Int?
                if (hashMap.containsKey(command)) {
                    cout = hashMap.get(command)
                    outputList.add(cout)
                }
            }
        }
        return outputList;
    }
}