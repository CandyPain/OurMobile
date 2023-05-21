package com.example.ourmobile

import java.util.HashMap
import java.util.Stack
import kotlin.reflect.KClass

class CelestialElysiaInterpreter(var varHashMap: HashMap<String, Any>,
                                 val commandList: MutableList<String>, var returnType: String = "void") {
    val tokenHashMap = hashMapOf<String, KClass<out IToken>>(
        "<variable" to VariableToken::class,
        "<equals" to EqualsToken::class,
        "<expression" to ExpressionToken::class,
        "<stringexpression" to StringExpressionToken::class,
        "<callout" to CallOutToken::class,
        "<if" to IfToken::class,
        "<endif" to EndIfToken::class,
        "<for" to ForToken::class,
        "<endfor" to EndForToken::class,
        "<array" to ArrayToken::class,
        "<truearray" to TrueArrayToken::class,
        "<while" to WhileToken::class,
        "<endwhile" to EndWhileToken::class,
        "<extendedfor" to ExtendedForToken::class,
        "<endextendedfor" to EndExtendedForToken::class,
        "<cast" to CastToken::class,
        "<function" to FunctionToken::class,
        "<endfunction" to EndFunctionToken::class,
        "<return" to ReturnToken::class,
        "<callin" to CallInToken::class
    )

    var baseVarHashMap: HashMap<String, Any> = varHashMap.toMap() as HashMap
    var functionHashMap = HashMap<String, CelestialElysiaInterpreter>()

    var calloutList = mutableListOf<String>()
    var stack = ArrayDeque<Double>()
    var stringStack = ArrayDeque<String>()
    var stringPoint: Int = 0
    var forStack = ArrayDeque<Int>()

    var returnValue: Any = 0

    var inputValue: String = "0"
    fun interprete(){
        var tokenRegex = Regex("<\\w+")
        varHashMap = baseVarHashMap
        while(stringPoint<commandList.size){
            var tokenName = tokenRegex.find(commandList[stringPoint])!!.value
            var tokenType = tokenHashMap.get(tokenName)
            var tokenObject = tokenType?.java?.newInstance() as? IToken ?: throw IllegalArgumentException("Invalid token type")
            tokenObject.command(commandList[stringPoint],this)
            stringPoint++
        }
    }

}