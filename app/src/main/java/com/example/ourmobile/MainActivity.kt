package com.example.ourmobile

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.*
import androidx.lifecycle.ViewModel
import com.example.ourmobile.ui.theme.OurMobileTheme
import kotlin.math.roundToInt


//1- TypeVarible
//2- VariableAssignment
//3 - IfBlock
data class VariableAssignmentClass(
    var offsetX: MutableState<Float> = mutableStateOf(0f),
    var offsetY: MutableState<Float> = mutableStateOf(0f),
    var isDragging: MutableState<Boolean> = mutableStateOf(false),
    var variableName: MutableState<String> = mutableStateOf(""),
    var variableValue: MutableState<String> = mutableStateOf("")
)


data class IfBlockClass(
    var offsetX: MutableState<Float> = mutableStateOf(0f),
    var offsetY: MutableState<Float> = mutableStateOf(0f),
    var isDragging: MutableState<Boolean> = mutableStateOf(false),
    var conditionValue: MutableState<String> = mutableStateOf(""),
)

data class TypeVaribleClass(
    var offsetX: MutableState<Float> = mutableStateOf(0f),
    var offsetY: MutableState<Float> = mutableStateOf(0f),
    var isDragging: MutableState<Boolean> = mutableStateOf(false),
    var expanded:  MutableState<Boolean> = mutableStateOf(false),
    var variableName: MutableState<String> = mutableStateOf(""),
    var selectedType: MutableState<String> = mutableStateOf(""),
)


@Composable
fun MyScreen() {
    var showNewScreen by remember { mutableStateOf(false) }
    val TypeVaribleList = remember { mutableListOf<TypeVaribleClass>() }
    val VariableAssignmentList = remember { mutableListOf<VariableAssignmentClass>() }
    val IfBlockList = remember { mutableListOf<IfBlockClass>() }

    // методы для добавления новой карточки в список
    fun TypeVaribleListAddCard() {
        TypeVaribleList.add(TypeVaribleClass())
    }

    fun VariableAssignmentListAddCard() {
        VariableAssignmentList.add(VariableAssignmentClass())
    }
    fun IfBlockListAddCard() {
        IfBlockList.add(IfBlockClass())
    }
    Box(
        modifier = Modifier
            .fillMaxSize()
            .wrapContentSize(align = Alignment.TopCenter)
            .padding(top = 20.dp)
    ) {
        AnimatedVisibility(
            visible = showNewScreen,
            enter = slideInVertically(
                initialOffsetY = { it },
                animationSpec = tween(durationMillis = 500)
            ),
            exit = slideOutVertically(
                targetOffsetY = { it },
                animationSpec = tween(durationMillis = 500)
            )
        ) {
            NewScreen(showNewScreen = showNewScreen) {
                // Закрываем экран
                showNewScreen = false
            }
        }

        if (!showNewScreen) {
            if (myGlobalNumber == 1) {
                TypeVaribleListAddCard()
                myGlobalNumber = 0;
            }
            if(myGlobalNumber == 2)
            {
                VariableAssignmentListAddCard()
                myGlobalNumber = 0;
            }
            if(myGlobalNumber == 3)
            {
                IfBlockListAddCard()
                myGlobalNumber = 0;
            }
            Button(
                onClick = {

                    showNewScreen = true // показываем новый экран
                },
            ) {
                Text("Добавить блоки")
            }
            for (card in TypeVaribleList) {
                TypeVariableReal(
                    offsetX = card.offsetX,
                    offsetY = card.offsetY,
                    isDragging = card.isDragging,
                    variableName = card.variableName,
                    expanded = card.expanded,
                    selectedType = card.selectedType,
                )

            }
            for (card in VariableAssignmentList) {
                VariableAssignmentReal(
                    offsetX = card.offsetX,
                    offsetY = card.offsetY,
                    isDragging = card.isDragging,
                    VariableName = card.variableName,
                    VariableValue = card.variableValue,
                )
            }
            for (card in IfBlockList) {
                IfBlockReal(
                    offsetX = card.offsetX,
                    offsetY = card.offsetY,
                    isDragging = card.isDragging,
                    condition = card.conditionValue,

                )
            }
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
        selectedType: MutableState<String>
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
                .padding(10.dp)
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


    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    fun VariableAssignmentReal(
        offsetX: MutableState<Float>,
        offsetY: MutableState<Float>,
        isDragging: MutableState<Boolean>,
        VariableName: MutableState<String>,
        VariableValue: MutableState<String>
    ) {
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
                        onDragCancel = { },
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

//Кард для ифа
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun IfBlockReal(
    offsetX: MutableState<Float>,
    offsetY: MutableState<Float>,
    isDragging: MutableState<Boolean>,
    condition: MutableState<String>
) {

    Card(
        modifier = Modifier
            .offset { IntOffset(offsetX.value.roundToInt(), offsetY.value.roundToInt()) }
            .width(380.dp)
            .padding(10.dp)
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
                    value = condition.value,
                    onValueChange = { newText ->
                        condition.value = newText
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

    class MainActivity : ComponentActivity() {
        override fun onCreate(savedInstanceState: Bundle?) {
            super.onCreate(savedInstanceState)
            setContent {
                OurMobileTheme {
                    Surface(
                        modifier = Modifier.fillMaxSize(),
                        color = MaterialTheme.colorScheme.background
                    ) {
                        MyScreen()
                    }
                }
            }
        }
    }

    @Composable
    fun Greeting(name: String, modifier: Modifier = Modifier) {
        Text(
            text = "Hello $name!",
            modifier = modifier
        )
    }

    @Preview(showBackground = true)
    @Composable
    fun GreetingPreview() {
        OurMobileTheme {
            Greeting("Android")
        }
    }