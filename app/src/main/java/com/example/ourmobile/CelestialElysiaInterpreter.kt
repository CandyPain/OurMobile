package com.example.ourmobile

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.reflect.KClass

class CelestialElysiaInterpreter(val varHashMap: HashMap<String, Any>,
                                 val commandList: MutableList<String>) {
    val tokenHashMap = hashMapOf<String, KClass<out IToken>>(
        "<variable" to VariableToken::class,
        "<equals" to EqualsToken::class,
        "<expression" to ExpressionToken::class,
        "<callout" to CallOutToken::class,
        "<if" to IfToken::class,
        "<endif" to EndIfToken::class,
        "<for" to ForToken::class,
        "<endfor" to EndForToken::class,
        "<array" to ArrayToken::class
    )
    var calloutList = mutableListOf<String>()
    var stack = ArrayDeque<Double>()
    var stringPoint: Int = 0
    var forStack = ArrayDeque<Int>()

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

    fun interpret_debug()
    {
        val scope = CoroutineScope(Dispatchers.Default)
        scope.launch {
            var tokenRegex = Regex("<\\w+")
            while (stringPoint < commandList.size) {
                if(NextStep) {
                    var tokenName = tokenRegex.find(commandList[stringPoint])!!.value
                    var tokenType = tokenHashMap.get(tokenName)
                    var tokenObject = tokenType?.java?.newInstance() as? IToken
                        ?: throw IllegalArgumentException("Invalid token type")
                    tokenObject.command(commandList[stringPoint], this@CelestialElysiaInterpreter)
                    stringPoint++
                    for(pair in DebugList)
                    {
                        if(pair.key.value != "")
                        {
                            if(this@CelestialElysiaInterpreter.varHashMap.get(pair.key.value) != null)
                            {
                                pair.value.value = this@CelestialElysiaInterpreter.varHashMap.get(pair.key.value).toString()
                            }
                        }
                    }
                    NextStep = false;
                }
                delay(100)
            }
        }
    }

}