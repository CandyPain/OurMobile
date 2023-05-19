package com.example.ourmobile

import androidx.compose.material3.Button
import androidx.compose.runtime.Composable

class Variables(
    var variableName: String,
    var variableType: String
)

var variablesList = mutableListOf<Variables>()
var doRun: Boolean = true

@Composable
fun RunApp() {
    Button(
        onClick = {
            valuesCout.clear()
            variablesList.clear()
            messagesCout.clear()
            valuesCout.clear()
            doRun = true
            var commandList = createCommandList()
            var checkCommands: Boolean = checkCommandList(commandList)
            var compiler = CelestialElysiaInterpreter(hashMapOf<String, Int>(), commandList)
            compiler.interprete()
            valuesCout = compiler.calloutList
        }
    )
    {

    }
}

fun createCommandList(): MutableList<String> {
    var commandList = mutableListOf<String>()

    var hasChild = true;
    var childId: Int = 0
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
                    commandList.add("<" + TypeVaribleList[i].selectedType.value + ":" + TypeVaribleList[i].variableName.value + ">")
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
                    if ((checkMakeAVariable(
                            VariableAssignmentList[i].variableName.value,
                            blockNumber
                        )) && (evaluateExpression(expString, blockNumber))
                    ) {
                        expString = normalizationOfExpression(expString)
                        commandList.add("<equals:" + VariableAssignmentList[i].variableName.value + ",<expression:" + expString + ">>")
                    } else {
                        doRun = false
                    }
                    childId = VariableAssignmentList[i].childId.value
                }
            }
        }

        /*
        if(!hasChild)
        {
            for (i in 0 until IfBlockList.size).
            {
                if(IfBlockList[i].thisID == childId)
                {
                    hasChild = true

                    childId = VariableAssignmentList[i].childId.value
                }
            }
        }
        */
    }

    return commandList
}

fun checkMakeAVariable(name: String, index: Int): Boolean {
    var checkResult: Boolean = true
    val variableRegex = "([a-zA-Z][a-zA-Z0-9]*)"
    var result = name
    result = variableRegex.replaceFirst(result, "a")
    if (result != "a") {
        checkResult = false
        messagesCout.add("In block number: $index name of variable is incorrect")
    }
    for (i in 0 until variablesList.size) {
        if (variablesList[i].variableName == name) {
            checkResult = false
            messagesCout.add("In block number: $index those variable is already created")
        }
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