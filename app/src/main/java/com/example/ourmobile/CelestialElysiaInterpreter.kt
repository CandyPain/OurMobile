package com.example.ourmobile

import java.util.HashMap
import java.util.Stack
import kotlin.reflect.KClass

class CelestialElysiaInterpreter(val varHashMap: HashMap<String, Int>,
                                 private val commandList: MutableList<String>) {
    val tokenHashMap = hashMapOf<String, KClass<out IToken>>(
        "<variable" to VariableToken::class,
        "<equals" to EqualsToken::class,
        "<expression" to ExpressionToken::class,
        "<callout" to CallOutToken::class,
        "<if" to IfToken::class,
        "<endif" to EndIfToken::class,
        "<for" to ForToken::class,
        "<endfor" to EndForToken::class
    )
    var calloutList = mutableListOf<Int>()
    var stack = ArrayDeque<Int>()
    var stringPoint: Int = 0

    fun interprete(){
        var tokenRegex = Regex("<\\w+")
        while(stringPoint<commandList.size){
            var tokenName = tokenRegex.find(commandList[stringPoint])!!.value
            var tokenType = tokenHashMap.get(tokenName)
            var tokenObject = tokenType?.java?.newInstance() as? IToken ?: throw IllegalArgumentException("Invalid token type")
            tokenObject.command(commandList[stringPoint],this)
            stringPoint++
        }
    }

}