package com.example.ourmobile

import Expression
import boolExpression
import java.util.HashMap
import kotlin.reflect.KClass

interface IToken {
    fun command(input:String, program:CelestialElysiaInterpreter){
        val processedInput: String?
        val match = regex.find(input)
        processedInput = match?.value
    }
    var regex: Regex
    var returnType: String
}
class VariableToken : IToken{
    override var regex = Regex("(?<=(^<variable:)).+(?=>$)")
    override var returnType = "void"
    override fun command(input:String, program:CelestialElysiaInterpreter){
        var varName: String?
        val match = regex.find(input)
        val processedInput = match?.value
        val arguments = processedInput!!.split(",")
        val variableType = when (arguments[1]) {
            "int" -> 0
            "double" -> 0.0
            "string" -> ""
            else -> 0
        }
        program.varHashMap.put(arguments[0], variableType)
    }
}
class EqualsToken : IToken{
    override var regex = Regex("(?<=(^<equals:)).+,<.+>(?=>$)")
    var tokenRegex = Regex("<\\w+")
    override var returnType = "void"

    val arrayRegex = Regex("^\\w+\\[.+]$")
    val arrayNameRegex = Regex("^\\w+(?=\\[)")
    val arrayExpressionRegex = Regex("(?<=(\\[)).+(?=]$)")
    override fun command(input:String, program:CelestialElysiaInterpreter){
        var varName: String?
        val processedInput: String?
        val match = regex.find(input)
        processedInput = match?.value

        val arguments = processedInput!!.split(",")
        val tokenName = tokenRegex.find(arguments[1])!!.value
        val tokenType = program.tokenHashMap.get(tokenName)
        val tokenObject = tokenType?.java?.newInstance() as? IToken ?: throw IllegalArgumentException("Invalid token type")
        tokenObject.command(arguments[1],program)

        varName = arguments[0]

        val variableValue = program.varHashMap[varName]
        if(arguments[0].matches(arrayRegex)){
            val expression = Expression()
            val arrayIndex = expression.evaluateReversePolishNotation(
                expression.toReversePolishNotation(arrayExpressionRegex
                    .find(arguments[0])!!.value,program.varHashMap, program)).toInt()
            val arrayName = arrayNameRegex.find(arguments[0])!!.value
            val array = program.varHashMap.get(arrayName) as? Array<*>
            if (array is Array && array != null) {
                if(array[arrayIndex] is Int){
                    val value = program.stack.removeFirst().toInt()
                    val typedArray = array as IntArray
                    typedArray[arrayIndex] = value.toInt()
                    program.varHashMap[arrayName] = typedArray
                }
                else if(array[arrayIndex] is Double){
                    val value = program.stack.removeFirst()
                    val typedArray = array as Array<Double>
                    typedArray[arrayIndex] = value
                    program.varHashMap[arrayName] = typedArray
                }
                else{
                    val value = program.stringStack.removeFirst()
                    val typedArray = array as Array<String>
                    typedArray[arrayIndex] = value
                    program.varHashMap[arrayName] = typedArray
                }

            }
            return
        }

        if(variableValue!!::class.java.simpleName == "Integer"){
            program.varHashMap.put(varName,program.stack.removeFirst().toInt())
        }
        else if(variableValue!!::class.java.simpleName == "String"){
            program.varHashMap.put(varName,program.stringStack.removeFirst().toString())
        }
        else{

            program.varHashMap.put(varName,program.stack.removeFirst())
        }
    }
}
class ExpressionToken : IToken{
    override var regex = Regex("(?<=(<expression:)).+(?=>)")
    override var returnType = "double"
    override fun command(input:String, program:CelestialElysiaInterpreter){
        val expressionString: String?
        val match = regex.find(input)
        expressionString = match?.value

        val expression = Expression()
        val expressionValue = expression.evaluateReversePolishNotation(
            expression.toReversePolishNotation(expressionString!!, program.varHashMap, program))
        program.stack.addFirst(expressionValue)
        program.FFAstack.addFirst(expressionValue)
    }
}
class StringExpressionToken : IToken{
    override var regex = Regex("(?<=(<stringexpression:)).+(?=>)")
    override var returnType = "void"

    var rawStringToken = Regex("^//.+//$")
    var stringToken = Regex("(?<=(^//)).+(?=//$)")

    override fun command(input:String, program:CelestialElysiaInterpreter){
        val processedInput: String?
        val match = regex.find(input)
        processedInput = match?.value

        val terms = processedInput!!.split("+").map { it.trim() }
        val stringBuilder = StringBuilder()

        for (term in terms) {
            val trimmedTerm = term.trim()
            val value = program.varHashMap[trimmedTerm] ?: stringToken.find(trimmedTerm)!!.value
            stringBuilder.append(value)
        }

        program.stringStack.addFirst(stringBuilder.toString())
        program.FFAstack.addFirst(stringBuilder.toString())
    }
}
class VariableExpressionToken : IToken {
    override var regex = Regex("(?<=(<anyxpression:)).+(?=>)")
    override var returnType = "void"
    override fun command(input:String, program:CelestialElysiaInterpreter) {
        val processedInput: String?
        val match = regex.find(input)
        processedInput = match?.value

        program.FFAstack.addFirst(program.varHashMap[processedInput!!]!!)
    }
}
class CallOutToken : IToken{
    override var regex = Regex("(?<=(^<callout:)).+(?=>$)")
    var tokenRegex = Regex("<\\w+")
    override fun command(input:String, program:CelestialElysiaInterpreter){
        val processedInput: String?
        val match = regex.find(input)
        processedInput = match?.value

        val tokenName = tokenRegex.find(processedInput!!)!!.value
        val tokenType = program.tokenHashMap.get(tokenName)
        val tokenObject = tokenType?.java?.newInstance() as? IToken ?: throw IllegalArgumentException("Invalid token type")

        tokenObject.command(processedInput,program)


        program.calloutList.add(program.FFAstack.removeFirst().toString())
        program.FFAstack.clear()
    }
    override var returnType = "void"
}

class IfToken : IToken{
    override var regex = Regex("(?<=(^<if:)).+(?=>\$)")
    override var returnType = "void"
    var tokenRegex = Regex("<\\w+")
    var endifRegex = Regex("<(endif|else):\\d+>")
    var idRegex = Regex("(?<=(^<endif:))\\d+(?=>$)")
    override fun command(input:String, program:CelestialElysiaInterpreter){
        val processedInput: String?
        val match = regex.find(input)
        processedInput = match?.value

        val arguments = processedInput!!.split(",")

        val boolValue: Boolean = boolExpression(arguments[0], arguments[1], arguments[2], program)

        if(!boolValue){
            for(n in program.stringPoint..program.commandList.size-1){
                if(program.commandList[n].matches(endifRegex)
                    && idRegex.find(program.commandList[n])!!.value == arguments[3]){
                    program.stringPoint = n
                }
            }
        }
    }
}
class ElseToken : IToken{
    override var regex = Regex("^<else")
    override var returnType = "void"
}
class EndIfToken : IToken{
    override var regex = Regex("^<endif")
    override var returnType = "void"
}

class ForToken : IToken {
    override var regex = Regex("(?<=(^<for:)).+(?=>$)")
    override var returnType = "void"
    override fun command(input:String, program:CelestialElysiaInterpreter){
        val processedInput: String?
        val match = regex.find(input)
        processedInput = match?.value

        val arguments = processedInput!!.split(",")
        program.forStack.addFirst(arguments[0].toInt())
    }
}
class EndForToken : IToken{
    override var regex = Regex("(?<=(^<endfor:)).+(?=>$)")
    var forRegex = Regex("<for:.+")
    override var returnType = "void"
    var idRegex = Regex("(?<=(,))\\d+(?=>$)")
    override fun command(input:String, program:CelestialElysiaInterpreter){
        val processedInput: String?
        val match = regex.find(input)
        processedInput = match?.value

        program.forStack.set(0,program.forStack[0]-1)
        if(program.forStack[0]>0){
            for(n in 0..program.commandList.size-1){
                if(program.commandList[n].matches(forRegex)
                    && idRegex.find(program.commandList[n])!!.value == processedInput){
                    program.stringPoint = n
                }
            }
        }
        else{
            program.forStack.removeFirst()
        }
    }
}
class ArrayToken : IToken{
    override var regex = Regex("(?<=(^<array:)).+(?=>\$)")
    override var returnType = "void"
    override fun command(input:String, program:CelestialElysiaInterpreter){
        val processedInput: String?
        val match = regex.find(input)
        processedInput = match?.value

        val arguments = processedInput!!.split(",")
        val arrayName = arguments[0]
        val arrayCapacity = arguments[1].toInt()
        val variableType = when (arguments[2]) {
            "int" -> 0
            "double" -> 0.0
            "string" -> ""
            else -> 0
        }

        for(n in 1..arrayCapacity){
            program.varHashMap.put(arrayName+"["+(n-1).toString()+"]", variableType)
        }
    }
}
class TrueArrayToken : IToken{
    override var regex = Regex("(?<=(^<truearray:)).+(?=>\$)")
    override var returnType = "void"
    override fun command(input:String, program:CelestialElysiaInterpreter){
        val processedInput: String?
        val match = regex.find(input)
        processedInput = match?.value

        val arguments = processedInput!!.split(",")
        val arrayName = arguments[0]
        val arrayCapacity = arguments[1].toInt()
        when (arguments[2]) {
            "int" -> program.varHashMap.put(arrayName, IntArray(arrayCapacity) {0})
            "double" -> program.varHashMap.put(arrayName, DoubleArray(arrayCapacity) {0.0})
            "string" -> program.varHashMap.put(arrayName, Array<String>(arrayCapacity) {""})
            else -> 0
        }

    }
}
class ExtendedForToken : IToken{
    override var regex = Regex("(?<=(^<extendedfor:)).+(?=>$)")
    override var returnType = "void"

    var tokenRegex = Regex("<\\w+")
    var endextendedforRegex = Regex("<endextendedfor:\\d+>")
    var idRegex = Regex("(?<=(^<endextendedfor:))\\d+(?=>$)")
    override fun command(input:String, program:CelestialElysiaInterpreter){
        val processedInput: String?
        val match = regex.find(input)
        processedInput = match?.value

        val arguments = processedInput!!.split(";")

        val beginToken = tokenRegex.find(arguments[0])!!.value
        val tokenType = program.tokenHashMap.get(beginToken)
        val tokenObject = tokenType?.java?.newInstance() as? IToken ?: throw IllegalArgumentException("Invalid token type")
        tokenObject.command(arguments[0],program)

        val boolValue: Boolean = boolExpression(arguments[1], arguments[2], arguments[3], program)

        if(!boolValue) {
            for (n in program.stringPoint..program.commandList.size - 1) {
                if (program.commandList[n].matches(endextendedforRegex)
                    && idRegex.find(program.commandList[n])!!.value == arguments[5]) {
                    program.stringPoint = n
                    return
                }
            }
        }
    }
}
class EndExtendedForToken : IToken {
    override var regex = Regex("(?<=(^<endextendedfor:)).+(?=>$)")
    override var returnType = "void"

    var forRegex = Regex("<extendedfor:.+")
    var idRegex = Regex("(?<=(;))\\d+(?=>$)")
    var tokenRegex = Regex("<\\w+")
    var argumentsRegex = Regex("(?<=(^<extendedfor:)).+(?=>$)")
    override fun command(input:String, program:CelestialElysiaInterpreter){
        val processedInput: String?
        val match = regex.find(input)
        processedInput = match?.value

        val currentString = program.stringPoint

        for(n in 0..program.commandList.size-1){
            if(program.commandList[n].matches(forRegex)
                && idRegex.find(program.commandList[n])!!.value == processedInput){
                program.stringPoint = n
                break
            }
        }

        val extendedforString = program.commandList[program.stringPoint]

        val extendedforInput: String?
        val extendedforMatch = argumentsRegex.find(extendedforString)
        extendedforInput = extendedforMatch?.value

        val arguments = extendedforInput!!.split(";")
        val beginToken = tokenRegex.find(arguments[4])!!.value
        val tokenType = program.tokenHashMap.get(beginToken)
        val tokenObject = tokenType?.java?.newInstance() as? IToken ?: throw IllegalArgumentException("Invalid token type")

        tokenObject.command(arguments[4],program)

        val boolValue: Boolean = boolExpression(arguments[1], arguments[2], arguments[3], program)

        if(!boolValue) {
            program.stringPoint = currentString
        }
    }
}
class WhileToken : IToken{
    override var regex = Regex("(?<=(^<while:)).+(?=>$)")
    override var returnType = "void"
    var endwhileRegex = Regex("<endwhile:\\d+>")
    var idRegex = Regex("(?<=(^<endwhile:))\\d+(?=>$)")
    override fun command(input:String, program:CelestialElysiaInterpreter){
        val processedInput: String?
        val match = regex.find(input)
        processedInput = match?.value

        val arguments = processedInput!!.split(",")

        val boolValue: Boolean = boolExpression(arguments[0], arguments[1], arguments[2], program)

        if(!boolValue){
            for(n in program.stringPoint..program.commandList.size-1){
                if(program.commandList[n].matches(endwhileRegex)
                    && idRegex.find(program.commandList[n])!!.value == arguments[3]){
                    program.stringPoint = n
                }
            }
        }
    }
}
class EndWhileToken : IToken{
    override var regex = Regex("(?<=(^<endwhile:)).+(?=>$)")
    override var returnType = "void"
    var idRegex = Regex("(?<=(,))\\d+(?=>$)")
    var whileRegex = Regex("<while:.+>")
    override fun command(input:String, program:CelestialElysiaInterpreter) {
        val processedInput: String?
        val match = regex.find(input)
        processedInput = match?.value

        for(n in 0..program.stringPoint){
            if(program.commandList[n].matches(whileRegex)
                && idRegex.find(program.commandList[n])!!.value == processedInput){
                program.stringPoint = n-1
            }
        }
    }
}
class CastToken : IToken{
    override var regex = Regex("(?<=(^<cast:)).+(?=>$)")
    override var returnType = "void"
    override fun command(input:String, program:CelestialElysiaInterpreter) {
        val processedInput: String?
        val match = regex.find(input)
        processedInput = match?.value

        val arguments = processedInput!!.split(",")
        val variableValue = program.varHashMap.getValue(arguments[0])

        val variableCasted: Any = when (arguments[1]) {
            "int" -> variableValue.toString().toInt()
            "double" -> variableValue.toString().toDouble()
            "string" -> variableValue.toString()
            else -> 0
        }
        program.varHashMap.put(arguments[0], variableCasted)
    }
}
class FunctionToken : IToken{
    override var regex = Regex("(?<=(^<function:)).+(?=>$)")
    override var returnType = "void"
    override fun command(input:String, program:CelestialElysiaInterpreter) {
        val processedInput: String?
        val match = regex.find(input)
        processedInput = match?.value

        val arguments = processedInput!!.split(";")

        val functionProperties = arguments[0].split(",")
        val functionArguments = arguments[1].split(",")

        var newVarHashMap = hashMapOf<String, Any>()
        var newCommandList = mutableListOf<String>()

        for(n in functionArguments){
            val nameAndType = n.split(":")
            val name = nameAndType[0]
            val type = nameAndType[1]

            when(type){
                "int" -> newVarHashMap.put(name, 0)
                "double" -> newVarHashMap.put(name, 0.0)
                "string" -> newVarHashMap.put(name, "")
                "array<int>" -> newVarHashMap.put(name, IntArray(0))
                "array<double>" -> newVarHashMap.put(name, DoubleArray(0))
                "array<string>" -> newVarHashMap.put(name, Array<String>(0){""})
            }
        }
        for(n in program.stringPoint..program.commandList.size-1){
            if(program.commandList[n]=="<endfuntion:"+functionProperties[2]+">") break
            newCommandList.add(program.commandList[n])
        }

        var newProgram: CelestialElysiaInterpreter
                = CelestialElysiaInterpreter(newVarHashMap,newCommandList,functionProperties[1])

        program.functionHashMap.put(functionProperties[0], newProgram)
    }
}
class EndFunctionToken : IToken{
    override var regex = Regex("(?<=(^<endfunction:)).+(?=>$)")
    override var returnType = "void"
}
class ReturnToken : IToken{
    override var regex = Regex("(?<=(^<return:)).+(?=>$)")
    override var returnType = "void"
    override fun command(input:String, program:CelestialElysiaInterpreter) {
        val processedInput: String?
        val match = regex.find(input)
        processedInput = match?.value
        program.returnValue = when(program.returnType){
            "void" -> null
            "int" -> processedInput!!.toInt()
            "double" -> processedInput!!.toDouble()
            "string" -> processedInput!!
            else -> 0
        }!!
    }
}
class CallInToken : IToken{
    override var regex = Regex("(?<=(^<callin:)).+(?=>$)")
    override var returnType = "void"
    override fun command(input:String, program:CelestialElysiaInterpreter) {
        val processedInput: String?
        val match = regex.find(input)
        processedInput = match?.value

        val equalsToken = EqualsToken()
        equalsToken.command("<equals:"+processedInput+program.inputValue+">", program)
    }
}
