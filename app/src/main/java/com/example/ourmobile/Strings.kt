package com.example.ourmobile

const val variableName = "([a-zA-Z][a-zA-Z0-9]*)"
const val variableType = "(((int)|(double)|(string))|(array<((int)|(double)|(string))>))"
const val variableTypeInList = "([:](((int)|(double)|(string))|(array<((int)|(double)|(string))>)))"
const val structName = "(([a-zA-Z][a-zA-Z0-9]*)|(([a-zA-Z][a-zA-Z0-9]*)[.]([a-zA-Z][a-zA-Z0-9]*)))"
const val numberRegex = "((([-])?[1-9][0-9]*([.][0-9]*[1-9])?)|(([-])?[0][.][0-9]*[1-9])|([0]))"
const val weakArrayRegex =
    "((([a-zA-Z][a-zA-Z0-9]*)|(([a-zA-Z][a-zA-Z0-9]*)[.]([a-zA-Z][a-zA-Z0-9]*)))\\[((([a-zA-Z][a-zA-Z0-9]*)|(([a-zA-Z][a-zA-Z0-9]*)[.]([a-zA-Z][a-zA-Z0-9]*)))|(([0])|([1-9][0-9]*)))\\])"
const val stringRegex = "(\\/\\/[^\\/ ]*\\/\\/)"
const val functionReg =
    "([a-zA-Z][a-zA-Z0-9]*\\((($variableName|$numberRegex|$weakArrayRegex|$stringRegex|$structName)([,]($variableName|$numberRegex|$weakArrayRegex|$stringRegex|$structName))*)?\\))"
const val parameter = "($variableType[ ]+$variableName)"
const val strongArrayRegex =
    "(($variableName|$structName)\\[(((\\()|(\\))|$variableName|$structName|$numberRegex|$weakArrayRegex|$stringRegex|$functionReg|[\\+\\-\\/\\*])+)\\])"
const val expRegex =
    "(($variableName|$strongArrayRegex|$numberRegex|$functionReg|[\\+\\/\\*\\-]|(\\()|(\\)))+)"
