package com.example.ourmobile

import android.util.Log


class Variables(
    var variableName: String,
    var variableType: String,
)

class Function(
    var funname: String,
    var argumentList: String,
    var argumentUsage: List<String>,
    var argumentTypesUsage: MutableList<String>,
    var numOfArguments: Int,
    var retType: String
)

var variablesList = mutableListOf<Variables>()
var funList = mutableListOf<Function>()
var doRun: Boolean = true
var messagesCout = mutableListOf<String>()
var messagesCin: String = ""
var commandList = mutableListOf<String>()

fun runApp() {
    variablesList.clear()
    messagesCout.clear()
    funList.clear()
    doRun = true
    commandList = createCommandList()
    if (doRun) {
        var compiler = CelestialElysiaInterpreter(hashMapOf<String, Any>(), commandList)
        compiler.interprete()
        messagesCout += compiler.calloutList
    }
    for (i in 0 until messagesCout.size) {
        Log.d("MyTag", messagesCout[i])
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
        var checkFun: Boolean = true
        val functionName: String = FunctionBlockList[i].FunctionName.value
        checkFun = checkFunName(functionName)
        if (checkFun) {
            var functionArgument: String = FunctionBlockList[i].FunctionParams.value
            checkFun = checkFunMakeParam(functionArgument, functionName)
            if (checkFun) {
                functionArgument = normalizationPrametres(functionArgument)
                val parametresList = makeParametresList(functionArgument)
                val typesList = makeTypesList(functionArgument)
                val numOfParametres = typesList.size
                funList.add(
                    Function(
                        functionName,
                        functionArgument,
                        parametresList,
                        typesList as MutableList<String>,
                        numOfParametres,
                        FunctionBlockList[i].selectedType.value
                    )
                )
                commandList.add("<function:" + functionName + "," + FunctionBlockList[i].selectedType.value + "," + i.toString() + ";" + functionArgument + ">")
            } else {
                doRun = false
            }
        } else {
            doRun = false
        }

        if (checkFun) {
            hasChild = true
            childId = FunctionBlockList[i].childId.value
            blockNumber = 0
            variablesList.clear()
            endList.clear()
            variablesList.add(
                Variables(
                    "TheVariableToCheckTheBadWordVlojenostttt",
                    "Int",
                )
            )
            while (hasChild) {
                ++blockNumber
                hasChild = false

                if ((childId == -1) || (childId == 0)) {
                    break
                }

                for (j in 0 until TypeVaribleList.size) {
                    if (TypeVaribleList[j].thisID == childId) {
                        hasChild = true
                        if (checkMakeAVariable(
                                TypeVaribleList[j].variableName.value,
                                blockNumber
                            )
                        ) {
                            commandList.add("<variable:" + TypeVaribleList[j].variableName.value + "," + TypeVaribleList[j].selectedType.value + ">")
                            if (TypeVaribleList[j].selectedType.value == "String") {
                                variablesList.add(
                                    Variables(
                                        TypeVaribleList[j].variableName.value,
                                        TypeVaribleList[j].selectedType.value,
                                    )
                                )
                            } else {
                                variablesList.add(
                                    Variables(
                                        TypeVaribleList[j].variableName.value,
                                        TypeVaribleList[j].selectedType.value,
                                    )
                                )
                            }
                        } else {
                            doRun = false
                        }
                        childId = TypeVaribleList[j].childId.value
                    }
                }

                if (!hasChild) {
                    for (j in 0 until ArrayVaribleList.size) {
                        if (ArrayVaribleList[j].thisID == childId) {
                            hasChild = true
                            if (checkMakeAVariable(
                                    ArrayVaribleList[j].variableName.value,
                                    blockNumber
                                )
                            ) {
                                if (checkSizeOfArray(
                                        ArrayVaribleList[j].count.value,
                                        blockNumber
                                    )
                                ) {
                                    commandList.add("<truearray:" + ArrayVaribleList[j].variableName.value + "," + ArrayVaribleList[j].count.value + "," + ArrayVaribleList[j].selectedType.value + ">")
                                    variablesList.add(
                                        Variables(
                                            ArrayVaribleList[j].variableName.value,
                                            "array<" + ArrayVaribleList[j].selectedType.value + ">",
                                        )
                                    )
                                } else {
                                    doRun = false
                                }
                            } else {
                                doRun = false
                            }
                            childId = TypeVaribleList[j].childId.value
                        }
                    }
                }

                if (!hasChild) {
                    for (j in 0 until VariableAssignmentList.size) {
                        if (VariableAssignmentList[j].thisID == childId) {
                            hasChild = true

                            if (checkAssingmentAVariable(
                                    VariableAssignmentList[j].variableName.value,
                                    blockNumber
                                )
                            ) {
                                val varType: String =
                                    returnRandVariableType(VariableAssignmentList[j].variableName.value)
                                val varUnderType: String =
                                    returnUnderVariableType(VariableAssignmentList[j].variableName.value)
                                var newName: String = VariableAssignmentList[j].variableName.value
                                if (varUnderType == "ElementOfArray") {
                                    newName =
                                        normalizationElementOfArray(VariableAssignmentList[j].variableName.value)
                                }
                                if ((varType == "Double") || (varType == "int")) {
                                    if (checkANumberExpression(
                                            VariableAssignmentList[j].variableValue.value,
                                            blockNumber
                                        )
                                    ) {
                                        commandList.add(
                                            "<equals:" + newName + ",<expression:" + normalizationOfExpression(
                                                VariableAssignmentList[j].variableValue.value
                                            ) + ">>"
                                        )
                                    } else {
                                        doRun = false
                                    }
                                } else {
                                    if (varType == "String") {
                                        if (checkStringExp(
                                                VariableAssignmentList[j].variableValue.value,
                                                blockNumber
                                            )
                                        ) {
                                            commandList.add(
                                                "<equals:" + newName + ",<stringexpression:" + normalizationOfExpression(
                                                    VariableAssignmentList[j].variableValue.value
                                                ) + ">>"
                                            )
                                        } else {
                                            doRun = false
                                        }
                                    } else {
                                        if (varUnderType == "Array") {
                                            if (checkArrExp(
                                                    VariableAssignmentList[j].variableValue.value,
                                                    varType,
                                                    blockNumber
                                                )
                                            ) {
                                                commandList.add("<equals:" + newName + ",<anyexpression:" + VariableAssignmentList[j].variableValue.value + ">>")
                                            } else {
                                                doRun = false
                                            }
                                        }
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
                            var expFirst = IfBlockList[j].conditionFirst.value
                            var expSecond = IfBlockList[j].conditionSecond.value
                            if ((checkANumberExpression(
                                    expFirst,
                                    blockNumber
                                )) && (checkANumberExpression(expSecond, blockNumber))
                            ) {
                                commandList.add(
                                    "<if:<expression:" + normalizationOfExpression(
                                        expFirst
                                    ) + ">,<expression" + normalizationOfExpression(expSecond) + ">," + IfBlockList[j].selectedSign.value + "," + numOfEnd.toString() + ">"
                                )
                                endList.add("endif:$numOfEnd")
                                ++numOfEnd
                            } else {
                                doRun = false
                            }
                            childId = IfBlockList[j].childId.value
                        }
                    }
                }

                if (!hasChild) {
                    for (j in 0 until ForBlockList.size) {
                        if (ForBlockList[j].thisID == childId) {
                            hasChild = true

                            var initExp: String = ForBlockList[j].initExpression.value
                            var condExp: String = ForBlockList[j].condExpression.value
                            var loopExp: String = ForBlockList[j].loopExpression.value

                            if ((checkInitExpression(initExp, blockNumber)) && (checkCondExpression(
                                    condExp,
                                    blockNumber
                                )) && (checkInitExpression(loopExp, blockNumber))
                            ) {
                                commandList.add(
                                    "extendedfor:" + makeFirstToken(initExp) + makeSecondToken(
                                        condExp
                                    ) + makeFirstToken(loopExp) + numOfEnd.toString() + ">"
                                )
                                endList.add("endextendedfor:$numOfEnd")
                                ++numOfEnd
                            } else {
                                doRun = false
                            }

                            childId = ForBlockList[j].childId.value
                        }
                    }
                }

                if (!hasChild) {
                    for (j in 0 until CoutBlockList.size) {
                        if (CoutBlockList[j].thisID == childId) {
                            hasChild = true
                            val varName = CoutBlockList[j].variableName.value
                            if (checkANumberExpression(varName, blockNumber) || checkStringExp(
                                    varName,
                                    blockNumber
                                )
                            ) {
                                commandList.add(
                                    "<callout:<expression:${
                                        normalizationOfExpression(
                                            varName
                                        )
                                    }>>"
                                )
                            } else {
                                doRun = false
                            }
                            childId = CoutBlockList[j].childId.value
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
                            if (checkAFunction(exp, blockNumber)) {
                                exp = normalizationOfExpression(exp)
                                commandList.add("<callfunction:$exp>")
                            } else {
                                doRun = false
                            }
                            childId = DoFunctionBlockList[j].childId.value
                        }
                    }
                }

                if (!hasChild) {
                    for (j in 0 until ReturnBlockList.size) {
                        if (ReturnBlockList[j].thisID == childId) {
                            val exp = ReturnBlockList[j].ReturnString.value

                            if (checkANumberExpression(
                                    exp,
                                    blockNumber
                                ) && (FunctionBlockList[i].selectedType.value == "String")
                            ) {
                                commandList.add("<return:<expression:${normalizationOfExpression(exp)}>>")
                                commandList.add("<endfunction:${i.toString()}>")
                            } else {
                                if (checkStringExp(
                                        exp,
                                        blockNumber
                                    ) && (FunctionBlockList[i].selectedType.value == "String")
                                ) {
                                    commandList.add(
                                        "<return:<expression:${
                                            normalizationOfExpression(
                                                exp
                                            )
                                        }>>"
                                    )
                                    commandList.add("<endfunction:${i.toString()}>")
                                } else {
                                    doRun = false
                                }
                            }

                            childId = -1
                        }
                    }
                }
            }

            if (endList.size != 0) {
                doRun = false
                messagesCout.add("Programm hasn't ${endList.size.toString()} end block")
            }
        }
    }

    hasChild = true
    childId = EndBeginBlockList[0].childId.value
    blockNumber = 0
    variablesList.clear()
    endList.clear()
    variablesList.add(
        Variables(
            "TheVariableToCheckTheBadWordVlojenostttt",
            "Int",
        )
    )

    while (hasChild) {
        ++blockNumber
        hasChild = false

        if ((childId == -1) || (childId == 0)) {
            break
        }

        for (j in 0 until TypeVaribleList.size) {
            if (TypeVaribleList[j].thisID == childId) {
                hasChild = true
                if (checkMakeAVariable(
                        TypeVaribleList[j].variableName.value,
                        blockNumber
                    )
                ) {
                    commandList.add("<variable:" + TypeVaribleList[j].variableName.value + "," + TypeVaribleList[j].selectedType.value + ">")
                    if (TypeVaribleList[j].selectedType.value == "String") {
                        variablesList.add(
                            Variables(
                                TypeVaribleList[j].variableName.value,
                                TypeVaribleList[j].selectedType.value,
                            )
                        )
                    } else {
                        variablesList.add(
                            Variables(
                                TypeVaribleList[j].variableName.value,
                                TypeVaribleList[j].selectedType.value,
                            )
                        )
                    }
                } else {
                    doRun = false
                }
                childId = TypeVaribleList[j].childId.value
            }
        }

        if (!hasChild) {
            for (j in 0 until ArrayVaribleList.size) {
                if (ArrayVaribleList[j].thisID == childId) {
                    hasChild = true
                    if (checkMakeAVariable(
                            ArrayVaribleList[j].variableName.value,
                            blockNumber
                        )
                    ) {
                        if (checkSizeOfArray(
                                ArrayVaribleList[j].count.value,
                                blockNumber
                            )
                        ) {
                            commandList.add("<truearray:" + ArrayVaribleList[j].variableName.value + "," + ArrayVaribleList[j].count.value + "," + ArrayVaribleList[j].selectedType.value + ">")
                            variablesList.add(
                                Variables(
                                    ArrayVaribleList[j].variableName.value,
                                    "array<" + ArrayVaribleList[j].selectedType.value + ">",
                                )
                            )
                        } else {
                            doRun = false
                        }
                    } else {
                        doRun = false
                    }
                    childId = TypeVaribleList[j].childId.value
                }
            }
        }

        if (!hasChild) {
            for (j in 0 until VariableAssignmentList.size) {
                if (VariableAssignmentList[j].thisID == childId) {
                    hasChild = true

                    if (checkAssingmentAVariable(
                            VariableAssignmentList[j].variableName.value,
                            blockNumber
                        )
                    ) {
                        val varType: String =
                            returnRandVariableType(VariableAssignmentList[j].variableName.value)
                        val varUnderType: String =
                            returnUnderVariableType(VariableAssignmentList[j].variableName.value)
                        var newName: String = VariableAssignmentList[j].variableName.value
                        if (varUnderType == "ElementOfArray") {
                            newName =
                                normalizationElementOfArray(VariableAssignmentList[j].variableName.value)
                        }
                        if ((varType == "Double") || (varType == "int")) {
                            if (checkANumberExpression(
                                    VariableAssignmentList[j].variableValue.value,
                                    blockNumber
                                )
                            ) {
                                commandList.add(
                                    "<equals:" + newName + ",<expression:" + normalizationOfExpression(
                                        VariableAssignmentList[j].variableValue.value
                                    ) + ">>"
                                )
                            } else {
                                doRun = false
                            }
                        } else {
                            if (varType == "String") {
                                if (checkStringExp(
                                        VariableAssignmentList[j].variableValue.value,
                                        blockNumber
                                    )
                                ) {
                                    commandList.add(
                                        "<equals:" + newName + ",<stringexpression:" + normalizationOfExpression(
                                            VariableAssignmentList[j].variableValue.value
                                        ) + ">>"
                                    )
                                } else {
                                    doRun = false
                                }
                            } else {
                                if (varUnderType == "Array") {
                                    if (checkArrExp(
                                            VariableAssignmentList[j].variableValue.value,
                                            varType,
                                            blockNumber
                                        )
                                    ) {
                                        commandList.add("<equals:" + newName + ",<anyexpression:" + VariableAssignmentList[j].variableValue.value + ">>")
                                    } else {
                                        doRun = false
                                    }
                                }
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
                    var expFirst = IfBlockList[j].conditionFirst.value
                    var expSecond = IfBlockList[j].conditionSecond.value
                    if ((checkANumberExpression(
                            expFirst,
                            blockNumber
                        )) && (checkANumberExpression(expSecond, blockNumber))
                    ) {
                        commandList.add(
                            "<if:<expression:" + normalizationOfExpression(
                                expFirst
                            ) + ">,<expression" + normalizationOfExpression(expSecond) + ">," + IfBlockList[j].selectedSign.value + "," + numOfEnd.toString() + ">"
                        )
                        endList.add("endif:$numOfEnd")
                        ++numOfEnd
                    } else {
                        doRun = false
                    }
                    childId = IfBlockList[j].childId.value
                }
            }
        }

        if (!hasChild) {
            for (j in 0 until ForBlockList.size) {
                if (ForBlockList[j].thisID == childId) {
                    hasChild = true

                    var initExp: String = ForBlockList[j].initExpression.value
                    var condExp: String = ForBlockList[j].condExpression.value
                    var loopExp: String = ForBlockList[j].loopExpression.value

                    if ((checkInitExpression(initExp, blockNumber)) && (checkCondExpression(
                            condExp,
                            blockNumber
                        )) && (checkInitExpression(loopExp, blockNumber))
                    ) {
                        commandList.add(
                            "extendedfor:" + makeFirstToken(initExp) + makeSecondToken(
                                condExp
                            ) + makeFirstToken(loopExp) + numOfEnd.toString() + ">"
                        )
                        endList.add("endextendedfor:$numOfEnd")
                        ++numOfEnd
                    } else {
                        doRun = false
                    }

                    childId = ForBlockList[j].childId.value
                }
            }
        }

        if (!hasChild) {
            for (j in 0 until CoutBlockList.size) {
                if (CoutBlockList[j].thisID == childId) {
                    hasChild = true
                    val varName = CoutBlockList[j].variableName.value
                    if (checkANumberExpression(varName, blockNumber) || checkStringExp(
                            varName,
                            blockNumber
                        )
                    ) {
                        commandList.add("<callout:<expression:${normalizationOfExpression(varName)}>>")
                    } else {
                        doRun = false
                    }
                    childId = CoutBlockList[j].childId.value
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
                    if (checkAFunction(exp, blockNumber)) {
                        exp = normalizationOfExpression(exp)
                        commandList.add("<callfunction:$exp>")
                    } else {
                        doRun = false
                    }
                    childId = DoFunctionBlockList[j].childId.value
                }
            }
        }

    }

    for (i in 0 until commandList.size) {
        Log.d("MyTag", commandList[i])
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
    } else {
        for (i in 0 until variablesList.size) {
            if (variablesList[i].variableName == name) {
                checkResult = false
                messagesCout.add("In block number: $index variable named like this already exist")
                break
            }
        }
    }
    return checkResult
}

fun checkSizeOfArray(size: String, index: Int): Boolean {
    val sizeRegex = "(([1-9][0-9]*)|([0]))".toRegex()
    var sizeExp: String = size
    sizeExp = sizeRegex.replaceFirst(sizeExp, "")
    if (sizeExp == "") {
        return true
    }
    messagesCout.add("In block number: $index size of array is incorrect")
    return false
}

fun checkAssingmentANumberVariable(name: String, index: Int): Boolean {
    var checkResult: Boolean = true
    val variableRegex = "([a-zA-Z][a-zA-Z0-9]*)".toRegex()
    val arrayRegex = "^[a-zA-Z][a-zA-Z0-9]*\\[[.]+\\]$".toRegex()
    var result = name
    if (result == "") {
        messagesCout.add("In block number: $index name of variable is incorrect")
        return false
    }
    result = variableRegex.replaceFirst(result, "a")
    if (result != "a") {
        result = name
        result = arrayRegex.replaceFirst(result, "a")
        if (result == "a") {
            result = name
            result = "[a-zA-Z][a-zA-Z0-9]*\\[".toRegex().replaceFirst(result, "")
            result = ".&".toRegex().replaceFirst(result, "")
            result = spaceRemove(result)
            if (checkANumberExpression(result, index)) {
                result = name
                var matchResult = "[a-zA-Z][a-zA-Z0-9]*".toRegex().find(result)
                var matchedText = matchResult?.value.toString()
                if (checkVariableName(matchedText)) {
                    if ((returnVariableType(matchedText) != "array<Int>") && (returnVariableType(
                            matchedText
                        ) != "array<Double>")
                    ) {
                        messagesCout.add("In block number: $index type of variable is incorrect")
                        checkResult = false
                    }
                } else {
                    messagesCout.add("In block number: $index variable named like this isn't exist")
                    checkResult = false
                }
            } else {
                checkResult = false
            }
        } else {
            checkResult = false
            messagesCout.add("In block number: $index name of variable is incorrect")
        }
    } else {
        result = name
        if (checkVariableName(result)) {
            if ((returnVariableType(result) != "Int") && (returnVariableType(result) != "Double")) {
                messagesCout.add("In block number: $index type of variable is incorrect")
                checkResult = false
            }
        } else {
            messagesCout.add("In block number: $index variable named like this isn't exist")
            checkResult = false
        }
    }
    return checkResult
}

fun checkAssingmentAStringVariable(name: String, index: Int): Boolean {
    var checkResult: Boolean = true
    val variableRegex = "([a-zA-Z][a-zA-Z0-9]*)".toRegex()
    val arrayRegex = "^[a-zA-Z][a-zA-Z0-9]*\\[[.]+\\]$".toRegex()
    var result = name
    if (result == "") {
        messagesCout.add("In block number: $index name of variable is incorrect")
        return false
    }
    result = variableRegex.replaceFirst(result, "a")
    if (result != "a") {
        result = name
        result = arrayRegex.replaceFirst(result, "a")
        if (result == "a") {
            result = name
            result = "[a-zA-Z][a-zA-Z0-9]*\\[".toRegex().replaceFirst(result, "")
            result = ".&".toRegex().replaceFirst(result, "")
            result = spaceRemove(result)
            if (checkANumberExpression(result, index)) {
                result = name
                var matchResult = "[a-zA-Z][a-zA-Z0-9]*".toRegex().find(result)
                var matchedText = matchResult?.value.toString()
                if (checkVariableName(matchedText)) {
                    if (returnVariableType(matchedText) != "array<String>") {
                        messagesCout.add("In block number: $index type of variable is incorrect")
                        checkResult = false
                    }
                } else {
                    messagesCout.add("In block number: $index variable named like this isn't exist")
                    checkResult = false
                }
            } else {
                checkResult = false
            }
        } else {
            checkResult = false
            messagesCout.add("In block number: $index name of variable is incorrect")
        }
    } else {
        result = name
        if (checkVariableName(result)) {
            if (returnVariableType(result) != "String") {
                messagesCout.add("In block number: $index type of variable is incorrect")
                checkResult = false
            }
        } else {
            messagesCout.add("In block number: $index variable named like this isn't exist")
            checkResult = false
        }
    }
    return checkResult
}

fun checkAssingmentAVariable(name: String, index: Int): Boolean {
    var checkResult: Boolean = true
    val variableRegex = "([a-zA-Z][a-zA-Z0-9]*)".toRegex()
    val arrayRegex = "^[a-zA-Z][a-zA-Z0-9]*\\[[.]+\\]$".toRegex()
    var result = name
    if (result == "") {
        messagesCout.add("In block number: $index name of variable is incorrect")
        return false
    }
    result = variableRegex.replaceFirst(result, "a")
    if (result != "a") {
        result = name
        result = arrayRegex.replaceFirst(result, "a")
        if (result == "a") {
            result = name
            result = "[a-zA-Z][a-zA-Z0-9]*\\[".toRegex().replaceFirst(result, "")
            result = ".&".toRegex().replaceFirst(result, "")
            result = spaceRemove(result)
            if (checkANumberExpression(result, index)) {
                result = name
                val matchResult = "[a-zA-Z][a-zA-Z0-9]*".toRegex().find(result)
                val matchedText = matchResult?.value.toString()
                if (!checkVariableName(matchedText)) {
                    messagesCout.add("In block number: $index variable named like this isn't exist")
                    checkResult = false
                }
            } else {
                checkResult = false
            }
        } else {
            checkResult = false
            messagesCout.add("In block number: $index name of variable is incorrect")
        }
    } else {
        result = name
        if (!checkVariableName(result)) {
            messagesCout.add("In block number: $index variable named like this isn't exist")
            checkResult = false
        }
    }
    return checkResult
}

fun checkANumberExpression(expression: String, index: Int): Boolean {
    var checkResult: Boolean = true

    checkResult = evaluateParenthesisExpression(expression, index)

    if (checkResult) {
        checkResult = checkAVariables(expression, index)
    }

    if (checkResult) {
        checkResult = checkAFunction(expression, index)
    }

    return checkResult
}

fun evaluateParenthesisExpression(expression: String, index: Int): Boolean {
    val variableRegex = "([a-zA-Z][a-zA-Z0-9]*)"
    val numberRegex = "((([-])?[1-9][0-9]*([.][0-9]*[1-9])?)|(([-])?[0][.][0-9]*[1-9])|([0]))"
    val arrayRegex = "([a-zA-Z][a-zA-Z0-9]*\\[(([a-zA-Z][a-zA-Z0-9]*)|(([0])|([1-9][0-9]*)))\\])"
    val stringRegex = "(\\/\\/[^\\/ ]*\\/\\/)"
    val functionRegex =
        "([a-zA-Z][a-zA-Z0-9]*\\((($variableRegex|$numberRegex|$arrayRegex|$stringRegex)([,]($variableRegex|$numberRegex|$arrayRegex|$stringRegex))*)?\\))"
    val operandRegex =
        "(($variableRegex|$numberRegex|$arrayRegex|$functionRegex)[\\+\\-/*]($variableRegex|$numberRegex|$arrayRegex|$functionRegex))"
    val expressionRegex = "(($operandRegex)|([\\(]$operandRegex[\\)]))"
    val elementString = "($variableRegex|$numberRegex|$arrayRegex|$functionRegex)"
    val pattern = expressionRegex.toRegex()
    val elementRegex = elementString.toRegex()
    var result = expression
    if (result == "") {
        return false
    }
    result = elementRegex.replaceFirst(result, "a")
    if (result != "a") {
        result = expression
        while (pattern.find(result) != null) {
            result = pattern.replaceFirst(result, "a")
        }
        if (result == "a") {
            return true
        }
        messagesCout.add("In block number: $index expression is incorrect")
        return false
    }
    return true
}

fun checkAVariables(expression: String, index: Int): Boolean {
    var checkResult: Boolean = true
    val variableRegex = "([a-zA-Z][a-zA-Z0-9]*)"
    val numberRegex = "((([-])?[1-9][0-9]*([.][0-9]*[1-9])?)|(([-])?[0][.][0-9]*[1-9])|([0]))"
    val arrayRegex = "([a-zA-Z][a-zA-Z0-9]*\\[(([a-zA-Z][a-zA-Z0-9]*)|(([0])|([1-9][0-9]*)))\\])"
    val stringRegex = "(\\/\\/[^\\/ ]*\\/\\/)"
    val functionRegex =
        "([a-zA-Z][a-zA-Z0-9]*\\((($variableRegex|$numberRegex|$arrayRegex|$stringRegex)([,]($variableRegex|$numberRegex|$arrayRegex|$stringRegex))*)?\\))"
    val array =
        "([a-zA-Z][a-zA-Z0-9]*\\[(($variableRegex|(([0])|([1-9][0-9]*))|$arrayRegex|$stringRegex|$functionRegex)|((\\()|(\\))|$variableRegex|$numberRegex|$arrayRegex|$stringRegex|$functionRegex|[\\+\\-\\/\\*])+)\\])"
    val variableReg = variableRegex.toRegex()
    val arrayReg = array.toRegex()
    var result = expression

    while (arrayReg.find(result) != null) {
        val matchResult = arrayReg.find(result)
        val matchedText = matchResult?.value.toString()
        checkResult = checkAssingmentANumberVariable(matchedText, index)
        result = arrayReg.replaceFirst(result, "TheVariableToCheckTheBadWordVlojenostttt")
    }

    while (variableReg.find(result) != null) {
        val matchResult = variableReg.find(result)
        val matchedText = matchResult?.value.toString()
        checkResult = checkAssingmentANumberVariable(matchedText, index)
        result = variableReg.replaceFirst(result, "")
    }

    return checkResult
}

fun checkAFunction(expression: String, index: Int): Boolean {
    var checkResult: Boolean = true

    val variableRegex = "([a-zA-Z][a-zA-Z0-9]*)"
    val numberRegex = "((([-])?[1-9][0-9]*([.][0-9]*[1-9])?)|(([-])?[0][.][0-9]*[1-9])|([0]))"
    val arrayRegex = "([a-zA-Z][a-zA-Z0-9]*\\[(([a-zA-Z][a-zA-Z0-9]*)|(([0])|([1-9][0-9]*)))\\])"
    val stringRegex = "(\\/\\/[^\\/ ]*\\/\\/)"
    val functionRegex =
        "([a-zA-Z][a-zA-Z0-9]*((($variableRegex|$numberRegex|$arrayRegex|$stringRegex)([,]($variableRegex|$numberRegex|$arrayRegex|$stringRegex))*)?))"
    val pattern = functionRegex.toRegex()
    var result = expression

    while (pattern.find(result) != null) {
        val matchResult = pattern.find(result)
        var matchedText = matchResult?.value.toString()
        if ("([a-zA-Z][a-zA-Z0-9]*\\(\\))".toRegex().find(matchedText) != null) {
            matchedText = "..$".toRegex().replaceFirst(matchedText, "")
            var check: Boolean = false
            for (i in 0 until funList.size) {
                if (funList[i].funname == matchedText) {
                    if (((funList[i].retType == "Int") || (funList[i].retType == "Double")) && (funList[i].numOfArguments == 0)) {
                        check = true
                    }
                }
            }
            if (!check) {
                messagesCout.add("In block number: $index function retType isn't correct or function named like this didn't exist or num of parameters isn't correct")
                checkResult = false
            }
        } else {
            var tempString = matchedText
            var functionName: String = ""
            tempString =
                "(\\((($variableRegex|$numberRegex|$arrayRegex|$stringRegex)([,]($variableRegex|$numberRegex|$arrayRegex|$stringRegex))*)?\\))".toRegex()
                    .replaceFirst(tempString, "")
            var checkName: Boolean = false
            for (i in 0 until funList.size) {
                if ((funList[i].funname == tempString) && ((funList[i].retType == "Int") || (funList[i].retType == "Double"))) {
                    functionName = tempString
                    checkName = true
                    break
                }
            }
            if (checkName) {
                tempString = matchedText
                tempString = "([a-zA-Z][a-zA-Z0-9]*\\()".toRegex().replaceFirst(tempString, "")
                tempString = ".$".toRegex().replaceFirst(tempString, "")
                var numOfParametres: Int = 0
                while ("($variableRegex|$numberRegex|$arrayRegex|$stringRegex)".toRegex()
                        .find(tempString) != null
                ) {
                    ++numOfParametres
                    tempString = "($variableRegex|$numberRegex|$arrayRegex|$stringRegex)".toRegex()
                        .replaceFirst(tempString, "")
                }
                var checkCount: Boolean = false
                for (i in 0 until funList.size) {
                    if (funList[i].funname == functionName) {
                        if (funList[i].numOfArguments == numOfParametres) {
                            checkCount = true
                        }
                        break
                    }
                }
                if (checkCount) {
                    tempString = matchedText
                    tempString = "([a-zA-Z][a-zA-Z0-9]*\\()".toRegex().replaceFirst(tempString, "")
                    tempString = ".$".toRegex().replaceFirst(tempString, ",")
                    var listOfParameter = mutableListOf<String>()
                    for (i in 0 until funList.size) {
                        if (funList[i].funname == functionName) {
                            listOfParameter = funList[i].argumentTypesUsage
                            break
                        }
                    }
                    var listOfTypes = mutableListOf<String>()
                    var checkTypes: Boolean = true
                    while (("^($variableRegex|$numberRegex|$arrayRegex|$stringRegex)[,]".toRegex()
                            .find(tempString) != null) && (checkTypes)
                    ) {
                        val matchParam =
                            "^($variableRegex|$numberRegex|$arrayRegex|$stringRegex)[,]".toRegex()
                                .find(result)
                        var matchedParam = matchParam?.value.toString()
                        matchedParam = "[,]$".toRegex().replaceFirst(matchedParam, "")
                        if (numberRegex.toRegex().find(matchedParam) != null) {
                            listOfTypes.add(returnTypeOfNum(matchedParam))
                        }
                        if ((variableRegex.toRegex()
                                .find(matchedParam) != null) || (arrayRegex.toRegex()
                                .find(matchedParam) != null)
                        ) {
                            if (!checkAssingmentAVariable(matchedParam, index)) {
                                checkTypes = false
                            } else {
                                var typeOfVar: String = ""
                                if (arrayRegex.toRegex().find(matchedParam) != null) {
                                    matchedParam =
                                        "\\[[.]*\\]]".toRegex().replaceFirst(matchedParam, "")
                                    typeOfVar = returnVariableType(matchedParam)
                                    typeOfVar = "array<".toRegex().replaceFirst(typeOfVar, "")
                                    typeOfVar = ".$".toRegex().replaceFirst(typeOfVar, "")
                                } else {
                                    typeOfVar = returnVariableType(matchedParam)
                                }
                                listOfTypes.add(typeOfVar)
                            }
                        }
                        if (stringRegex.toRegex().find(matchedParam) != null) {
                            listOfTypes.add("String")
                        }
                        tempString = matchedParam.toRegex().replaceFirst(tempString, "")
                    }
                    for (i in 0 until listOfTypes.size) {
                        if (listOfTypes[i] != listOfParameter[i]) {
                            messagesCout.add("In block number: $index in function parameters isn't correct")
                            checkTypes = false
                            break
                        }
                    }
                    if (!checkTypes) {
                        checkResult = false
                    }
                } else {
                    messagesCout.add("In block number: $index in function num of parameters isn't correct")
                    checkResult = false
                }
            } else {
                messagesCout.add("In block number: $index function named like this didn't exist or type isn't correct")
                checkResult = false
            }
        }
    }

    return checkResult
}

fun checkAStringFunction(expression: String, index: Int): Boolean {
    var checkResult: Boolean = true

    val variableRegex = "([a-zA-Z][a-zA-Z0-9]*)"
    val numberRegex = "((([-])?[1-9][0-9]*([.][0-9]*[1-9])?)|(([-])?[0][.][0-9]*[1-9])|([0]))"
    val arrayRegex = "([a-zA-Z][a-zA-Z0-9]*\\[(([a-zA-Z][a-zA-Z0-9]*)|(([0])|([1-9][0-9]*)))\\])"
    val stringRegex = "(\\/\\/[^\\/ ]*\\/\\/)"
    val functionRegex =
        "([a-zA-Z][a-zA-Z0-9]*((($variableRegex|$numberRegex|$arrayRegex|$stringRegex)([,]($variableRegex|$numberRegex|$arrayRegex|$stringRegex))*)?))"
    val pattern = functionRegex.toRegex()
    var result = expression

    while (pattern.find(result) != null) {
        val matchResult = pattern.find(result)
        var matchedText = matchResult?.value.toString()
        if ("([a-zA-Z][a-zA-Z0-9]*\\(\\))".toRegex().find(matchedText) != null) {
            matchedText = "..$".toRegex().replaceFirst(matchedText, "")
            var check: Boolean = false
            for (i in 0 until funList.size) {
                if (funList[i].funname == matchedText) {
                    if ((funList[i].retType == "String") && (funList[i].numOfArguments == 0)) {
                        check = true
                    }
                }
            }
            if (!check) {
                messagesCout.add("In block number: $index function retType isn't correct or function named like this didn't exist or num of parameters isn't correct")
                checkResult = false
            }
        } else {
            var tempString = matchedText
            var functionName: String = ""
            tempString =
                "(\\((($variableRegex|$numberRegex|$arrayRegex|$stringRegex)([,]($variableRegex|$numberRegex|$arrayRegex|$stringRegex))*)?\\))".toRegex()
                    .replaceFirst(tempString, "")
            var checkName: Boolean = false
            for (i in 0 until funList.size) {
                if ((funList[i].funname == tempString) && (funList[i].retType == "String")) {
                    functionName = tempString
                    checkName = true
                    break
                }
            }
            if (checkName) {
                tempString = matchedText
                tempString = "([a-zA-Z][a-zA-Z0-9]*\\()".toRegex().replaceFirst(tempString, "")
                tempString = ".$".toRegex().replaceFirst(tempString, "")
                var numOfParametres: Int = 0
                while ("($variableRegex|$numberRegex|$arrayRegex|$stringRegex)".toRegex()
                        .find(tempString) != null
                ) {
                    ++numOfParametres
                    tempString = "($variableRegex|$numberRegex|$arrayRegex|$stringRegex)".toRegex()
                        .replaceFirst(tempString, "")
                }
                var checkCount: Boolean = false
                for (i in 0 until funList.size) {
                    if (funList[i].funname == functionName) {
                        if (funList[i].numOfArguments == numOfParametres) {
                            checkCount = true
                        }
                        break
                    }
                }
                if (checkCount) {
                    tempString = matchedText
                    tempString = "([a-zA-Z][a-zA-Z0-9]*\\()".toRegex().replaceFirst(tempString, "")
                    tempString = ".$".toRegex().replaceFirst(tempString, ",")
                    var listOfParameter = mutableListOf<String>()
                    for (i in 0 until funList.size) {
                        if (funList[i].funname == functionName) {
                            listOfParameter = funList[i].argumentTypesUsage
                            break
                        }
                    }
                    var listOfTypes = mutableListOf<String>()
                    var checkTypes: Boolean = true
                    while (("^($variableRegex|$numberRegex|$arrayRegex|$stringRegex)[,]".toRegex()
                            .find(tempString) != null) && (checkTypes)
                    ) {
                        val matchParam =
                            "^($variableRegex|$numberRegex|$arrayRegex|$stringRegex)[,]".toRegex()
                                .find(result)
                        var matchedParam = matchParam?.value.toString()
                        matchedParam = "[,]$".toRegex().replaceFirst(matchedParam, "")
                        if (numberRegex.toRegex().find(matchedParam) != null) {
                            listOfTypes.add(returnTypeOfNum(matchedParam))
                        }
                        if ((variableRegex.toRegex()
                                .find(matchedParam) != null) || (arrayRegex.toRegex()
                                .find(matchedParam) != null)
                        ) {
                            if (!checkAssingmentAVariable(matchedParam, index)) {
                                checkTypes = false
                            } else {
                                var typeOfVar: String = ""
                                if (arrayRegex.toRegex().find(matchedParam) != null) {
                                    matchedParam =
                                        "\\[[.]*\\]]".toRegex().replaceFirst(matchedParam, "")
                                    typeOfVar = returnVariableType(matchedParam)
                                    typeOfVar = "array<".toRegex().replaceFirst(typeOfVar, "")
                                    typeOfVar = ".$".toRegex().replaceFirst(typeOfVar, "")
                                } else {
                                    typeOfVar = returnVariableType(matchedParam)
                                }
                                listOfTypes.add(typeOfVar)
                            }
                        }
                        if (stringRegex.toRegex().find(matchedParam) != null) {
                            listOfTypes.add("String")
                        }
                        tempString = matchedParam.toRegex().replaceFirst(tempString, "")
                    }
                    for (i in 0 until listOfTypes.size) {
                        if (listOfTypes[i] != listOfParameter[i]) {
                            messagesCout.add("In block number: $index in function parameters isn't correct")
                            checkTypes = false
                            break
                        }
                    }
                    if (!checkTypes) {
                        checkResult = false
                    }
                } else {
                    messagesCout.add("In block number: $index in function num of parameters isn't correct")
                    checkResult = false
                }
            } else {
                messagesCout.add("In block number: $index function named like this didn't exist or type isn't correct")
                checkResult = false
            }
        }
    }

    return checkResult
}

fun checkFunName(name: String): Boolean {
    var checkResult: Boolean = true
    val variableRegex = "([a-zA-Z][a-zA-Z0-9]*)".toRegex()
    var result = name
    result = variableRegex.replaceFirst(result, "a")
    if (result != "a") {
        checkResult = false
        messagesCout.add("'$name' - is incorrect name for function")
    } else {
        for (i in 0 until funList.size) {
            if (funList[i].funname == name) {
                checkResult = false
                messagesCout.add("Function named like '$name' already exist")
                break
            }
        }
    }
    return checkResult
}

fun checkFunMakeParam(params: String, name: String): Boolean {
    var checkResult: Boolean = true
    val parameterRegex =
        "([ ]*(((Int)|(Double)|(String))|(array<((Int)|(Double)|(String))>))[ ]+([a-zA-Z][a-zA-Z0-9]*)[ ]*)"
    val allParametersRegex = "($parameterRegex([,]$parameterRegex)*)"
    val pattern = allParametersRegex.toRegex()
    var result = params

    if (result == "") {
        return checkResult
    }
    result = pattern.replaceFirst(result, "")
    if (result == "") {
        return checkResult
    }
    checkResult = false
    messagesCout.add("In function '$name' parametres is incorrect")
    return checkResult
}

fun normalizationPrametres(params: String): String {
    var normalParametres: String = ""
    val variableName = "([a-zA-Z][a-zA-Z0-9]*)"
    val variableNameRegex = variableName.toRegex()
    val variableType = "(((Int)|(Double)|(String))|(array<((Int)|(Double)|(String))>))"
    val variableTypeRegex = variableType.toRegex()
    val parameter = "($variableType[ ]+$variableName)"
    val parameterRegex = parameter.toRegex()
    var badString: String = params
    while (parameterRegex.find(badString) != null) {
        val matchExp = parameterRegex.find(badString)
        var matchedExp = matchExp?.value.toString()
        val matchName = variableNameRegex.find(matchedExp)
        var matchedName = matchName?.value.toString()
        val matchType = variableTypeRegex.find(matchedExp)
        var matchedType = matchType?.value.toString()
        normalParametres += matchedName + ":" + matchedType + ","
        badString = parameterRegex.replaceFirst(badString, "")
    }
    normalParametres = ".$".toRegex().replaceFirst(normalParametres, "")
    return normalParametres
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

fun makeTypesList(params: String): List<String> {
    var typesList = mutableListOf<String>()
    val variableTypes = "[:](((Int)|(Double)|(String))|(array<((Int)|(Double)|(String))>))"
    val variableTypesRegex = variableTypes.toRegex()
    var exp = params
    while (variableTypesRegex.find(exp) != null) {
        val matchExp = variableTypesRegex.find(exp)
        var matchedExp = matchExp?.value.toString()
        matchedExp = "^.".toRegex().replaceFirst(matchedExp, "")
        typesList.add(matchedExp)
        exp = variableTypesRegex.replaceFirst(exp, "")
    }
    return typesList
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

fun checkStringExp(exp: String, index: Int): Boolean {
    if (exp == "") {
        messagesCout.add("In block number: $index expression is incorrect")
        return false
    }
    var checkResult: Boolean = true
    val variableRegex = "([a-zA-Z][a-zA-Z0-9]*)"
    val numberRegex = "((([-])?[1-9][0-9]*([.][0-9]*[1-9])?)|(([-])?[0][.][0-9]*[1-9])|([0]))"
    val arrayRegex = "([a-zA-Z][a-zA-Z0-9]*\\[(([a-zA-Z][a-zA-Z0-9]*)|(([0])|([1-9][0-9]*)))\\])"
    val stringRegex = "(\\/\\/[^\\/ ]*\\/\\/)"
    val functionRegex =
        "([a-zA-Z][a-zA-Z0-9]*\\((($variableRegex|$numberRegex|$arrayRegex|$stringRegex)([,]($variableRegex|$numberRegex|$arrayRegex|$stringRegex))*)?\\))"
    val array =
        "([a-zA-Z][a-zA-Z0-9]*\\[(($variableRegex|(([0])|([1-9][0-9]*))|$arrayRegex|$stringRegex|$functionRegex)|((\\()|(\\))|$variableRegex|$numberRegex|$arrayRegex|$stringRegex|$functionRegex|[\\+\\-\\/\\*])+)\\])"

    val expRegex =
        "($variableRegex|$array|$functionRegex|$stringRegex)([+]($variableRegex|$array|$functionRegex|$stringRegex))*".toRegex()
    var result: String = exp
    result = spaceRemove(result)
    result = expRegex.replaceFirst(result, "")
    if (result == "") {
        result = exp
        while (stringRegex.toRegex().find(result) != null) {
            result = stringRegex.toRegex().replaceFirst(result, "")
        }
        while (array.toRegex().find(result) != null) {
            val matchExp = array.toRegex().find(result)
            val matchedExp = matchExp?.value.toString()
            checkResult = checkAssingmentAStringVariable(matchedExp, index)
            result = array.toRegex().replaceFirst(result, "")
        }
        while (variableRegex.toRegex().find(result) != null) {
            val matchExp = variableRegex.toRegex().find(result)
            val matchedExp = matchExp?.value.toString()
            checkResult = checkAssingmentAStringVariable(matchedExp, index)
            result = variableRegex.toRegex().replaceFirst(result, "")
        }
        while (functionRegex.toRegex().find(result) != null) {
            val matchExp = functionRegex.toRegex().find(result)
            val matchedExp = matchExp?.value.toString()
            checkResult = checkAStringFunction(matchedExp, index)
            result = functionRegex.toRegex().replaceFirst(result, "")
        }
        if (!checkResult) {
            messagesCout.add("In block number: $index expression is incorrect")
        }
        return checkResult
    } else {
        messagesCout.add("In block number: $index expression is incorrect")
        return false
    }
}

fun checkArrExp(exp: String, type: String, index: Int): Boolean {
    var check: Boolean = true
    val variableRegex = "([a-zA-Z][a-zA-Z0-9]*)"
    val pattern = variableRegex.toRegex()
    var result = exp
    result = pattern.replaceFirst(result, "a")
    if (result == "a") {
        result = exp
        return if (checkVariableName(result)) {
            if (returnVariableType(result) == type) {
                true
            } else {
                messagesCout.add("In block number: $index expression is incorrect")
                false
            }
        } else {
            messagesCout.add("In block number: $index expression is incorrect")
            false
        }
    } else {
        messagesCout.add("In block number: $index expression is incorrect")
        return false
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
            if (matchednamefunc == funList[i].funname) {
                parametersTypes = funList[i].argumentUsage as MutableList<String>
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
        newFun = "$matchednamefunc<$newFun>"
        matchedfunc = matchfunc?.value.toString()
        result = matchedfunc.toRegex().replaceFirst(result, newFun)
    }

    return result
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

fun checkInitExpression(exp: String, index: Int): Boolean {
    var checkResult: Boolean = true
    val variableRegex = "([a-zA-Z][a-zA-Z0-9]*)"
    val numberRegex = "((([-])?[1-9][0-9]*([.][0-9]*[1-9])?)|(([-])?[0][.][0-9]*[1-9])|([0]))"
    val arrayRegex = "([a-zA-Z][a-zA-Z0-9]*\\[(([a-zA-Z][a-zA-Z0-9]*)|(([0])|([1-9][0-9]*)))\\])"
    val stringRegex = "(\\/\\/[^\\/ ]*\\/\\/)"
    val functionRegex =
        "([a-zA-Z][a-zA-Z0-9]*\\((($variableRegex|$numberRegex|$arrayRegex|$stringRegex)([,]($variableRegex|$numberRegex|$arrayRegex|$stringRegex))*)?\\))"
    val array =
        "([a-zA-Z][a-zA-Z0-9]*\\[(((\\()|(\\))|$variableRegex|$numberRegex|$arrayRegex|$stringRegex|$functionRegex|[\\+\\-\\/\\*])+)\\])"
    val operandRegex =
        "(^[ ]*($variableRegex|$array)[ ]*[=][ ]*($variableRegex|$array|$numberRegex|$functionRegex|[\\+\\/\\*\\-]|(\\()|(\\)))+[ ]*$)"
    val pattern = operandRegex.toRegex()
    var result = exp

    if (result == "") {
        messagesCout.add("In block number $index initExpression is empty")
        return false
    }
    result = pattern.replaceFirst(result, "")
    if (result == "") {
        result = exp
        val matchLeft = "^[ ]*($variableRegex|$array)".toRegex().find(result)
        var matchedLeft = matchLeft?.value.toString()
        matchedLeft = "^[ ]*".toRegex().replaceFirst(matchedLeft, "")
        checkResult = checkAssingmentANumberVariable(matchedLeft, index)
        if (!checkResult) {
            messagesCout.add("In block number $index variable in initExpression is incorrect")
            return checkResult
        } else {
            val matchRight =
                "[=][ ]*($variableRegex|$array|$numberRegex|$functionRegex)[ ]*$".toRegex()
                    .find(result)
            var matchedRight = matchRight?.value.toString()
            matchedRight = "^[=][ ]*".toRegex().replaceFirst(matchedRight, "")
            matchedRight = "[ ]*$".toRegex().replaceFirst(matchedRight, "")
            checkResult = checkANumberExpression(matchedRight, index)
            if (!checkResult) {
                messagesCout.add("In block number $index expression in initExpression is incorrect")
                return checkResult
            } else {
                return checkResult
            }
        }
    } else {
        messagesCout.add("In block number $index initExpression is incorrect")
        return false
    }
}

fun checkCondExpression(exp: String, index: Int): Boolean {
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
    val operandRegex = "(^[ ]*$expRegex[ ]*([==]|[>=]|[<=]|[>]|[<]|[!=])[ ]*$expRegex[ ]*$)"
    val pattern = operandRegex.toRegex()
    var result = exp

    if (result == "") {
        messagesCout.add("In block number $index condExpression is empty")
        return false
    }

    result = pattern.replaceFirst(result, "")
    if (result == "") {
        result = exp
        val matchLeft = "^[ ]*($expRegex)".toRegex().find(result)
        var matchedLeft = matchLeft?.value.toString()
        matchedLeft = "^[ ]*".toRegex().replaceFirst(matchedLeft, "")
        val matchRight = "($expRegex)[ ]*$".toRegex().find(result)
        var matchedRight = matchRight?.value.toString()
        matchedRight = "[ ]*$".toRegex().replaceFirst(matchedRight, "")
        return (checkANumberExpression(matchedLeft, index)) && (checkANumberExpression(
            matchedRight,
            index
        ))
    } else {
        messagesCout.add("In block number $index condExpression is incorrect")
        return false
    }
}

fun makeFirstToken(exp: String): String {
    val variableRegex = "([a-zA-Z][a-zA-Z0-9]*)"
    val numberRegex = "((([-])?[1-9][0-9]*([.][0-9]*[1-9])?)|(([-])?[0][.][0-9]*[1-9])|([0]))"
    val arrayRegex = "([a-zA-Z][a-zA-Z0-9]*\\[(([a-zA-Z][a-zA-Z0-9]*)|(([0])|([1-9][0-9]*)))\\])"
    val stringRegex = "(\\/\\/[^\\/ ]*\\/\\/)"
    val functionRegex =
        "([a-zA-Z][a-zA-Z0-9]*\\((($variableRegex|$numberRegex|$arrayRegex|$stringRegex)([,]($variableRegex|$numberRegex|$arrayRegex|$stringRegex))*)?\\))"
    val array =
        "([a-zA-Z][a-zA-Z0-9]*\\[(((\\()|(\\))|$variableRegex|$numberRegex|$arrayRegex|$stringRegex|$functionRegex|[\\+\\-\\/\\*])+)\\])"
    val operandRegex =
        "(^[ ]*($variableRegex|$array)[ ]*[=][ ]*($variableRegex|$array|$numberRegex|$functionRegex|[\\+\\/\\*\\-]|(\\()|(\\)))+[ ]*$)"
    val pattern = operandRegex.toRegex()
    var result = exp

    val matchLeft = "^[ ]*($variableRegex|$array)".toRegex().find(result)
    var matchedLeft = matchLeft?.value.toString()
    matchedLeft = "^[ ]*".toRegex().replaceFirst(matchedLeft, "")

    if ("^$array$".toRegex().find(matchedLeft) != null) {
        matchedLeft = normalizationElementOfArray(matchedLeft)
    }

    val matchRight =
        "[=][ ]*($variableRegex|$array|$numberRegex|$functionRegex)[ ]*$".toRegex().find(result)
    var matchedRight = matchRight?.value.toString()
    matchedRight = "^[=][ ]*".toRegex().replaceFirst(matchedRight, "")
    matchedRight = "[ ]*$".toRegex().replaceFirst(matchedRight, "")
    matchedRight = normalizationOfExpression(matchedRight)

    result = "<equals:$matchedLeft,<expression:$matchedRight>>;"
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

fun returnVariableType(name: String): String {
    for (i in 0 until variablesList.size) {
        if (variablesList[i].variableName == name) {
            return variablesList[i].variableType
        }
    }
    return ""
}

fun returnRandVariableType(name: String): String {
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

fun returnTypeOfNum(num: String): String {
    if ("[.]".toRegex().find(num) != null) {
        return "Double"
    }
    return "Int"
}

fun checkVariableName(name: String): Boolean {
    for (i in 0 until variablesList.size) {
        if (variablesList[i].variableName == name) {
            return true
        }
    }
    return false
}