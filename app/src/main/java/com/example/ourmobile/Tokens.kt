package com.example.ourmobile

import Expression
import kotlin.reflect.KClass

interface IToken {
    fun command(input:String, program:CelestialElysiaInterpreter){

    }
    var regex: Regex
    var returnType: String
}
class VariableToken : IToken{
    override var regex = Regex("(?<=(<variable:))\\w+(?=>)")
    override var returnType = "void"
    override fun command(input:String, program:CelestialElysiaInterpreter){
        var varName: String?
        var match = regex.find(input)
        varName = match?.value
        program.varHashMap.put(varName!!, 0)
    }
}
class EqualsToken : IToken{
    override var regex = Regex("(?<=(<equals:))\\w+,<.+>(?=>)")
    var tokenRegex = Regex("<\\w+")
    override var returnType = "void"
    override fun command(input:String, program:CelestialElysiaInterpreter){
        var varName: String?
        var processedInput: String?
        var match = regex.find(input)
        processedInput = match?.value

        var arguments = processedInput!!.split(",")
        var tokenName = tokenRegex.find(arguments[1])!!.value
        var tokenType = program.tokenHashMap.get(tokenName)
        var tokenObject = tokenType?.java?.newInstance() as? IToken ?: throw IllegalArgumentException("Invalid token type")
        tokenObject.command(arguments[1],program)
        program.varHashMap.put(arguments[0],program.stack.removeFirst())
    }
}
class ExpressionToken : IToken{
    override var regex = Regex("(?<=(<expression:)).+(?=>)")
    override var returnType = "int"
    override fun command(input:String, program:CelestialElysiaInterpreter){
        var expressionString: String?
        var match = regex.find(input)
        expressionString = match?.value

        val expression = Expression()
        val expressionValue = expression.evaluateReversePolishNotation(expression.toReversePolishNotation(expressionString!!, program.varHashMap))
        program.stack.add(expressionValue)
    }
}
class CallOutToken : IToken{
    override var regex = Regex("(?<=(<callout:)).+(?=>)")
    var tokenRegex = Regex("<\\w+")
    override fun command(input:String, program:CelestialElysiaInterpreter){
        var processedInput: String?
        var match = regex.find(input)
        processedInput = match?.value

        var tokenName = tokenRegex.find(processedInput!!)!!.value
        var tokenType = program.tokenHashMap.get(tokenName)
        var tokenObject = tokenType?.java?.newInstance() as? IToken ?: throw IllegalArgumentException("Invalid token type")
        tokenObject.command(processedInput,program)
        program.calloutList.add(program.stack.removeFirst())
    }
    override var returnType = "void"
}

class IfToken : IToken{
    override var regex = Regex("^var \\w+")
    override var returnType = "void"
}
class EndIfToken : IToken{
    override var regex = Regex("^var \\w+")
    override var returnType = "void"
}
class ForToken : IToken {
    override var regex = Regex("^var \\w+")
    override var returnType = "void"
}
class EndForToken : IToken{
    override var regex = Regex("^var \\w+")
    override var returnType = "void"
}