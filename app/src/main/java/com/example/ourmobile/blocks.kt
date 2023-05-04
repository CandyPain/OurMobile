package com.example.ourmobile

import androidx.compose.animation.*
import androidx.compose.animation.core.*
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.input.pointer.consumeAllChanges
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.*
import kotlin.math.roundToInt

@OptIn(ExperimentalMaterial3Api::class)
/*
fun copyCard(card: Card): Card {
    val offsetX = card.offsetX.value
    val offsetY = card.offsetY.value
    val isDragging = card.isDragging.value
    val VariableName = card.VariableName.value
    val VariableValue = card.VariableValue.value

    return Card(
        modifier = Modifier
            .offset { IntOffset(offsetX.roundToInt(), offsetY.roundToInt()) }
            .width(500.dp)
            .padding(10.dp)
            .pointerInput(Unit) {
                detectDragGestures(
                    onDragStart = { isDragging.value = true },
                    onDragEnd = { isDragging.value = false },
                    onDragCancel = { /* не нужно ничего делать */ },
                    onDrag = { change, dragAmount ->
                        offsetX += dragAmount.x
                        offsetY += dragAmount.y
                        change.consumeAllChanges()
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
                    value = VariableName,
                    onValueChange = { newText -> /* не нужно делать ничего */ }
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
                    value = VariableValue,
                    onValueChange = { newText -> /* не нужно делать ничего */ }
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
*/
@Composable
fun DraggableText() {
    val offsetX = remember { mutableStateOf(0f) }
    val offsetY = remember { mutableStateOf(0f) }
    val isDragging = remember { mutableStateOf(false) }
    val text = remember { mutableStateOf("Text block") }

    Text(
        text = text.value,
        modifier = Modifier
            .offset { IntOffset(offsetX.value.roundToInt(), offsetY.value.roundToInt()) }
            .pointerInput(Unit) {
                detectDragGestures(
                    onDragStart = {
                        isDragging.value = true
                    },
                    onDragEnd = {
                        isDragging.value = false
                    },
                    onDrag = { change, dragAmount ->
                        offsetX.value += dragAmount.x
                        offsetY.value += dragAmount.y
                        change.consumeAllChanges()
                    }
                )
            }
    )
}


class SmallYellowCard {
    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    fun card() {
        Card(
            modifier = Modifier.size(100.dp)
        ) {
            Column(
                modifier = Modifier.padding(16.dp)
            ) {
                var variableName by remember { mutableStateOf("") }
                var variableValue by remember { mutableStateOf("") }

                TextField(
                    value = variableName,
                    onValueChange = { variableName = it },
                    label = { Text("Variable name") }
                )

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.padding(vertical = 8.dp)
                ) {
                    Text("=")
                    Spacer(modifier = Modifier.width(16.dp))
                    TextField(
                        value = variableValue,
                        onValueChange = { variableValue = it },
                        label = { Text("Variable value") }
                    )
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun VariableItem()
{
    val VariableType = remember{mutableStateOf("")}
    val VariableName = remember{mutableStateOf("")}
    val offsetX = remember { mutableStateOf(0f) }
    val offsetY = remember { mutableStateOf(0f) }
    val isDragging = remember { mutableStateOf(false) }
    Card(
        modifier = Modifier
            .offset { IntOffset(offsetX.value.roundToInt(), offsetY.value.roundToInt()) }
            .width(500.dp)
            .padding(10.dp)
            .pointerInput(Unit) {
                detectDragGestures(
                    onDragStart = { isDragging.value = true
                    },
                    onDragEnd = { isDragging.value = false },
                    onDragCancel = { /* не нужно ничего делать */ },
                    onDrag = { change, dragAmount ->
                        offsetX.value += dragAmount.x
                        offsetY.value += dragAmount.y
                        change.consumeAllChanges()
                    }
                )
            },
        shape = RoundedCornerShape(15.dp),
    ){
        Box(){
            Row()
            {
                TextField(
                    modifier = Modifier.width(200.dp),
                    textStyle = LocalTextStyle.current.copy(fontSize = 20.sp),
                    value = VariableType.value,
                    onValueChange = {newText -> VariableType.value = newText}
                )
                Text(
                    text = "   ",
                    fontSize = 20.sp
                )
                TextField(
                    modifier = Modifier.width(200.dp),
                    textStyle = LocalTextStyle.current.copy(fontSize = 20.sp),
                    value = VariableName.value,
                    onValueChange = {newText -> VariableName.value = newText}
                )
                Button(
                    onClick = {}
                )
                {
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun VariableAssignment(onCloseClicked: () -> Unit) {
    val offsetX = remember { mutableStateOf(0f) }
    val offsetY = remember { mutableStateOf(0f) }
    val isDragging = remember { mutableStateOf(false) }
    val VariableName = remember { mutableStateOf("") }
    val VariableValue = remember { mutableStateOf("") }

    Card(

        modifier = Modifier
            .offset { IntOffset(offsetX.value.roundToInt(), offsetY.value.roundToInt()) }
            .width(500.dp)
            .padding(10.dp)
            .pointerInput(Unit) {
                detectDragGestures(
                    onDragStart = { isDragging.value = true
                        },
                    onDragEnd = { isDragging.value = false },
                    onDragCancel = { /* не нужно ничего делать */ },
                    onDrag = { change, dragAmount ->
                        offsetX.value += dragAmount.x
                        offsetY.value += dragAmount.y
                        change.consumeAllChanges()
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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NewScreen(showNewScreen: Boolean, onCloseClicked: () -> Unit) {
    var selectedButton by remember { mutableStateOf(-1) }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .wrapContentSize(align = Alignment.TopCenter)
            .padding(top = 20.dp)
    ) {
        if (showNewScreen) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
            ) {
                for (i in 1..3) {
                    Button(
                        onClick = {
                            if (selectedButton == i) {
                                selectedButton = -1
                            } else {
                                selectedButton = i
                            }
                        },
                        modifier = Modifier
                            .padding(vertical = 8.dp)
                    ) {
                        Text("Button $i")
                    }
                    if (selectedButton == i) {
                        VariableAssignment(onCloseClicked = onCloseClicked)
                    }
                }
                Button(

                    onClick = onCloseClicked,
                    modifier = Modifier.padding(top = 16.dp)
                ) {
                    Text("Закрыть")
                }
            }
        } else {
            Button(
                onClick = {},
            ) {
                Text("Нажми меня еще раз")
            }
        }
    }
}