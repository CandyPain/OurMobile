package com.example.ourmobile

import android.util.Log


class Variables(
    var variableName: String,
    var variableType: String,
)

class Functions(
    var funcName: String,
    var usageOfParams: List<String>
)

var variablesList = mutableListOf<Variables>()
var funList = mutableListOf<Functions>()
var doRun: Boolean = true
var messagesCout = mutableListOf<String>()
var messagesCin: String = ""
var commandList = mutableListOf<String>()
var commandListID = mutableListOf<Int>()

var pendingCin: Boolean = false
var currentCin: Boolean = false


fun runApp() {
    variablesList.clear()
    messagesCout.clear()
    funList.clear()
    doRun = true
    commandList = createCommandList()
    var compiler = CelestialElysiaInterpreter(hashMapOf<String, Any>(), commandList)
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

    var hasChild = true
    var childId: Int = -1
    var blockNumber: Int = 0

    for (i in 0 until FunctionBlockList.size) {
        val functionName: String = FunctionBlockList[i].FunctionName.value
        var functionArgument: String = FunctionBlockList[i].FunctionParams.value
        functionArgument = normalizationPrametres(functionArgument)
        commandList.add("<function:" + functionName + "," + FunctionBlockList[i].selectedType.value  + "," + i.toString() + ";" + functionArgument + ">")
        funList.add(Functions(functionName, makeParametresList(functionArgument)))

        hasChild = true
        childId = FunctionBlockList[i].childId.value
        blockNumber = 0
        variablesList.clear()
        endList.clear()

        while (hasChild) {
            ++blockNumber
            hasChild = false

            if ((childId == -1) || (childId == 0)) {
                break
            }

            for (j in 0 until TypeVaribleList.size) {
                if (TypeVaribleList[j].thisID == childId) {
                    hasChild = true
                    commandList.add("<variable:" + TypeVaribleList[j].variableName.value + "," + TypeVaribleList[j].selectedType.value + ">")
                    variablesList.add(
                        Variables(
                            TypeVaribleList[j].variableName.value,
                            TypeVaribleList[j].selectedType.value,
                        )
                    )
                    childId = TypeVaribleList[j].childId.value
                }
            }

            if (!hasChild) {
                for (j in 0 until ArrayVaribleList.size) {
                    if (ArrayVaribleList[j].thisID == childId) {
                        hasChild = true
                        commandList.add("<truearray:" + ArrayVaribleList[j].variableName.value + "," + ArrayVaribleList[j].count.value + "," + ArrayVaribleList[j].selectedType.value + ">")
                        variablesList.add(
                            Variables(
                                ArrayVaribleList[j].variableName.value,
                                "array<" + ArrayVaribleList[j].selectedType.value + ">",
                            )
                        )
                        childId = ArrayVaribleList[j].childId.value
                    }
                }
            }

            if (!hasChild) {
                for (j in 0 until VariableAssignmentList.size) {
                    if (VariableAssignmentList[j].thisID == childId) {
                        hasChild = true
                        if (checkVariableName(VariableAssignmentList[j].variableName.value)) {
                            val type = returnVariableType(VariableAssignmentList[j].variableName.value)
                            val underType =
                                returnUnderVariableType(VariableAssignmentList[j].variableName.value)
                            if (underType == "Array") {
                                commandList.add("<equals:" + VariableAssignmentList[j].variableName.value + ",<anyexpression:" + normalizationOfExpression(VariableAssignmentList[j].variableValue.value) + ">>")
                            } else {
                                var name = VariableAssignmentList[j].variableName.value
                                if(underType == "ElementOfArray") {
                                    name = normalizationElementOfArray(name)
                                }
                                if (type == "String") {
                                    commandList.add("<equals:" + name + ",<stringexpression:" + normalizationOfExpression(VariableAssignmentList[j].variableValue.value) + ">>")
                                } else {
                                    commandList.add("<equals:" + name + ",<expression:" + normalizationOfExpression(VariableAssignmentList[j].variableValue.value) + ">>")
                                }
                            }
                        } else {
                            doRun = false
                        }
                        childId = VariableAssignmentList[j].childId.value
                    }
                }
            }

            if (!hasChild) {
                for (j in 0 until IfBlockList.size) {
                    if (IfBlockList[j].thisID == childId) {
                        hasChild = true
                        commandList.add(
                            "<if:<expression:" + normalizationOfExpression(IfBlockList[j].conditionFirst.value) + ">,<expression:" + normalizationOfExpression(
                                IfBlockList[j].conditionSecond.value
                            ) + ">," + IfBlockList[j].selectedSign.value + "," + numOfEnd.toString() + ">"
                        )
                        endList.add("<endif:$numOfEnd>")
                        ++numOfEnd
                        childId = IfBlockList[j].childId.value
                    }
                }
            }

            if (!hasChild) {
                for (j in 0 until ForBlockList.size) {
                    if (ForBlockList[j].thisID == childId) {
                        hasChild = true
                        commandList.add(
                            "<extendedfor:" + makeFirstToken(ForBlockList[j].initExpression.value) + makeSecondToken(
                                ForBlockList[j].condExpression.value
                            ) + makeFirstToken(ForBlockList[j].loopExpression.value) + numOfEnd.toString() + ">"
                        )
                        endList.add("<endextendedfor:$numOfEnd>")
                        ++numOfEnd
                        childId = ForBlockList[j].childId.value
                    }
                }
            }

            if (!hasChild) {
                for (j in 0 until CoutBlockList.size) {
                    if (CoutBlockList[j].thisID == childId) {
                        hasChild = true
                        commandList.add(
                            "<callout:<expression:${
                                normalizationOfExpression(
                                    CoutBlockList[j].variableName.value
                                )
                            }>>"
                        )
                        childId = CoutBlockList[j].childId.value
                    }
                }
            }

            if (!hasChild) {
                for (j in 0 until CinBlockList.size) {
                    if (CinBlockList[j].thisID == childId) {
                        hasChild = true
                        commandList.add("<callin:" + CinBlockList[j].variableName.value + ">")
                        childId = CinBlockList[j].childId.value
                    }
                }
            }

            if (!hasChild) {
                for (j in 0 until DoFunctionBlockList.size) {
                    if (DoFunctionBlockList[j].thisID == childId) {
                        hasChild = true
                        val functName = DoFunctionBlockList[j].FunctionName.value
                        val functParam = DoFunctionBlockList[j].FunctionParams.value
                        var exp = "$functName($functParam)"
                        exp = normalizationOfExpression(exp)
                        commandList.add("<callfunction:$exp>")
                        childId = DoFunctionBlockList[j].childId.value
                    }
                }
            }

            if (!hasChild) {
                for (j in 0 until EndBlockList.size) {
                    if (EndBlockList[j].thisID == childId) {
                        hasChild = true
                        commandList.add(endList[endList.size - 1])
                        endList.removeAt(endList.size - 1)
                        childId = EndBlockList[j].childId.value
                    }
                }
            }

            if (!hasChild) {
                for (j in 0 until ReturnBlockList.size) {
                    if (ReturnBlockList[j].thisID == childId) {
                        val exp = ReturnBlockList[j].ReturnString.value
                        commandList.add("<return:<expression:${normalizationOfExpression(exp)}>>")
                        childId = -1
                    }
                }
            }
        }

        commandList.add("<endfunction:${i.toString()}>")

        if (endList.size != 0) {
            doRun = false
            messagesCout.add("Programm hasn't ${endList.size.toString()} end block")
        }
    }

    hasChild = true
    childId = EndBeginBlockList[0].childId.value
    blockNumber = 0
    variablesList.clear()
    endList.clear()

    while (hasChild) {
        ++blockNumber
        hasChild = false

        if ((childId == -1) || (childId == 0)) {
            break
        }

        for (j in 0 until TypeVaribleList.size) {
            if (TypeVaribleList[j].thisID == childId) {
                hasChild = true
                commandList.add("<variable:" + TypeVaribleList[j].variableName.value + "," + TypeVaribleList[j].selectedType.value + ">")
                variablesList.add(
                    Variables(
                        TypeVaribleList[j].variableName.value,
                        TypeVaribleList[j].selectedType.value,
                    )
                )
                childId = TypeVaribleList[j].childId.value
            }
        }

        if (!hasChild) {
            for (j in 0 until ArrayVaribleList.size) {
                if (ArrayVaribleList[j].thisID == childId) {
                    hasChild = true
                    commandList.add("<truearray:" + ArrayVaribleList[j].variableName.value + "," + ArrayVaribleList[j].count.value + "," + ArrayVaribleList[j].selectedType.value + ">")
                    variablesList.add(
                        Variables(
                            ArrayVaribleList[j].variableName.value,
                            "array<" + ArrayVaribleList[j].selectedType.value + ">",
                        )
                    )
                    childId = ArrayVaribleList[j].childId.value
                }
            }
        }

        if (!hasChild) {
            for (j in 0 until VariableAssignmentList.size) {
                if (VariableAssignmentList[j].thisID == childId) {
                    hasChild = true
                    if (checkVariableName(VariableAssignmentList[j].variableName.value)) {
                        val type = returnVariableType(VariableAssignmentList[j].variableName.value)
                        val underType =
                            returnUnderVariableType(VariableAssignmentList[j].variableName.value)
                        if (underType == "Array") {
                            commandList.add("<equals:" + VariableAssignmentList[j].variableName.value + ",<anyexpression:" + normalizationOfExpression(VariableAssignmentList[j].variableValue.value) + ">>")
                        } else {
                            var name = VariableAssignmentList[j].variableName.value
                            if(underType == "ElementOfArray") {
                                name = normalizationElementOfArray(name)
                            }
                            if (type == "String") {
                                commandList.add("<equals:" + name + ",<stringexpression:" + normalizationOfExpression(VariableAssignmentList[j].variableValue.value) + ">>")
                            } else {
                                commandList.add("<equals:" + name + ",<expression:" + normalizationOfExpression(VariableAssignmentList[j].variableValue.value) + ">>")
                            }
                        }
                    } else {
                        doRun = false
                    }
                    childId = VariableAssignmentList[j].childId.value
                }
            }
        }

        if (!hasChild) {
            for (j in 0 until IfBlockList.size) {
                if (IfBlockList[j].thisID == childId) {
                    hasChild = true
                    commandList.add(
                        "<if:<expression:" + normalizationOfExpression(IfBlockList[j].conditionFirst.value) + ">,<expression:" + normalizationOfExpression(
                            IfBlockList[j].conditionSecond.value
                        ) + ">," + IfBlockList[j].selectedSign.value + "," + numOfEnd.toString() + ">"
                    )
                    endList.add("<endif:$numOfEnd>")
                    ++numOfEnd
                    childId = IfBlockList[j].childId.value
                }
            }
        }

        if (!hasChild) {
            for (j in 0 until ForBlockList.size) {
                if (ForBlockList[j].thisID == childId) {
                    hasChild = true
                    commandList.add(
                        "<extendedfor:" + makeFirstToken(ForBlockList[j].initExpression.value) + makeSecondToken(
                            ForBlockList[j].condExpression.value
                        ) + makeFirstToken(ForBlockList[j].loopExpression.value) + numOfEnd.toString() + ">"
                    )
                    endList.add("<endextendedfor:$numOfEnd>")
                    ++numOfEnd
                    childId = ForBlockList[j].childId.value
                }
            }
        }

        if (!hasChild) {
            for (j in 0 until CoutBlockList.size) {
                if (CoutBlockList[j].thisID == childId) {
                    hasChild = true
                    commandList.add("<callout:<expression:${normalizationOfExpression(CoutBlockList[j].variableName.value)}>>")
                    childId = CoutBlockList[j].childId.value
                }
            }
        }

        if (!hasChild) {
            for (j in 0 until EndBlockList.size) {
                if (EndBlockList[j].thisID == childId) {
                    hasChild = true
                    commandList.add(endList[endList.size - 1])
                    endList.removeAt(endList.size - 1)
                    childId = EndBlockList[j].childId.value
                }
            }
        }

        if (!hasChild) {
            for (j in 0 until CinBlockList.size) {
                if (CinBlockList[j].thisID == childId) {
                    hasChild = true
                    commandList.add("<callin:" + CinBlockList[j].variableName.value + ">")
                    childId = CinBlockList[j].childId.value
                }
            }
        }

        if (!hasChild) {
            for (j in 0 until DoFunctionBlockList.size) {
                if (DoFunctionBlockList[j].thisID == childId) {
                    hasChild = true
                    val functName = DoFunctionBlockList[j].FunctionName.value
                    val functParam = DoFunctionBlockList[j].FunctionParams.value
                    var exp = "$functName($functParam)"
                    exp = normalizationOfExpression(exp)
                    commandList.add("<callfunction:$exp>")
                    childId = DoFunctionBlockList[j].childId.value
                }
            }
        }

        if (!hasChild) {
            for (j in 0 until EndBlockList.size) {
                if (EndBlockList[j].thisID == childId) {
                    hasChild = true
                    commandList.add(endList[endList.size - 1])
                    endList.removeAt(endList.size - 1)
                    childId = EndBlockList[j].childId.value
                }
            }
        }
    }

    if (endList.size != 0) {
        doRun = false
        messagesCout.add("Programm hasn't ${endList.size.toString()} end block")
    }

    for (i in 0 until commandList.size) {
        Log.d("MyTag", commandList[i])
    }

    for (i in 0 until variablesList.size) {
        Log.d("MyTag", variablesList[i].variableType + " " + variablesList[i].variableName)
    }

    return commandList
}

fun normalizationPrametres(params: String): String {
    var normalParametres: String = ""
    val variableName = "([a-zA-Z][a-zA-Z0-9]*)"
    val variableNameRegex = variableName.toRegex()
    val variableType = "(((int)|(double)|(string))|(array<((int)|(double)|(string))>))"
    val variableTypeRegex = variableType.toRegex()
    val parameter = "($variableType[ ]+$variableName)"
    val parameterRegex = parameter.toRegex()
    var badString: String = params
    while (parameterRegex.find(badString) != null) {
        val matchExp = parameterRegex.find(badString)
        var matchedExp = matchExp?.value.toString()
        val matchType = variableTypeRegex.find(matchedExp)
        var matchedType = matchType?.value.toString()
        matchedExp = matchedType.toRegex().replaceFirst(matchedExp,"")
        val matchName = variableNameRegex.find(matchedExp)
        var matchedName = matchName?.value.toString()
        normalParametres += matchedName + ":" + matchedType + ","
        badString = parameterRegex.replaceFirst(badString, "")
    }
    normalParametres = ".$".toRegex().replaceFirst(normalParametres, "")
    return normalParametres
}
fun returnNumOfArg(exp: String): Int {
    var num: Int = 0;
    for(i in 0 until exp.length) {
        if(exp[i] == ':') {
            ++num
        }
    }
    return num
}

fun makeParametresList(params: String): List<String> {
    var namesList = mutableListOf<String>()
    val variableName = "([a-zA-Z][a-zA-Z0-9]*[:])"
    val variableNameRegex = variableName.toRegex()
    var exp = params
    while (variableNameRegex.find(exp) != null) {
        val matchExp = variableNameRegex.find(exp)
        val matchedExp = matchExp?.value.toString()
        namesList.add(matchedExp)
        exp = variableNameRegex.replaceFirst(exp, "")
    }
    return namesList
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

fun normalizationElementOfArray(name: String): String {
    val variableRegex = "([a-zA-Z][a-zA-Z0-9]*)"
    val pattern = variableRegex.toRegex()
    val nameOfElement: String = pattern.find(name).toString()
    var exp = name
    exp = pattern.replaceFirst(exp, "")
    exp = "^.".toRegex().replaceFirst(exp, "")
    exp = ".$".toRegex().replaceFirst(exp, "")
    exp = normalizationOfExpression(exp)
    return "$nameOfElement[$exp]"
}

fun checkVariableName(name: String): Boolean {
    for (i in 0 until variablesList.size) {
        if (variablesList[i].variableName == name) {
            return true
        }
    }
    return false
}

fun returnVariableType(name: String): String {
    var variab = name
    if ("\\[".toRegex().find(variab) != null) {
        val matchExp = "^[a-zA-Z][a-zA-Z0-9]".toRegex().find(variab)
        var matchedExp = matchExp?.value.toString()
        for (i in 0 until variablesList.size) {
            if (variablesList[i].variableName == matchedExp) {
                matchedExp = "array<".toRegex().replaceFirst(matchedExp, "")
                matchedExp = ".$".toRegex().replaceFirst(matchedExp, "")
                return matchedExp
            }
        }
    } else {
        for (i in 0 until variablesList.size) {
            if (variablesList[i].variableName == name) {
                return variablesList[i].variableType
            }
        }
    }
    return ""
}

fun returnUnderVariableType(name: String): String {
    if ("\\[".toRegex().find(name) != null) {
        return "ElementOfArray"
    } else {
        var type: String = ""
        for (i in 0 until variablesList.size) {
            if (variablesList[i].variableName == name) {
                type = variablesList[i].variableType
            }
        }
        if ("array".toRegex().find(name) != null) {
            return "Array"
        } else {
            return "Typical"
        }
    }
}

fun normalizationOfExpression(expression: String): String {
    val variableRegex = "([a-zA-Z][a-zA-Z0-9]*)"
    val numberRegex = "((([-])?[1-9][0-9]*([.][0-9]*[1-9])?)|(([-])?[0][.][0-9]*[1-9])|([0]))"
    val arrayRegex = "([a-zA-Z][a-zA-Z0-9]*\\[(([a-zA-Z][a-zA-Z0-9]*)|(([0])|([1-9][0-9]*)))\\])"
    val stringRegex = "(\\/\\/[^\\/ ]*\\/\\/)"
    val functionReg =
        "([a-zA-Z][a-zA-Z0-9]*\\((($variableRegex|$numberRegex|$arrayRegex|$stringRegex)([,]($variableRegex|$numberRegex|$arrayRegex|$stringRegex))*)?\\))"
    val pattern = functionReg.toRegex()
    var result = expression

    result = spaceRemove(result)

    while (pattern.find(result) != null) {
        val matchfunc = pattern.find(result)
        var matchedfunc = matchfunc?.value.toString()
        val matchnamefunc = variableRegex.toRegex().find(result)
        var matchednamefunc = matchnamefunc?.value.toString()
        matchedfunc = "([a-zA-Z][a-zA-Z0-9]*\\()".toRegex().replaceFirst(matchedfunc, "")
        matchedfunc = ".$".toRegex().replaceFirst(matchedfunc, "")
        matchedfunc += ","
        var numOfPar: Int = 0
        var parametersTypes = mutableListOf<String>()
        for (i in 0 until funList.size) {
            if (matchednamefunc == funList[i].funcName) {
                parametersTypes = funList[i].usageOfParams as MutableList<String>
                break
            }
        }
        var newFun: String = ""
        while ("^($variableRegex|$numberRegex|$arrayRegex|$stringRegex)[,]".toRegex()
                .find(matchedfunc) != null
        ) {
            val matchVar = "^($variableRegex|$numberRegex|$arrayRegex|$stringRegex)[,]".toRegex()
                .find(matchedfunc)
            var matchedVar = matchVar?.value.toString()
            matchedVar = ".$".toRegex().replaceFirst(matchedVar, "|")
            newFun += parametersTypes[numOfPar] + matchedVar
            numOfPar++
            matchedfunc = "^($variableRegex|$numberRegex|$arrayRegex|$stringRegex)[,]".toRegex()
                .replaceFirst(matchedfunc, "")
        }
        newFun = ".$".toRegex().replaceFirst(newFun,"")
        newFun = "$matchednamefunc<$newFun>"
        matchedfunc = matchfunc?.value.toString()
        result = pattern.replaceFirst(result, newFun)
    }

    return result
}

fun makeFirstToken(exp: String): String {
    var result = exp
    result = spaceRemove(result)
    result = normalizationOfExpression(result)

    val matchLeft = "^([^ ])+[=]".toRegex().find(result)
    var matchedLeft = matchLeft?.value.toString()
    result = matchedLeft.toRegex().replaceFirst(result, "")
    matchedLeft = "[=]$".toRegex().replaceFirst(matchedLeft, "")
    result = "<equals:$matchedLeft,<expression:$result>>;"
    return result
}

fun makeSecondToken(exp: String): String {
    val variableRegex = "([a-zA-Z][a-zA-Z0-9]*)"
    val numberRegex = "((([-])?[1-9][0-9]*([.][0-9]*[1-9])?)|(([-])?[0][.][0-9]*[1-9])|([0]))"
    val arrayRegex = "([a-zA-Z][a-zA-Z0-9]*\\[(([a-zA-Z][a-zA-Z0-9]*)|(([0])|([1-9][0-9]*)))\\])"
    val stringRegex = "(\\/\\/[^\\/ ]*\\/\\/)"
    val functionRegex =
        "([a-zA-Z][a-zA-Z0-9]*\\((($variableRegex|$numberRegex|$arrayRegex|$stringRegex)([,]($variableRegex|$numberRegex|$arrayRegex|$stringRegex))*)?\\))"
    val array =
        "([a-zA-Z][a-zA-Z0-9]*\\[(((\\()|(\\))|$variableRegex|$numberRegex|$arrayRegex|$stringRegex|$functionRegex|[\\+\\-\\/\\*])+)\\])"
    val expRegex =
        "(($variableRegex|$array|$numberRegex|$functionRegex|[\\+\\/\\*\\-]|(\\()|(\\)))+)"
    var result = exp

    val matchLeft = "^[ ]*($expRegex)".toRegex().find(result)
    var matchedLeft = matchLeft?.value.toString()
    result = matchedLeft.toRegex().replaceFirst(result, "")
    matchedLeft = "^[ ]*".toRegex().replaceFirst(matchedLeft, "")
    matchedLeft = normalizationOfExpression(matchedLeft)

    val matchRight = "($expRegex)[ ]*$".toRegex().find(result)
    var matchedRight = matchRight?.value.toString()
    result = matchedRight.toRegex().replaceFirst(result, "")
    matchedRight = "[ ]*$".toRegex().replaceFirst(matchedRight, "")
    matchedRight = normalizationOfExpression(matchedRight)

    result = "^[ ]*".toRegex().replaceFirst(result, "")
    result = "[ ]*$".toRegex().replaceFirst(result, "")

    return "<expression:$matchedLeft>;<expression:$matchedRight>;$result;"
}