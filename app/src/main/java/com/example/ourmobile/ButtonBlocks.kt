package com.example.ourmobile

import androidx.compose.animation.*
import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.consumeAllChanges
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.*
import kotlin.math.roundToInt

var myGlobalNumber by mutableStateOf(0);

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
fun VariableItem() {
    val VariableType = remember { mutableStateOf("") }
    val VariableName = remember { mutableStateOf("") }
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
                    onDragStart = {
                        isDragging.value = true
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
        Box() {
            Row()
            {
                TextField(
                    modifier = Modifier.width(200.dp),
                    textStyle = LocalTextStyle.current.copy(fontSize = 20.sp),
                    value = VariableType.value,
                    onValueChange = { newText -> VariableType.value = newText }
                )
                Text(
                    text = "   ",
                    fontSize = 20.sp
                )
                TextField(
                    modifier = Modifier.width(200.dp),
                    textStyle = LocalTextStyle.current.copy(fontSize = 20.sp),
                    value = VariableName.value,
                    onValueChange = { newText -> VariableName.value = newText }
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
            .height(80.dp)
            .padding(2.dp)
            .clickable {
                myGlobalNumber = 2;
                onCloseClicked()

            },
        shape = RoundedCornerShape(15.dp),
    ) {
        Box() {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                TextField(
                    modifier = Modifier
                        .weight(1f)
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
                        .weight(1f)
                        .padding(10.dp),
                    textStyle = LocalTextStyle.current.copy(fontSize = 20.sp),
                    value = VariableValue.value,
                    onValueChange = { newText -> VariableValue.value = newText }
                )
            }
        }
    }
}

//Кард для создания переменной
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TypeVariable(onCloseClicked: () -> Unit) {
    var expanded by remember { mutableStateOf(false) }
    val variableName = remember { mutableStateOf("") }
    val selectedType = remember { mutableStateOf("") }

    // Сохраненный тип переменной
    selectedType.value = "int"
    // Сохраненное имя переменной
    variableName.value = "NewVariable"

    Card(
        modifier = Modifier
            .width(500.dp)
            .height(80.dp)
            .padding(2.dp)
            .clickable {
                myGlobalNumber = 1;
                onCloseClicked()

            },
        shape = RoundedCornerShape(15.dp),

        )
    {
        Column {
            Row()
            {
                IconButton(onClick = { expanded = true })
                {
                    Icon(Icons.Filled.ArrowDropDown, contentDescription = null)
                }
                Text(
                    text = selectedType.value,
                    modifier = Modifier.padding(15.dp),
                    fontSize = 15.sp
                )
                DropdownMenu(
                    expanded = expanded,
                    onDismissRequest = { expanded = false }
                ) {
                    DropdownMenuItem(
                        text = { Text(text = "int") },
                        onClick = {
                            selectedType.value = "int"
                            // Изменять значение внешнего класса (типа переменной) здесь (при изменении дроп меню) именно через selectedType.value
                            expanded = false
                        })
                    DropdownMenuItem(
                        text = { Text(text = "double") },
                        onClick = {
                            selectedType.value = "double"
                            // Изменять значение внешнего класса (типа переменной) здесь (при изменении дроп меню) именно через selectedType.value
                            expanded = false
                        })
                    DropdownMenuItem(
                        text = { Text(text = "string") },
                        onClick = {
                            selectedType.value = "string"
                            // Изменять значение внешнего класса (типа переменной) здесь (при изменении дроп меню) именно через selectedType.value
                            expanded = false
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
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ArrayVariable(onCloseClicked: () -> Unit) {
    var expanded by remember { mutableStateOf(false) }
    val variableName = remember { mutableStateOf("") }
    val selectedType = remember { mutableStateOf("") }
    val count = remember { mutableStateOf("") }

    // Сохраненный тип переменной
    selectedType.value = "int"
    // Сохраненное имя переменной
    variableName.value = "NewVariable"

    Card(
        modifier = Modifier
            .width(500.dp)
            .height(120.dp)
            .padding(10.dp)
            .clickable {
                myGlobalNumber = 7;
                onCloseClicked()
            },
        shape = RoundedCornerShape(15.dp)

    )
    {
        Column {
            Row(verticalAlignment = Alignment.CenterVertically) {
                IconButton(onClick = { expanded = true }) {
                    Icon(Icons.Filled.ArrowDropDown, contentDescription = null)
                }
                Text(text = "Array", fontSize = 16.sp, fontWeight = FontWeight.Bold)
                Spacer(modifier = Modifier.width(8.dp))
                DropdownMenu(
                    expanded = expanded,
                    onDismissRequest = { expanded = false }
                ) {
                    DropdownMenuItem(
                        text = { Text(text = "int") },
                        onClick = {
                            selectedType.value = "int"
                            expanded = false
                        })
                    DropdownMenuItem(
                        text = { Text(text = "double") },
                        onClick = {
                            selectedType.value = "double"
                            expanded = false
                        })
                }
                Spacer(modifier = Modifier.width(16.dp))
                Text(text = "Name:", fontSize = 15.sp)
                Spacer(modifier = Modifier.width(8.dp))
                TextField(
                    modifier = Modifier.width(200.dp),
                    textStyle = LocalTextStyle.current.copy(fontSize = 15.sp),
                    value = variableName.value,
                    onValueChange = { newText ->
                        variableName.value = newText
                    }
                )
                Spacer(modifier = Modifier.width(16.dp))
                Text(text = "Count:", fontSize = 15.sp)
                Spacer(modifier = Modifier.width(8.dp))
                TextField(
                    modifier = Modifier.width(100.dp),
                    textStyle = LocalTextStyle.current.copy(fontSize = 15.sp),
                    value = count.value,
                    onValueChange = { /* Handle count value change */ }
                )
                Spacer(modifier = Modifier.width(16.dp))
                IconButton(
                    onClick = {
                        //NeedClear.IdToClear = thisID
                        //NeedClear.WhatList = 1
                    }
                ) {
                    Icon(Icons.Filled.Close, contentDescription = null)
                }
            }
        }
    }
}

//Кард для ифа
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun IfBlock(onCloseClicked: () -> Unit) {
    var expanded by remember { mutableStateOf(false) }
    val conditionFirst = remember { mutableStateOf("") }
    val conditionSecond = remember { mutableStateOf("") }
    var selectedSign by remember { mutableStateOf("") }
    Card(
        modifier = Modifier
            .width(500.dp)
            .height(150.dp)
            .padding(8.dp)
            .clickable {
                myGlobalNumber = 3;
                onCloseClicked()
            },
        shape = RoundedCornerShape(15.dp),
    ) {
        Column(
            Modifier.padding(16.dp)
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(
                    text = "If",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(end = 8.dp)
                )
                TextField(
                    modifier = Modifier
                        .weight(1f)
                        .padding(end = 8.dp),
                    textStyle = LocalTextStyle.current.copy(fontSize = 15.sp),
                    value = conditionFirst.value,
                    onValueChange = { newText ->
                        conditionFirst.value = newText
                    }
                )
                IconButton(onClick = { expanded = true }) {
                    Icon(Icons.Filled.ArrowDropDown, contentDescription = null)
                }
                Text(
                    text = selectedSign,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(horizontal = 8.dp)
                )
                DropdownMenu(
                    expanded = expanded,
                    onDismissRequest = { expanded = false }
                ) {
                    DropdownMenuItem(
                        text = { Text(text = "==") },
                        onClick = {
                            selectedSign = "=="
                            expanded = false
                        })
                    DropdownMenuItem(
                        text = { Text(text = "!=") },
                        onClick = {
                            selectedSign = "!="
                            expanded = false
                        })
                    DropdownMenuItem(
                        text = { Text(text = ">") },
                        onClick = {
                            selectedSign = ">"
                            expanded = false
                        })
                    DropdownMenuItem(
                        text = { Text(text = ">=") },
                        onClick = {
                            selectedSign = ">="
                            expanded = false
                        })
                    DropdownMenuItem(
                        text = { Text(text = "<") },
                        onClick = {
                            selectedSign = "<"
                            expanded = false
                        })
                    DropdownMenuItem(
                        text = { Text(text = "<=") },
                        onClick = {
                            selectedSign = "<="
                            expanded = false
                        })
                }
                TextField(
                    modifier = Modifier
                        .weight(1f)
                        .padding(start = 8.dp),
                    textStyle = LocalTextStyle.current.copy(fontSize = 15.sp),
                    value = conditionSecond.value,
                    onValueChange = { newText ->
                        conditionSecond.value = newText
                    }
                )
            }
            Text(
                text = "Then begin",
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(top = 8.dp)
            )
        }
    }
}


//Кард для фора
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ForBlock(onCloseClicked: () -> Unit) {
    val initExpression = remember { mutableStateOf("") }
    val condExpression = remember { mutableStateOf("") }
    val loopExpression = remember { mutableStateOf("") }

    // Сохраненное пре-объявление переменной
    initExpression.value = ""
    // Сохраненное условие цикла
    condExpression.value = ""
    // Сохраненное действие цикла
    loopExpression.value = ""

    Card(
        modifier = Modifier
            .width(250.dp)
            .padding(2.dp)
            .height(200.dp)
            .clickable {
                myGlobalNumber = 4;
                onCloseClicked()
            },
        shape = RoundedCornerShape(15.dp),
    ) {
        Column(Modifier.padding(16.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(text = "For", fontSize = 16.sp, fontWeight = FontWeight.Bold)
                Spacer(modifier = Modifier.width(8.dp))
                TextField(
                    modifier = Modifier.width(200.dp),
                    textStyle = LocalTextStyle.current.copy(fontSize = 15.sp),
                    value = initExpression.value,
                    onValueChange = { newText ->
                        initExpression.value = newText
                    }
                )
            }
            Spacer(modifier = Modifier.height(8.dp))
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(text = "to", fontSize = 16.sp, fontWeight = FontWeight.Bold)
                Spacer(modifier = Modifier.width(8.dp))
                TextField(
                    modifier = Modifier.width(200.dp),
                    textStyle = LocalTextStyle.current.copy(fontSize = 15.sp),
                    value = condExpression.value,
                    onValueChange = { newText ->
                        condExpression.value = newText
                    }
                )
            }
            Spacer(modifier = Modifier.height(8.dp))
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(text = "step", fontSize = 16.sp, fontWeight = FontWeight.Bold)
                Spacer(modifier = Modifier.width(8.dp))
                TextField(
                    modifier = Modifier.width(200.dp),
                    textStyle = LocalTextStyle.current.copy(fontSize = 15.sp),
                    value = loopExpression.value,
                    onValueChange = { newText ->
                        loopExpression.value = newText
                    }
                )

            }
            Text(
                text = "Do begin",
                fontSize = 15.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(top = 16.dp)
            )
        }
    }
}

// Кард для ввода значения переменной
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CinBlock(onCloseClicked: () -> Unit) {
    val variableName = remember { mutableStateOf("") }

    // Сохраненное значение имени переменной
    variableName.value = ""

    Card(
        modifier = Modifier
            .width(300.dp)
            .padding(2.dp)
            .height(80.dp)
            .clickable {
                myGlobalNumber = 5;
                onCloseClicked();
            },
        shape = RoundedCornerShape(15.dp),
    ) {
        Row(
            modifier = Modifier.padding(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Cin",
                fontSize = 15.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(end = 8.dp)
            )
            TextField(
                modifier = Modifier.weight(1f),
                textStyle = LocalTextStyle.current.copy(fontSize = 15.sp),
                value = variableName.value,
                onValueChange = { newText ->
                    variableName.value = newText
                    // Изменять значение внешнего класса (значение имени переменной) здесь (при изменении текст филда) именно через variableName.value
                }
            )
        }
    }
}

// Кард для вывода значения переменной
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CoutBlock(onCloseClicked: () -> Unit) {
    val variableName = remember { mutableStateOf("") }

    // Сохраненное значение имени переменной
    variableName.value = ""

    Card(
        modifier = Modifier
            .width(300.dp)
            .height(80.dp)
            .padding(2.dp)
            .clickable {
                myGlobalNumber = 6;
                onCloseClicked();
            },
        shape = RoundedCornerShape(15.dp),
    ) {
        Row(
            modifier = Modifier.padding(8.dp),
            verticalAlignment = Alignment.CenterVertically
        )
        {
            Text(
                text = "Cout",
                fontSize = 15.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(end = 8.dp)
            )
            TextField(
                modifier = Modifier.weight(1f),
                textStyle = LocalTextStyle.current.copy(fontSize = 15.sp),
                value = variableName.value,
                onValueChange = { newText ->
                    variableName.value = newText
                    // Изменять значение внешнего класса (значение имени переменной) здесь (при изменении текст филда) именно через variableName.value
                }
            )

        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FunctionBlock(
    onCloseClicked: () -> Unit
) {
    val FunctionName = remember { mutableStateOf("") }
    val FunctionParams = remember { mutableStateOf("") }
    val selectedType = remember { mutableStateOf("") }
    var expanded by remember {
        mutableStateOf(false)
    }
    if (FunctionParams.value == "") {
        FunctionParams.value = "<>";
    }
    Card(
        modifier = Modifier
            .width(500.dp)
            .height(90.dp)
            .padding(2.dp)
            .background(Color.LightGray)
            .clickable {
                myGlobalNumber = 8;
                onCloseClicked();
            },
        shape = RoundedCornerShape(15.dp),
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(8.dp),
            contentAlignment = Alignment.Center
        ) {
            Row()
            {
                IconButton(onClick = { expanded = true }) {
                    Icon(Icons.Filled.ArrowDropDown, contentDescription = null)
                }
                Text(
                    text = selectedType.value,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(horizontal = 8.dp)
                )
                DropdownMenu(
                    expanded = expanded,
                    onDismissRequest = { expanded = false }
                ) {
                    DropdownMenuItem(
                        text = { Text(text = "int") },
                        onClick = {
                            selectedType.value = "int"
                            expanded = false
                        })
                    DropdownMenuItem(
                        text = { Text(text = "double") },
                        onClick = {
                            selectedType.value = "double"
                            expanded = false
                        })
                    DropdownMenuItem(
                        text = { Text(text = "string") },
                        onClick = {
                            selectedType.value = "string"
                            expanded = false
                        })
                }
                Text(
                    text = "Function name ",
                    style = TextStyle(fontSize = 16.sp, fontWeight = FontWeight.Bold)
                )
                TextField(modifier = Modifier
                    .width(50.dp),
                    value = FunctionName.value, onValueChange = { newText ->
                        FunctionName.value = newText
                    })
                Text(
                    text = "     ",
                    style = TextStyle(fontSize = 16.sp, fontWeight = FontWeight.Bold)
                )
                TextField(value = FunctionParams.value, onValueChange = { newText ->
                    FunctionParams.value = newText
                })
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DoFunctionBlock(
    onCloseClicked: () -> Unit
) {
    val FunctionName = remember { mutableStateOf("") }
    val FunctionParams = remember { mutableStateOf("") }
    if (FunctionParams.value == "") {
        FunctionParams.value = "<>";
    }
    Card(
        modifier = Modifier
            .width(350.dp)
            .height(90.dp)
            .padding(2.dp)
            .background(Color.LightGray)
            .clickable {
                myGlobalNumber = 9;
                onCloseClicked();
            },
        shape = RoundedCornerShape(15.dp),
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(8.dp),
            contentAlignment = Alignment.Center
        ) {
            Row()
            {

                Text(
                    text = "Do Function name ",
                    style = TextStyle(fontSize = 16.sp, fontWeight = FontWeight.Bold)
                )
                TextField(modifier = Modifier
                    .width(50.dp),
                    value = FunctionName.value, onValueChange = { newText ->
                        FunctionName.value = newText
                    })
                Text(
                    text = "     ",
                    style = TextStyle(fontSize = 16.sp, fontWeight = FontWeight.Bold)
                )
                TextField(value = FunctionParams.value, onValueChange = { newText ->
                    FunctionParams.value = newText
                })
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun StructBlock(onCloseClicked: () -> Unit) {
    var Name by remember { mutableStateOf("") }
    var StrObjects by remember { mutableStateOf("") }
    Card(
        modifier = Modifier
            .width(400.dp)
            .height(120.dp)
            .clickable {
                myGlobalNumber = 10;
                onCloseClicked();
            },
        shape = RoundedCornerShape(15.dp),
    ) {
        Column(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth()
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text("Struct Name")
                TextField(
                    value = "",
                    onValueChange = { newText ->
                        Name = newText
                    },
                    modifier = Modifier.weight(1f)
                )
                IconButton(
                    onClick = { /* Обработка нажатия на кнопку */ }
                ) {
                    Icon(Icons.Default.Info, contentDescription = "Info Icon")
                }
            }
            TextField(
                value = "",
                onValueChange = { newText ->
                    StrObjects = newText
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight()
                    .padding(top = 16.dp)
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun StructVarBlock(onCloseClicked: () -> Unit) {
    var Name by remember { mutableStateOf("") }
    var Type by remember { mutableStateOf("") }
    Card(
        modifier = Modifier
            .width(400.dp)
            .height(120.dp)
            .clickable {
                myGlobalNumber = 11;
                onCloseClicked();
            },
        shape = RoundedCornerShape(15.dp),
    ) {
        Column(
            modifier = Modifier
                .padding(4.dp)
                .fillMaxWidth()
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text("Struct Type: ")
                TextField(
                    value = Type,
                    onValueChange = { newText ->
                        Type = newText
                    },
                    modifier = Modifier.weight(1f)
                )
            }
            Row()
            {
                TextField(
                    value = Name,
                    onValueChange = { newText ->
                        Name = newText
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .fillMaxHeight()
                        .padding(top = 5.dp)
                )
            }
        }
    }
}

@Composable
fun BreakBlock(onCloseClicked: () -> Unit) {
    Card(
        modifier = Modifier
            .width(200.dp)
            .height(45.dp)
            .padding(2.dp)
            .clickable {
                myGlobalNumber = 12;
                onCloseClicked();
            },
        shape = RoundedCornerShape(15.dp),
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(8.dp),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "Break",
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold
            )
        }
    }
}

@Composable
fun ContinueBlock(onCloseClicked: () -> Unit) {
    Card(
        modifier = Modifier
            .width(200.dp)
            .height(45.dp)
            .padding(2.dp)
            .clickable {
                myGlobalNumber = 13;
                onCloseClicked();
            },
        shape = RoundedCornerShape(15.dp),
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(8.dp),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "Continue",
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun OtherTypeVariableReal(onCloseClicked: () -> Unit) {
    var expanded by remember { mutableStateOf(false) }
    val variableName = remember { mutableStateOf("") }
    val selectedType = remember { mutableStateOf("") }
    // Сохраненный тип переменной
    if (selectedType.value == "") {
        selectedType.value = "int"
    }
    // Сохраненное имя переменной

    Card(
        modifier = Modifier
            .width(500.dp)
            .height(80.dp)
            .padding(2.dp)
            .clickable {
                myGlobalNumber = 14;
                onCloseClicked();
            },
        shape = RoundedCornerShape(15.dp),

        )
    {
        Column {
            Row()
            {
                Text(text = "Change Type to")
                IconButton(onClick = { expanded = true })
                {
                    Icon(Icons.Filled.ArrowDropDown, contentDescription = null)
                }
                Text(
                    text = selectedType.value,
                    modifier = Modifier.padding(15.dp),
                    fontSize = 15.sp
                )
                DropdownMenu(
                    expanded = expanded,
                    onDismissRequest = { expanded = false }
                ) {
                    DropdownMenuItem(
                        text = { Text(text = "int") },
                        onClick = {
                            selectedType.value = "int"
                            // Изменять значение внешнего класса (типа переменной) здесь (при изменении дроп меню) именно через selectedType.value
                            expanded = false
                        })
                    DropdownMenuItem(
                        text = { Text(text = "double") },
                        onClick = {
                            selectedType.value = "double"
                            // Изменять значение внешнего класса (типа переменной) здесь (при изменении дроп меню) именно через selectedType.value
                            expanded = false
                        })
                    DropdownMenuItem(
                        text = { Text(text = "string") },
                        onClick = {
                            selectedType.value = "string"
                            // Изменять значение внешнего класса (типа переменной) здесь (при изменении дроп меню) именно через selectedType.value
                            expanded = false
                        })
                }
                Text(text = "for var:", fontSize = 15.sp)
                TextField(
                    modifier = Modifier.width(200.dp),
                    textStyle = LocalTextStyle.current.copy(fontSize = 15.sp),
                    value = variableName.value,
                    onValueChange = { newText ->
                        variableName.value = newText
                        // Изменять значение внешнего класса (имени переменной) здесь (при изменении текст филда) именно через variableName.value
                    }
                )
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
                Button(
                    onClick = {
                        if (selectedButton == 1) {
                            selectedButton = -1
                        } else {
                            selectedButton = 1
                        }
                    },
                    modifier = Modifier
                        .padding(vertical = 8.dp)
                ) {
                    Text("Переменные")
                }
                if (selectedButton == 1) {
                    TypeVariable(onCloseClicked = onCloseClicked)
                    VariableAssignment(onCloseClicked = onCloseClicked)
                    CinBlock(onCloseClicked = onCloseClicked)
                    CoutBlock(onCloseClicked = onCloseClicked)
                }
                Button(
                    onClick = {
                        if (selectedButton == 2) {
                            selectedButton = -1
                        } else {
                            selectedButton = 2
                        }
                    },
                    modifier = Modifier
                        .padding(vertical = 8.dp)
                ) {
                    Text("Условия")
                }
                if (selectedButton == 2) {
                    IfBlock(onCloseClicked = onCloseClicked)
                }
                Button(
                    onClick = {
                        if (selectedButton == 3) {
                            selectedButton = -1
                        } else {
                            selectedButton = 3
                        }
                    },
                    modifier = Modifier
                        .padding(vertical = 8.dp)
                ) {
                    Text("Циклы")
                }
                if (selectedButton == 3) {
                    ForBlock(onCloseClicked = onCloseClicked)
                    BreakBlock(onCloseClicked = onCloseClicked)
                    ContinueBlock(onCloseClicked = onCloseClicked)
                }
                Button(
                    onClick = {
                        if (selectedButton == 4) {
                            selectedButton = -1
                        } else {
                            selectedButton = 4
                        }
                    },
                    modifier = Modifier
                        .padding(vertical = 8.dp)
                ) {
                    Text("Структуры")
                }
                if (selectedButton == 4) {
                    StructBlock(onCloseClicked = onCloseClicked)
                    StructVarBlock(onCloseClicked = onCloseClicked)
                }
                Button(
                    onClick = {
                        if (selectedButton == 5) {
                            selectedButton = -1
                        } else {
                            selectedButton = 5
                        }
                    },
                    modifier = Modifier
                        .padding(vertical = 8.dp)
                ) {
                    Text("Остальное")
                }
                if (selectedButton == 5) {
                    ArrayVariable(onCloseClicked = onCloseClicked)
                    FunctionBlock(onCloseClicked = onCloseClicked)
                    DoFunctionBlock(onCloseClicked = onCloseClicked)
                    OtherTypeVariableReal(onCloseClicked = onCloseClicked)
                }
                Button(

                    onClick = {
                        for (card in CardList) {
                            card.offsetY.value -= 1500f;
                        }
                        onCloseClicked()
                    },
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


