package com.example.ourmobile

import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.consumeAllChanges
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ourmobile.CardClass
import com.example.ourmobile.NeedClear
import kotlin.math.roundToInt

@Composable
fun BeginBlock(
    offsetX: MutableState<Float>,
    offsetY: MutableState<Float>,
    isDragging: MutableState<Boolean>,
    thisID: Int,
    CardList: MutableList<CardClass>,
) {
    Card(
        modifier = Modifier
            .offset { IntOffset(offsetX.value.roundToInt(), offsetY.value.roundToInt()) }
            .width(500.dp)
            .height(80.dp)
            .padding(2.dp)
            .pointerInput(Unit) {
                detectDragGestures(
                    onDragStart = {
                        isDragging.value = true
                    },
                    onDragEnd = { isDragging.value = false },
                    onDragCancel = { },
                    onDrag = { change, dragAmount ->
                        offsetX.value += dragAmount.x
                        offsetY.value += dragAmount.y
                        change.consumeAllChanges()
                        var i = CardList[thisID].childId.value;
                        while (i != -1) {
                            CardList[i].offsetY.value += dragAmount.y
                            CardList[i].offsetX.value += dragAmount.x
                            i = CardList[i].childId.value
                        }
                    }
                )
            },
        shape = RoundedCornerShape(15.dp)

    )
    {
        Box()
        {
            Text(
                text = "Main begin"
            )
        }
    }
}

@Composable
fun BeginBlockReal(
    offsetX: MutableState<Float>,
    offsetY: MutableState<Float>,
    isDragging: MutableState<Boolean>,
    thisID: Int,
    CardList: MutableList<CardClass>,
) {
    Card(
        modifier = Modifier
            .offset { IntOffset(offsetX.value.roundToInt(), offsetY.value.roundToInt()) }
            .width(500.dp)
            .height(80.dp)
            .padding(2.dp)
            .pointerInput(Unit) {
                detectDragGestures(
                    onDragStart = {
                        isDragging.value = true
                    },
                    onDragEnd = { isDragging.value = false },
                    onDragCancel = { },
                    onDrag = { change, dragAmount ->
                        offsetX.value += dragAmount.x
                        offsetY.value += dragAmount.y
                        change.consumeAllChanges()
                        var i = CardList[thisID].childId.value;
                        while (i != -1) {
                            CardList[i].offsetY.value += dragAmount.y
                            CardList[i].offsetX.value += dragAmount.x
                            i = CardList[i].childId.value
                        }
                    }
                )
            },
        shape = RoundedCornerShape(15.dp)

    )
    {
        Box()
        {
            Text(
                text = "begin"
            )
        }
    }
}

@Composable
fun EndBlockReal(
    offsetX: MutableState<Float>,
    offsetY: MutableState<Float>,
    isDragging: MutableState<Boolean>,
    thisID: Int,
    CardList: MutableList<CardClass>,
) {
    Card(
        modifier = Modifier
            .offset { IntOffset(offsetX.value.roundToInt(), offsetY.value.roundToInt()) }
            .width(500.dp)
            .height(80.dp)
            .padding(2.dp)
            .pointerInput(Unit) {
                detectDragGestures(
                    onDragStart = {
                        isDragging.value = true
                    },
                    onDragEnd = { isDragging.value = false },
                    onDragCancel = { },
                    onDrag = { change, dragAmount ->
                        offsetX.value += dragAmount.x
                        offsetY.value += dragAmount.y
                        change.consumeAllChanges()
                        var i = CardList[thisID].childId.value;
                        while (i != -1) {
                            CardList[i].offsetY.value += dragAmount.y
                            CardList[i].offsetX.value += dragAmount.x
                            i = CardList[i].childId.value
                        }
                    }
                )
            },
        shape = RoundedCornerShape(15.dp)

    )
    {
        Box()
        {
            Text(
                text = "end"
            )
        }
    }
}

@Composable
fun EndBlock(
    offsetX: MutableState<Float>,
    offsetY: MutableState<Float>,
    isDragging: MutableState<Boolean>,
    thisID: Int,
    CardList: MutableList<CardClass>,

    ) {
    Card(
        modifier = Modifier
            .offset { IntOffset(offsetX.value.roundToInt(), offsetY.value.roundToInt()) }
            .width(500.dp)
            .height(80.dp)
            .padding(2.dp)
            .pointerInput(Unit) {
                detectDragGestures(
                    onDragStart = {
                        isDragging.value = true
                    },
                    onDragEnd = { isDragging.value = false },
                    onDragCancel = { },
                    onDrag = { change, dragAmount ->
                        offsetX.value += dragAmount.x
                        offsetY.value += dragAmount.y
                        change.consumeAllChanges()
                        var i = CardList[thisID].childId.value;
                        while (i != -1) {
                            CardList[i].offsetY.value += dragAmount.y
                            CardList[i].offsetX.value += dragAmount.x
                            i = CardList[i].childId.value
                        }
                    }
                )
            },
        shape = RoundedCornerShape(15.dp)

    )
    {
        Box()
        {
            Text(
                text = "Main End"
            )
        }
    }
}

//Кард для создания переменной
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TypeVariableReal(
    offsetX: MutableState<Float>,
    offsetY: MutableState<Float>,
    isDragging: MutableState<Boolean>,
    expanded: MutableState<Boolean>,
    variableName: MutableState<String>,
    selectedType: MutableState<String>,
    thisID: Int,
    CardList: MutableList<CardClass>,
) {
    // Сохраненный тип переменной
    if (selectedType.value == "") {
        selectedType.value = "int"
    }
    // Сохраненное имя переменной
    if (variableName.value == "") {
        variableName.value = "NewVariable"
    }

    Card(
        modifier = Modifier
            .offset { IntOffset(offsetX.value.roundToInt(), offsetY.value.roundToInt()) }
            .width(500.dp)
            .height(80.dp)
            .padding(2.dp)
            .pointerInput(Unit) {
                detectDragGestures(
                    onDragStart = {
                        isDragging.value = true
                    },
                    onDragEnd = { isDragging.value = false },
                    onDragCancel = { },
                    onDrag = { change, dragAmount ->
                        offsetX.value += dragAmount.x
                        offsetY.value += dragAmount.y
                        change.consumeAllChanges()
                        var i = CardList[thisID].childId.value;
                        while (i != -1) {
                            CardList[i].offsetY.value += dragAmount.y
                            CardList[i].offsetX.value += dragAmount.x
                            i = CardList[i].childId.value
                        }
                    }
                )
            },
        shape = RoundedCornerShape(15.dp)

    )
    {
        Column {
            Row()
            {
                IconButton(onClick = { expanded.value = true })
                {
                    Icon(Icons.Filled.ArrowDropDown, contentDescription = null)
                }
                Text(
                    text = selectedType.value,
                    modifier = Modifier.padding(15.dp),
                    fontSize = 15.sp
                )
                DropdownMenu(
                    expanded = expanded.value,
                    onDismissRequest = { expanded.value = false }
                ) {
                    DropdownMenuItem(
                        text = { Text(text = "int") },
                        onClick = {
                            selectedType.value = "int"
                            // Изменять значение внешнего класса (типа переменной) здесь (при изменении дроп меню) именно через selectedType.value
                            expanded.value = false
                        })
                    DropdownMenuItem(
                        text = { Text(text = "double") },
                        onClick = {
                            selectedType.value = "double"
                            // Изменять значение внешнего класса (типа переменной) здесь (при изменении дроп меню) именно через selectedType.value
                            expanded.value = false
                        })
                    DropdownMenuItem(
                        text = { Text(text = "string") },
                        onClick = {
                            selectedType.value = "string"
                            // Изменять значение внешнего класса (типа переменной) здесь (при изменении дроп меню) именно через selectedType.value
                            expanded.value = false
                        })
                }
                Text(text = "   ", fontSize = 15.sp)
                TextField(
                    modifier = Modifier.width(200.dp),
                    textStyle = LocalTextStyle.current.copy(fontSize = 15.sp),
                    value = variableName.value,
                    onValueChange = { newText ->
                        variableName.value = newText
                        // Изменять значение внешнего класса (имени переменной) здесь (при изменении текст филда) именно через variableName.value
                    }
                )
                IconButton(onClick = { NeedClear.IdToClear = thisID
                    NeedClear.WhatList = 1})
                {
                    Icon(Icons.Filled.Close, contentDescription = null)
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ForBlockReal(
    offsetX: MutableState<Float>,
    offsetY: MutableState<Float>,
    isDragging: MutableState<Boolean>,
    thisID: Int,
    CardList: MutableList<CardClass>,
    initExpression: MutableState<String>,
    condExpression: MutableState<String>,
    loopExpression: MutableState<String>,
) {

    Card(
        modifier = Modifier
            .offset { IntOffset(offsetX.value.roundToInt(), offsetY.value.roundToInt()) }
            .width(500.dp)
            .padding(2.dp)
            .height(150.dp)
            .pointerInput(Unit) {
                detectDragGestures(
                    onDragStart = {
                        isDragging.value = true
                    },
                    onDragEnd = { isDragging.value = false },
                    onDragCancel = { },
                    onDrag = { change, dragAmount ->
                        offsetX.value += dragAmount.x
                        offsetY.value += dragAmount.y
                        change.consumeAllChanges()
                        var i = CardList[thisID].childId.value;
                        while (i != -1) {
                            CardList[i].offsetY.value += dragAmount.y
                            CardList[i].offsetX.value += dragAmount.x
                            i = CardList[i].childId.value
                        }
                    }
                )
            },
        shape = RoundedCornerShape(15.dp),
    ) {
        Column {
            Row()
            {
                Text(text = "For ", fontSize = 15.sp, modifier = Modifier.padding(15.dp))
                TextField(
                    modifier = Modifier.width(100.dp),
                    textStyle = LocalTextStyle.current.copy(fontSize = 15.sp),
                    value = initExpression.value,
                    onValueChange = { newText ->
                        initExpression.value = newText
                        // Изменять значение внешнего класса (пре-объявление переменной) здесь (при изменении текст филда) именно через initExpression.value
                    }
                )
                Text(text = " ", fontSize = 15.sp, modifier = Modifier.padding(15.dp))
                TextField(
                    modifier = Modifier.width(100.dp),
                    textStyle = LocalTextStyle.current.copy(fontSize = 15.sp),
                    value = condExpression.value,
                    onValueChange = { newText ->
                        condExpression.value = newText
                        // Изменять значение внешнего класса (условие цикла) здесь (при изменении текст филда) именно через condExpression.value
                    }
                )
                Text(text = " ", fontSize = 15.sp, modifier = Modifier.padding(15.dp))
                TextField(
                    modifier = Modifier.width(100.dp),
                    textStyle = LocalTextStyle.current.copy(fontSize = 15.sp),
                    value = loopExpression.value,
                    onValueChange = { newText ->
                        loopExpression.value = newText
                        // Изменять значение внешнего класса (действие цикла) здесь (при изменении текст филда) именно через loopExpression.value
                    }
                )
                Button(
                    modifier = Modifier.padding(5.dp),
                    onClick = {
                        // Действие для удаления блока
                    }
                )
                {
                    Text(text = "Del", fontSize = 15.sp)
                }
            }
            Text(text = "Do begin", fontSize = 15.sp, modifier = Modifier.padding(15.dp))
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CinBlockReal(
    offsetX: MutableState<Float>,
    offsetY: MutableState<Float>,
    isDragging: MutableState<Boolean>,
    variableName: MutableState<String>,
    thisID: Int,
    CardList: MutableList<CardClass>,
) {
    Card(
        modifier = Modifier
            .offset { IntOffset(offsetX.value.roundToInt(), offsetY.value.roundToInt()) }
            .width(400.dp)
            .padding(2.dp)
            .height(80.dp)
            .pointerInput(Unit) {
                detectDragGestures(
                    onDragStart = {
                        isDragging.value = true
                    },
                    onDragEnd = { isDragging.value = false },
                    onDragCancel = { },
                    onDrag = { change, dragAmount ->
                        offsetX.value += dragAmount.x
                        offsetY.value += dragAmount.y
                        change.consumeAllChanges()
                        var i = CardList[thisID].childId.value;
                        while (i != -1) {
                            CardList[i].offsetY.value += dragAmount.y
                            CardList[i].offsetX.value += dragAmount.x
                            i = CardList[i].childId.value
                        }
                    }
                )
            },
        shape = RoundedCornerShape(15.dp),
    ) {
        Row()
        {
            Text(text = "Cin ", fontSize = 15.sp, modifier = Modifier.padding(15.dp))
            TextField(
                modifier = Modifier.width(200.dp),
                textStyle = LocalTextStyle.current.copy(fontSize = 15.sp),
                value = variableName.value,
                onValueChange = { newText ->
                    variableName.value = newText
                    // Изменять значение внешнего класса (значение имени переменной) здесь (при изменении текст филда) именно через variableName.value
                }
            )
            Button(
                modifier = Modifier.padding(5.dp),
                onClick = {
                    // Действие для удаления блока
                }
            )
            {
                Text(text = "Del", fontSize = 15.sp)
            }
        }
    }
}

// Кард для вывода значения переменной
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CoutBlockReal(
    offsetX: MutableState<Float>,
    offsetY: MutableState<Float>,
    isDragging: MutableState<Boolean>,
    variableName: MutableState<String>,
    thisID: Int,
    CardList: MutableList<CardClass>,
) {

    Card(
        modifier = Modifier
            .offset { IntOffset(offsetX.value.roundToInt(), offsetY.value.roundToInt()) }
            .width(400.dp)
            .height(80.dp)
            .padding(2.dp)
            .pointerInput(Unit) {
                detectDragGestures(
                    onDragStart = {
                        isDragging.value = true
                    },
                    onDragEnd = { isDragging.value = false },
                    onDragCancel = { },
                    onDrag = { change, dragAmount ->
                        offsetX.value += dragAmount.x
                        offsetY.value += dragAmount.y
                        change.consumeAllChanges()
                        var i = CardList[thisID].childId.value;
                        while (i != -1) {
                            CardList[i].offsetY.value += dragAmount.y
                            CardList[i].offsetX.value += dragAmount.x
                            i = CardList[i].childId.value
                        }
                    }
                )
            },
        shape = RoundedCornerShape(15.dp),
    ) {
        Row()
        {
            Text(text = "Cout ", fontSize = 15.sp, modifier = Modifier.padding(15.dp))
            TextField(
                modifier = Modifier.width(200.dp),
                textStyle = LocalTextStyle.current.copy(fontSize = 15.sp),
                value = variableName.value,
                onValueChange = { newText ->
                    variableName.value = newText
                    // Изменять значение внешнего класса (значение имени переменной) здесь (при изменении текст филда) именно через variableName.value
                }
            )
            Button(
                modifier = Modifier.padding(5.dp),
                onClick = {
                    // Действие для удаления блока
                }
            )
            {
                Text(text = "Del", fontSize = 15.sp)
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun VariableAssignmentReal(
    offsetX: MutableState<Float>,
    offsetY: MutableState<Float>,
    isDragging: MutableState<Boolean>,
    VariableName: MutableState<String>,
    VariableValue: MutableState<String>,
    thisID: Int,
    CardList: MutableList<CardClass>,
) {
    Card(
        modifier = Modifier
            .offset { IntOffset(offsetX.value.roundToInt(), offsetY.value.roundToInt()) }
            .width(500.dp)
            .height(80.dp)
            .padding(2.dp)
            .pointerInput(Unit) {
                detectDragGestures(
                    onDragStart = {
                        isDragging.value = true
                    },
                    onDragEnd = { isDragging.value = false },
                    onDragCancel = { },
                    onDrag = { change, dragAmount ->
                        offsetX.value += dragAmount.x
                        offsetY.value += dragAmount.y
                        change.consumeAllChanges()
                        var i = CardList[thisID].childId.value;
                        while (i != -1) {
                            CardList[i].offsetY.value += dragAmount.y
                            CardList[i].offsetX.value += dragAmount.x
                            i = CardList[i].childId.value
                        }
                    }
                )
            },
        shape = RoundedCornerShape(15.dp),
    ) {
        Box(
        ) {
            Row() {
                TextField(
                    modifier = Modifier
                        .width(200.dp)
                        .padding(10.dp),
                    textStyle = LocalTextStyle.current.copy(fontSize = 20.sp),
                    value = VariableName.value,
                    onValueChange = { newText -> VariableName.value = newText }
                )
                Text(
                    text = " = ",
                    fontSize = 20.sp,
                    modifier = Modifier.padding(10.dp)
                )
                TextField(
                    modifier = Modifier
                        .width(200.dp)
                        .padding(10.dp),
                    textStyle = LocalTextStyle.current.copy(fontSize = 20.sp),
                    value = VariableValue.value,
                    onValueChange = { newText -> VariableValue.value = newText }
                )
                Button(
                    onClick = {},
                    modifier = Modifier.padding(10.dp)
                ) {
                    Text(text = "Save")
                }
            }
        }
    }
}

//Кард для ифа
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun IfBlockReal(
    offsetX: MutableState<Float>,
    offsetY: MutableState<Float>,
    isDragging: MutableState<Boolean>,
    conditionFirst: MutableState<String>,
    conditionSecond: MutableState<String>,
    expanded: MutableState<Boolean>,
    selectedSign: MutableState<String>,
    thisID: Int,
    CardList: MutableList<CardClass>,
) {

    Card(
        modifier = Modifier
            .offset { IntOffset(offsetX.value.roundToInt(), offsetY.value.roundToInt()) }
            .width(500.dp)
            .padding(2.dp)
            .height(130.dp)
            .pointerInput(Unit) {
                detectDragGestures(
                    onDragStart = {
                        isDragging.value = true
                    },
                    onDragEnd = { isDragging.value = false },
                    onDragCancel = { },
                    onDrag = { change, dragAmount ->
                        offsetX.value += dragAmount.x
                        offsetY.value += dragAmount.y
                        change.consumeAllChanges()
                        var i = CardList[thisID].childId.value;
                        while (i != -1) {
                            CardList[i].offsetY.value += dragAmount.y
                            CardList[i].offsetX.value += dragAmount.x
                            i = CardList[i].childId.value
                        }
                    }
                )
            },
        shape = RoundedCornerShape(15.dp),
    ) {
        Column {
            Row()
            {
                Text(text = "If ", modifier = Modifier.padding(15.dp), fontSize = 15.sp)
                TextField(
                    modifier = Modifier.width(200.dp),
                    textStyle = LocalTextStyle.current.copy(fontSize = 15.sp),
                    value = conditionFirst.value,
                    onValueChange = { newText ->
                        conditionFirst.value = newText
// Изменять значение внешнего класса (условия ифа) здесь (при изменении текст филда) именно через condition.value
                    }
                )
                IconButton(onClick = { expanded.value = true })
                {
                    Icon(Icons.Filled.ArrowDropDown, contentDescription = null)
                }
                Text(
                    text = selectedSign.value,
                    modifier = Modifier.padding(15.dp),
                    fontSize = 15.sp
                )
                DropdownMenu(
                    expanded = expanded.value,
                    onDismissRequest = { expanded.value = false }
                ) {
                    DropdownMenuItem(
                        text = { Text(text = "=") },
                        onClick = {
                            selectedSign.value = "="
                            expanded.value = false
                        })
                    DropdownMenuItem(
                        text = { Text(text = "!=") },
                        onClick = {
                            selectedSign.value = "!="
                            expanded.value = false
                        })
                    DropdownMenuItem(
                        text = { Text(text = ">") },
                        onClick = {
                            selectedSign.value = ">"
                            expanded.value = false
                        })
                    DropdownMenuItem(
                        text = { Text(text = ">=") },
                        onClick = {
                            selectedSign.value = ">="
                            expanded.value = false
                        })
                    DropdownMenuItem(
                        text = { Text(text = "<") },
                        onClick = {
                            selectedSign.value = "<"
                            expanded.value = false
                        })
                    DropdownMenuItem(
                        text = { Text(text = "<=") },
                        onClick = {
                            selectedSign.value = "<="
                            expanded.value = false
                        })
                }
                TextField(
                    modifier = Modifier.width(200.dp),
                    textStyle = LocalTextStyle.current.copy(fontSize = 15.sp),
                    value = conditionSecond.value,
                    onValueChange = { newText ->
                        conditionSecond.value = newText
// Изменять значение внешнего класса (условия ифа) здесь (при изменении текст филда) именно через condition.value
                    }
                )
                Button(
                    modifier = Modifier.padding(5.dp),
                    onClick = {
// Действие для удаления блока
                    }
                )
                {
                    Text(text = "Del", fontSize = 15.sp)
                }
            }
            Text(text = "Then begin", fontSize = 15.sp, modifier = Modifier.padding(15.dp))
        }
    }
}