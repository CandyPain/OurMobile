package com.example.ourmobile

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import processArray
import kotlin.reflect.KClass

class CelestialElysiaInterpreter(
    var varHashMap: HashMap<String, Any>,
    val commandList: MutableList<String>, var returnType: String = "void"
) {
    val tokenHashMap = hashMapOf<String, KClass<out IToken>>(
        "<variable" to VariableToken::class,
        "<equals" to EqualsToken::class,
        "<expression" to ExpressionToken::class,
        "<stringexpression" to StringExpressionToken::class,
        "<anyexpression" to AnyExpressionToken::class,
        "<callout" to CallOutToken::class,
        "<if" to IfToken::class,
        "<else" to ElseToken::class,
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
        "<callin" to CallInToken::class,
        "<struct" to StructToken::class,
        "<structobject" to StructObjectToken::class,
        "<callfucntion" to CallFunctionToken::class,
        "<continue" to ContinueToken::class,
        "<break" to BreakToken::class
    )

    var functionHashMap = HashMap<String, CelestialElysiaInterpreter>()

    var calloutList = mutableListOf<String>()
    var stack = ArrayDeque<Double>()
    var stringStack = ArrayDeque<String>()
    var stringPoint: Int = 0
    var forStack = ArrayDeque<Int>()
    var FFAstack = ArrayDeque<Any>()

    var variableVisibilityStack = ArrayDeque<kotlin.collections.MutableList<String>>()

    var returnValue: Any = 0

    val CinList = messagesCin.split(",")
    var NumberCin = 0;

    var inputValue: String = "0"
    fun interprete() {
        stringPoint = 0
        variableVisibilityStack.clear()
        variableVisibilityStack.addFirst(mutableListOf<String>())

        var tokenRegex = Regex("<\\w+")
        while (stringPoint < commandList.size) {
            var tokenName = tokenRegex.find(commandList[stringPoint])!!.value
            var tokenType = tokenHashMap.get(tokenName)
            var tokenObject = tokenType?.java?.newInstance() as? IToken
                ?: throw IllegalArgumentException("Invalid token type")
            tokenObject.command(commandList[stringPoint], this)
            stringPoint++
        }
    }

    fun interpret_debug() {

        val scope = CoroutineScope(Dispatchers.Default)
        scope.launch {
            var tokenRegex = Regex("<\\w+")
            val arrayNameRegex = Regex("^\\w+(?=\\[)")
            val arrayExpressionRegex = Regex("(?<=(\\[)).+(?=]$)")
            val arrayRegex = Regex("^\\w+\\[.+]$")
            variableVisibilityStack.clear()
            variableVisibilityStack.addFirst(mutableListOf<String>())
            while (stringPoint < com.example.ourmobile.commandList.size) {
                if (NextStep) {
                    var tokenName =
                        tokenRegex.find(com.example.ourmobile.commandList[stringPoint])!!.value
                    var tokenType = tokenHashMap.get(tokenName)
                    var tokenObject = tokenType?.java?.newInstance() as? IToken
                        ?: throw IllegalArgumentException("Invalid token type")
                    tokenObject.command(commandList[stringPoint], this@CelestialElysiaInterpreter)
                    stringPoint++
                    for (pair in DebugList) {
                        if (pair.key.value != "") {
                            if(pair.key.value.toString().matches(arrayRegex))
                            {
                                pair.value.value =
                                    processArray(this@CelestialElysiaInterpreter.varHashMap.get(pair.key.value).toString(),this@CelestialElysiaInterpreter.varHashMap,this@CelestialElysiaInterpreter)
                            }
                            if (this@CelestialElysiaInterpreter.varHashMap.get(pair.key.value) != null) {
                                pair.value.value =
                                    this@CelestialElysiaInterpreter.varHashMap.get(pair.key.value)
                                        .toString()
                            }
                        }
                    }
                    DebugID = stringPoint
                    NextStep = false;
                }
                delay(100)
            }
        }
    }

}

