package com.example.ourmobile

import androidx.compose.animation.*
import androidx.compose.animation.core.*
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.input.pointer.consumeAllChanges
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.*
import kotlin.math.roundToInt
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember

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
                    onDragStart = {
                        myGlobalNumber = 2;
                        isDragging.value = true
                        onCloseClicked()
                    },
                    onDragEnd = { isDragging.value = false },
                    onDragCancel = { },
                    onDrag = { change, dragAmount ->
                        offsetX.value += dragAmount.x
                        offsetY.value += dragAmount.y
                        change.consumeAllChanges()
                    }
                )
            }
            .clickable {
                myGlobalNumber = 2;
                onCloseClicked()

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
            .padding(10.dp)
            .clickable {
                myGlobalNumber = 1;
                onCloseClicked()
            },
        shape = RoundedCornerShape(15.dp)

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
}

//Кард для ифа
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun IfBlock(onCloseClicked: () -> Unit) {
    var expanded by remember { mutableStateOf(false) }
    val conditionFirst= remember { mutableStateOf("") }
    val conditionSecond = remember { mutableStateOf("") }
    var selectedSign by remember { mutableStateOf("") }
    Card(
        modifier = Modifier
            .width(500.dp)
            .padding(10.dp)
            .clickable {
                myGlobalNumber = 3;
                onCloseClicked()
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
                IconButton(onClick = { expanded= true })
                {
                    Icon(Icons.Filled.ArrowDropDown, contentDescription = null)
                }
                Text(
                    text = selectedSign,
                    modifier = Modifier.padding(15.dp),
                    fontSize = 15.sp
                )
                DropdownMenu(
                    expanded = expanded,
                    onDismissRequest = { expanded = false }
                ) {
                    DropdownMenuItem(
                        text = { Text(text = "=") },
                        onClick = {
                            selectedSign = "="
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
                            selectedSign= ">"
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
            .width(500.dp)
            .padding(10.dp)
            .clickable {
                myGlobalNumber = 4;
                onCloseClicked();
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

// Кард для ввода значения переменной
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CinBlock(onCloseClicked: () -> Unit) {
    val variableName = remember { mutableStateOf("") }

    // Сохраненное значение имени переменной
    variableName.value = ""

    Card(
        modifier = Modifier
            .width(400.dp)
            .padding(10.dp)
            .height(80.dp)
            .clickable {
                myGlobalNumber = 5;
                onCloseClicked();
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
fun CoutBlock(onCloseClicked: () -> Unit) {
    val variableName = remember { mutableStateOf("") }

    // Сохраненное значение имени переменной
    variableName.value = ""

    Card(
        modifier = Modifier
            .width(400.dp)
            .padding(10.dp)
            .clickable {
                myGlobalNumber = 6;
                onCloseClicked();
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