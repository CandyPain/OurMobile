package com.example.ourmobile

import android.util.Log

class Variables(
    var variableName: String,
    var variableType: String
)

var variablesList = mutableListOf<Variables>()
var doRun: Boolean = true
var messagesCout = mutableListOf<String>()
var valuesCout = mutableListOf<Int>()
var messagesCin: String = ""
var commandList = mutableListOf<String>()
var commandListID = mutableListOf<Int>()

fun RunApp() {
    valuesCout.clear()
    variablesList.clear()
    messagesCout.clear()
    valuesCout.clear()
    doRun = true
    commandList = createCommandList()
    var compiler = CelestialElysiaInterpreter(hashMapOf<String, Any>(), commandList)
    var checkCommands: Boolean = checkCommandList(commandList)
    if(doRun && GlobalDebugMod == false)
    {
        compiler.interprete()
        for(pair in DebugList)
        {
            if(pair.key.value != "")
            {
                if(compiler.varHashMap.get(pair.key.value) != null)
                {
                    pair.value.value = compiler.varHashMap.get(pair.key.value).toString()
                }
            }
        }
    }
    if(doRun && GlobalDebugMod == true)
    {
        compiler.interpret_debug()
    }
    messagesCout += compiler.calloutList
    for(i in 0 until messagesCout.size)
    {
        Log.d("MyTag", messagesCout[i])
    }
    for(i in 0 until commandList.size)
    {
        Log.d("MyTag", commandList[i])
    }
}

fun createCommandList(): MutableList<String> {

    var commandList = mutableListOf<String>()

    var numOfEnd: Int = 1
    var endList = mutableListOf<String>()

    var hasChild = true;
    var childId: Int = CardList[0].childId.value
    var blockNumber: Int = 0
    while (hasChild) {
        ++blockNumber
        hasChild = false

        if (childId == -1) {
            break
        }

        for (i in 0 until TypeVaribleList.size) {
            if (TypeVaribleList[i].thisID == childId) {
                hasChild = true
                if (checkMakeAVariable(TypeVaribleList[i].variableName.value, blockNumber)) {
                    commandList.add("<variable:" + TypeVaribleList[i].variableName.value + "," + TypeVaribleList[i].selectedType.value + ">")
                    commandListID.add(TypeVaribleList[i].thisID)
                    variablesList.add(
                        Variables(
                            TypeVaribleList[i].variableName.value,
                            TypeVaribleList[i].selectedType.value
                        )
                    )
                } else {
                    doRun = false
                }
                childId = TypeVaribleList[i].childId.value
            }
        }

        if (!hasChild) {
            for (i in 0 until VariableAssignmentList.size) {
                if (VariableAssignmentList[i].thisID == childId) {
                    hasChild = true
                    var expString = spaceRemove(VariableAssignmentList[i].variableValue.value)
                    if (checkMakeAVariable(
                            VariableAssignmentList[i].variableName.value,
                            blockNumber
                        )
                    ) {
                        expString = normalizationOfExpression(expString)
                        commandList.add("<equals:" + VariableAssignmentList[i].variableName.value + ",<expression:" + expString + ">>")
                        commandListID.add(VariableAssignmentList[i].thisID)
                    } else {
                        doRun = false
                    }
                    childId = VariableAssignmentList[i].childId.value
                }
            }
        }

        if (!hasChild) {
            for (i in 0 until CoutBlockList.size) {
                if (CoutBlockList[i].thisID == childId) {
                    hasChild = true
                    var expString = spaceRemove(CoutBlockList[i].variableName.value)
                    expString = normalizationOfExpression(expString)
                    commandList.add("<callout:<expression:$expString>>")
                    commandListID.add(CoutBlockList[i].thisID)
                    childId = CoutBlockList[i].childId.value
                }
            }
        }
        /*
        if (!hasChild) {
            for (i in 0 until ForBlockList.size) {
                if (ForBlockList[i].thisID == childId) {
                    hasChild = true
                    var expStringFir = spaceRemove(ForBlockList[i].initExpression.value)
                    var expStringSec = spaceRemove(ForBlockList[i].condExpression.value)
                    var expStringThir = spaceRemove(ForBlockList[i].loopExpression.value)
                    expStringFir = normalizationOfExpression(expStringFir)
                    expStringSec = normalizationOfExpression(expStringSec)
                    expStringThir = normalizationOfExpression(expStringSec)
                    commandList.add("<if:<expression:$expStringFir,<expression:$expStringSec>," + IfBlockList[i].selectedSign.value + ",$numOfEnd>")
                    endList.add("<endfor:$numOfEnd>")
                    ++numOfEnd
                    childId = IfBlockList[i].childId.value
                }
            }
        }
        */
        if (!hasChild) {
            for (i in 0 until IfBlockList.size) {
                if (IfBlockList[i].thisID == childId) {
                    hasChild = true
                    var expStringFir = spaceRemove(IfBlockList[i].conditionFirst.value)
                    var expStringSec = spaceRemove(IfBlockList[i].conditionSecond.value)
                    expStringFir = normalizationOfExpression(expStringFir)
                    expStringSec = normalizationOfExpression(expStringSec)
                    commandList.add("<if:<expression:$expStringFir>,<expression:$expStringSec>," + IfBlockList[i].selectedSign.value + ",$numOfEnd>")
                    endList.add("<endif:$numOfEnd>")
                    commandListID.add(IfBlockList[i].thisID)
                    ++numOfEnd
                    childId = IfBlockList[i].childId.value
                }
            }
        }

        if (!hasChild) {
            for (i in 0 until EndBlockList.size) {
                if (EndBlockList[i].thisID == childId) {
                    hasChild = true
                    if (endList.size > 0) {
                        commandList.add(endList[endList.size - 1])
                        commandListID.add(EndBlockList[i].thisID)
                        endList.removeAt(endList.size - 1)
                    } else {
                        doRun = false
                    }
                    childId = EndBlockList[i].childId.value
                }
            }
        }
    }

    return commandList
}

fun checkMakeAVariable(name: String, index: Int): Boolean {
    var checkResult: Boolean = true
    val variableRegex = "([a-zA-Z][a-zA-Z0-9]*)".toRegex()
    var result = name
    result = variableRegex.replaceFirst(result, "a")
    if (result != "a") {
        checkResult = false
        messagesCout.add("In block number: $index name of variable is incorrect")
    }
    return checkResult
}

fun spaceRemove(exp: String): String {
    var adjExp: String = ""
    for (i in exp.indices) {
        if (exp[i] != ' ') {
            adjExp += exp[i]
        }
    }
    return adjExp
}

fun evaluateExpression(expression: String, index: Int): Boolean {
    val variableRegex = "([a-zA-Z][a-zA-Z0-9]*)"
    val numberRegex = "((([-])?[1-9][0-9]*([.][0-9]*[1-9])?)|(([-])?[0][.][0-9]*[1-9])|([0]))"
    val arrayRegex = "([a-zA-Z]+\\[(([a-zA-Z]+)|(([0])|([1-9][0-9]*)))\\])"
    val operandRegex =
        "(($variableRegex|$numberRegex|$arrayRegex)[\\+\\-/*]($variableRegex|$numberRegex|$arrayRegex))"
    val expressionRegex = "(($operandRegex)|([\\(]$operandRegex[\\)]))"
    val pattern = expressionRegex.toRegex()
    var result = expression
    while (pattern.find(result) != null) {
        val matchResult = pattern.find(result)
        val matchedText = matchResult?.value
        println(matchedText)
        result = pattern.replaceFirst(result, "a")
        println(result)
    }
    if (result == "a") {
        return true
    }
    messagesCout.add("In block number: $index expression is incorrect")
    return false
}

fun normalizationOfExpression(expression: String): String {
    val variableRegex = "([a-zA-Z][a-zA-Z0-9]*)"
    val numberRegex = "(([1-9][0-9]*([.][0-9]*[1-9])?)|([0][.][0-9]*[1-9])|([0]))"
    val arrayRegex = "([a-zA-Z]+\\[(([a-zA-Z]+)|(([0])|([1-9][0-9]*)))\\])"
    val expressionRegex =
        "($variableRegex|$numberRegex|$arrayRegex|([\\+\\-\\/\\*])|([\\(])|([\\)]))"
    val pattern = expressionRegex.toRegex()
    var expSymbReg = "([\\+\\-\\/\\*][ ][\\-][ ])"
    var expSymbRegex = expSymbReg.toRegex()
    var exp = expression
    var result: String = ""

    while (pattern.find(exp) != null) {
        val matchExp = pattern.find(exp)
        var matchedExp = matchExp?.value
        exp = pattern.replaceFirst(exp, "")
        result += "$matchedExp "
    }

    while (expSymbRegex.find(result) != null) {
        val matchExp = expSymbRegex.find(result)
        val matchedExp = matchExp?.value
        var neededExp = matchedExp.toString()
        neededExp = ".$".toRegex().replaceFirst(neededExp, "")
        result = expSymbRegex.replaceFirst(result, neededExp)
    }

    result = ".$".toRegex().replaceFirst(result, "")
    return result
}

fun checkCommandList(commandList: List<String>): Boolean {
    //toDo
    return true
}