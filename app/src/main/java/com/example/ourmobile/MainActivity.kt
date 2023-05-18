package com.example.ourmobile

import android.os.Bundle
import android.util.TypedValue
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.*
import androidx.compose.animation.core.*
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.input.pointer.consumeAllChanges
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.*
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.lifecycle.ViewModel
import com.example.ourmobile.ui.theme.OurMobileTheme
import kotlin.math.roundToInt
import androidx.compose.ui.platform.LocalDensity

var cardIdCounter = 0
data class needClear(
    var IdToClear:Int,
    var WhatList:Int,
)
var NeedClear = needClear(-1,0)

//1- TypeVarible
//2- VariableAssignment
//3 - IfBlock
//4 - ForBlock
//5 - Cin
//6 - Cout

class CardClass(
    var offsetX: MutableState<Float> = mutableStateOf(0f),
    var offsetY: MutableState<Float> = mutableStateOf(0f),
    var thisID: Int,
    var height: Dp,
    var width: Int,
    var isDragging: MutableState<Boolean> = mutableStateOf(false),
    var childId: MutableState<Int> = mutableStateOf(-1),

)

data class VariableAssignmentClass(
    var offsetX: MutableState<Float> = mutableStateOf(0f),
    var offsetY: MutableState<Float> = mutableStateOf(0f),
    var isDragging: MutableState<Boolean> = mutableStateOf(false),
    var variableName: MutableState<String> = mutableStateOf(""),
    var variableValue: MutableState<String> = mutableStateOf(""),
    var thisID: Int,
    var childId: MutableState<Int> = mutableStateOf(-1),

)


data class IfBlockClass(
    var offsetX: MutableState<Float> = mutableStateOf(0f),
    var offsetY: MutableState<Float> = mutableStateOf(0f),
    var isDragging: MutableState<Boolean> = mutableStateOf(false),
    var thisID: Int,
    var childId: MutableState<Int> = mutableStateOf(-1),
    var conditionFirst: MutableState<String> = mutableStateOf(""),
    var conditionSecond: MutableState<String> = mutableStateOf(""),
    var selectedSign: MutableState<String> = mutableStateOf(""),
    var expanded: MutableState<Boolean> = mutableStateOf(false),

)

data class ForBlockClass(
    var offsetX: MutableState<Float> = mutableStateOf(0f),
    var offsetY: MutableState<Float> = mutableStateOf(0f),
    var isDragging: MutableState<Boolean> = mutableStateOf(false),
    var initExpression: MutableState<String> = mutableStateOf(""),
    var condExpression: MutableState<String> = mutableStateOf(""),
    var loopExpression: MutableState<String> = mutableStateOf(""),
    var thisID: Int,
    var childId: MutableState<Int> = mutableStateOf(-1),
)

data class TypeVaribleClass(
    var offsetX: MutableState<Float> = mutableStateOf(0f),
    var offsetY: MutableState<Float> = mutableStateOf(0f),
    var isDragging: MutableState<Boolean> = mutableStateOf(false),
    var expanded:  MutableState<Boolean> = mutableStateOf(false),
    var variableName: MutableState<String> = mutableStateOf(""),
    var selectedType: MutableState<String> = mutableStateOf(""),
    var thisID: Int,
    var childId: MutableState<Int> = mutableStateOf(-1),
)

data class CinBlockClass(
    var offsetX: MutableState<Float> = mutableStateOf(0f),
    var offsetY: MutableState<Float> = mutableStateOf(0f),
    var isDragging: MutableState<Boolean> = mutableStateOf(false),
    var variableName: MutableState<String> = mutableStateOf(""),
    var thisID: Int,
    var childId: MutableState<Int> = mutableStateOf(-1),
)

data class CoutBlockClass(
    var offsetX: MutableState<Float> = mutableStateOf(0f),
    var offsetY: MutableState<Float> = mutableStateOf(0f),
    var isDragging: MutableState<Boolean> = mutableStateOf(false),
    var variableName: MutableState<String> = mutableStateOf(""),
    var thisID: Int,
    var childId: MutableState<Int> = mutableStateOf(-1),
)

data class EndBeginBlockClass(
    var offsetX: MutableState<Float> = mutableStateOf(0f),
    var offsetY: MutableState<Float> = mutableStateOf(0f),
    var thisID: Int,
    var isDragging: MutableState<Boolean> = mutableStateOf(false),
    var childId: MutableState<Int> = mutableStateOf(-1),
)

data class BeginBlockClass(
    var offsetX: MutableState<Float> = mutableStateOf(0f),
    var offsetY: MutableState<Float> = mutableStateOf(0f),
    var thisID: Int,
    var isDragging: MutableState<Boolean> = mutableStateOf(false),
    var childId: MutableState<Int> = mutableStateOf(-1),
)
data class EndBlockClass(
    var offsetX: MutableState<Float> = mutableStateOf(0f),
    var offsetY: MutableState<Float> = mutableStateOf(0f),
    var thisID: Int,
    var isDragging: MutableState<Boolean> = mutableStateOf(false),
    var childId: MutableState<Int> = mutableStateOf(-1),
)


val TypeVaribleList = mutableListOf<TypeVaribleClass>()
val VariableAssignmentList = mutableListOf<VariableAssignmentClass>()
val IfBlockList = mutableListOf<IfBlockClass>()
val CardList = mutableListOf<CardClass>()
val ForBlockList = mutableListOf<ForBlockClass>()
val CinBlockList = mutableListOf<CinBlockClass>()
val CoutBlockList = mutableListOf<CoutBlockClass>()
val BeginBlockList = mutableListOf<BeginBlockClass>()
val EndBlockList = mutableListOf<EndBlockClass>()
val EndBeginBlockList = mutableListOf<EndBeginBlockClass>()

@Composable
fun MyScreen(pixelsPerDp: Float) {
    var showNewScreen by remember { mutableStateOf(false) }
    var FirstTime by remember { mutableStateOf(true) }

    // методы для добавления новой карточки в список
    fun TypeVaribleListAddCard() {
        TypeVaribleList.add(TypeVaribleClass(thisID = cardIdCounter))
        CardList.add(CardClass(childId = TypeVaribleList.last().childId,isDragging = TypeVaribleList.last().isDragging, offsetX = TypeVaribleList.last().offsetX, offsetY = TypeVaribleList.last().offsetY,thisID = cardIdCounter, width = 500, height = 80.dp))
        cardIdCounter++;
    }

    fun VariableAssignmentListAddCard() {
        VariableAssignmentList.add(VariableAssignmentClass(thisID = cardIdCounter))
        CardList.add(CardClass(childId = VariableAssignmentList.last().childId,isDragging = VariableAssignmentList.last().isDragging, offsetX = VariableAssignmentList.last().offsetX, offsetY = VariableAssignmentList.last().offsetY,thisID = cardIdCounter,  width = 500, height = 80.dp))
        cardIdCounter++;
    }
    fun IfBlockListAddCard() {
        IfBlockList.add(IfBlockClass(thisID = cardIdCounter))
        CardList.add(CardClass(childId = IfBlockList.last().childId,isDragging = IfBlockList.last().isDragging, offsetX = IfBlockList.last().offsetX, offsetY = IfBlockList.last().offsetY,thisID = cardIdCounter,width = 500, height = 130.dp))
        cardIdCounter++;
        BeginBlockList.add(BeginBlockClass(thisID = cardIdCounter))
        CardList.add(CardClass(childId = BeginBlockList.last().childId,isDragging = BeginBlockList.last().isDragging, offsetX = BeginBlockList.last().offsetX, offsetY = BeginBlockList.last().offsetY,thisID = cardIdCounter,width = 500, height = 80.dp))
        cardIdCounter++;
        EndBlockList.add(EndBlockClass(thisID = cardIdCounter))
        CardList.add(CardClass(childId = EndBlockList.last().childId,isDragging = EndBlockList.last().isDragging, offsetX = EndBlockList.last().offsetX, offsetY = EndBlockList.last().offsetY,thisID = cardIdCounter,width = 500, height = 80.dp))
        cardIdCounter++;
    }
    fun ForBlockListAddCard() {
        ForBlockList.add(ForBlockClass(thisID = cardIdCounter))
        CardList.add(CardClass(childId = ForBlockList.last().childId,isDragging = ForBlockList.last().isDragging, offsetX = ForBlockList.last().offsetX, offsetY = ForBlockList.last().offsetY,thisID = cardIdCounter,width = 500, height = 150.dp))
        cardIdCounter++;
        BeginBlockList.add(BeginBlockClass(thisID = cardIdCounter))
        CardList.add(CardClass(childId = BeginBlockList.last().childId,isDragging = BeginBlockList.last().isDragging, offsetX = BeginBlockList.last().offsetX, offsetY = BeginBlockList.last().offsetY,thisID = cardIdCounter,width = 500, height = 80.dp))
        cardIdCounter++;
        EndBlockList.add(EndBlockClass(thisID = cardIdCounter))
        CardList.add(CardClass(childId = EndBlockList.last().childId,isDragging = EndBlockList.last().isDragging, offsetX = EndBlockList.last().offsetX, offsetY = EndBlockList.last().offsetY,thisID = cardIdCounter,width = 500, height = 80.dp))
        cardIdCounter++;
    }

    fun CinBlockListAddCard() {
        CinBlockList.add(CinBlockClass(thisID = cardIdCounter))
        CardList.add(CardClass(childId = CinBlockList.last().childId,isDragging = CinBlockList.last().isDragging, offsetX = CinBlockList.last().offsetX, offsetY = CinBlockList.last().offsetY,thisID = cardIdCounter,width = 500, height = 80.dp))
        cardIdCounter++;
    }

    fun CoutBlockListAddCard() {
        CoutBlockList.add(CoutBlockClass(thisID = cardIdCounter))
        CardList.add(CardClass(childId = CoutBlockList.last().childId,isDragging = CoutBlockList.last().isDragging, offsetX = CoutBlockList.last().offsetX, offsetY = CoutBlockList.last().offsetY,thisID = cardIdCounter,width = 500, height = 80.dp))
        cardIdCounter++;
    }
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
    )
    { item{
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
                if (myGlobalNumber == 2) {
                    VariableAssignmentListAddCard()
                    myGlobalNumber = 0;
                }
                if (myGlobalNumber == 3) {
                    IfBlockListAddCard()
                    myGlobalNumber = 0;
                }
                if (myGlobalNumber == 4) {
                    ForBlockListAddCard()
                    myGlobalNumber = 0;
                }
                if (myGlobalNumber == 5) {
                    CinBlockListAddCard()
                    myGlobalNumber = 0;
                }
                if (myGlobalNumber == 6) {
                    CoutBlockListAddCard()
                    myGlobalNumber = 0;
                }
                if (FirstTime == true) {
                    EndBeginBlockList.add(EndBeginBlockClass(thisID = cardIdCounter))
                    CardList.add(
                        CardClass(
                            childId = EndBeginBlockList.last().childId,
                            isDragging = EndBeginBlockList.last().isDragging,
                            offsetX = EndBeginBlockList.last().offsetX,
                            offsetY = EndBeginBlockList.last().offsetY,
                            thisID = 0,
                            width = 500,
                            height = 80.dp
                        )
                    )
                    cardIdCounter++
                    EndBeginBlockList.add(EndBeginBlockClass(thisID = cardIdCounter))
                    CardList.add(
                        CardClass(
                            childId = EndBeginBlockList.last().childId,
                            isDragging = EndBeginBlockList.last().isDragging,
                            offsetX = EndBeginBlockList.last().offsetX,
                            offsetY = EndBeginBlockList.last().offsetY,
                            thisID = 1,
                            width = 500,
                            height = 80.dp
                        )
                    )
                    cardIdCounter++
                    FirstTime = false
                }
                Button(
                    onClick = {

                        showNewScreen = true // показываем новый экран
                    },
                ) {
                    Text("Добавить блоки")
                }
                //отрисовка
                for (card in TypeVaribleList) {
                    TypeVariableReal(
                        offsetX = card.offsetX,
                        offsetY = card.offsetY,
                        isDragging = card.isDragging,
                        variableName = card.variableName,
                        expanded = card.expanded,
                        selectedType = card.selectedType,
                        thisID = card.thisID,
                        CardList = CardList,
                    )

                }
                for (card in EndBeginBlockList) {
                    if (card.thisID == 0) {
                        BeginBlock(
                            offsetX = card.offsetX,
                            offsetY = card.offsetY,
                            isDragging = card.isDragging,
                            thisID = card.thisID,
                            CardList = CardList
                        )
                    } else {
                        EndBlock(
                            offsetX = card.offsetX,
                            offsetY = card.offsetY,
                            isDragging = card.isDragging,
                            thisID = card.thisID,
                            CardList = CardList
                        )
                    }
                }

                for (card in VariableAssignmentList) {
                    VariableAssignmentReal(
                        offsetX = card.offsetX,
                        offsetY = card.offsetY,
                        isDragging = card.isDragging,
                        VariableName = card.variableName,
                        VariableValue = card.variableValue,
                        thisID = card.thisID,
                        CardList = CardList,
                    )
                }
                for (card in IfBlockList) {
                    IfBlockReal(
                        offsetX = card.offsetX,
                        offsetY = card.offsetY,
                        isDragging = card.isDragging,
                        conditionFirst = card.conditionFirst,
                        conditionSecond = card.conditionSecond,
                        expanded = card.expanded,
                        selectedSign = card.selectedSign,
                        thisID = card.thisID,
                        CardList = CardList,

                        )
                }
                for (card in ForBlockList) {
                    ForBlockReal(
                        offsetX = card.offsetX,
                        offsetY = card.offsetY,
                        isDragging = card.isDragging,
                        initExpression = card.initExpression,
                        condExpression = card.condExpression,
                        loopExpression = card.loopExpression,
                        thisID = card.thisID,
                        CardList = CardList,

                        )
                }
                for (card in CinBlockList) {
                    CinBlockReal(
                        offsetX = card.offsetX,
                        offsetY = card.offsetY,
                        isDragging = card.isDragging,
                        variableName = card.variableName,
                        thisID = card.thisID,
                        CardList = CardList,
                    )
                }
                for (card in CoutBlockList) {
                    CoutBlockReal(
                        offsetX = card.offsetX,
                        offsetY = card.offsetY,
                        isDragging = card.isDragging,
                        variableName = card.variableName,
                        thisID = card.thisID,
                        CardList = CardList,
                    )
                }
                for (card in BeginBlockList) {
                    BeginBlockReal(
                        offsetX = card.offsetX,
                        offsetY = card.offsetY,
                        isDragging = card.isDragging,
                        thisID = card.thisID,
                        CardList = CardList
                    )
                }
                for (card in EndBlockList) {
                    EndBlockReal(
                        offsetX = card.offsetX,
                        offsetY = card.offsetY,
                        isDragging = card.isDragging,
                        thisID = card.thisID,
                        CardList = CardList
                    )
                }

                if (NeedClear.IdToClear != -1) {
                    if (NeedClear.WhatList == 1) {
                        TypeVaribleList.removeIf { it.thisID == NeedClear.IdToClear }
                        CardList.removeIf { it.thisID == NeedClear.IdToClear }
                        NeedClear.IdToClear = -1;
                    }
                }
                val MagnitRange = 80;
                var cardHeightInPixels = 0
                var HasChild = false;
                //Магниты
                // LocalDensity.current.run { MagnitRange.toDp().to }
                if (CardList.all { it.isDragging.value == false }) {
                    for (i in 0 until CardList.size) {
                        HasChild = false;
                        cardHeightInPixels =
                            LocalDensity.current.run { CardList[i].height.toPx() }.toInt()
                        for (j in 0 until CardList.size) {
                            if (i != j && CardList[i].offsetY.value < CardList[j].offsetY.value && CardList[j].offsetY.value - (CardList[i].offsetY.value + cardHeightInPixels) < MagnitRange) {
                                CardList[j].offsetY.value -= CardList[j].offsetY.value - (CardList[i].offsetY.value + cardHeightInPixels)
                                CardList[j].offsetX.value = CardList[i].offsetX.value;
                                CardList[i].childId.value = CardList[j].thisID;
                                HasChild = true;
                            }
                        }
                        if (HasChild == false) {
                            CardList[i].childId.value = -1;
                        }
                    }
                }


            }
        }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
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



@OptIn(ExperimentalMaterial3Api::class)
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

@OptIn(ExperimentalMaterial3Api::class)
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

@OptIn(ExperimentalMaterial3Api::class)
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




var messagesCout = mutableListOf<String>()
var valuesCout = mutableListOf<Int>()
var messagesCin: String = ""

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ConsoleScreen()
{
    Card(

    )
    {
        Column()
        {
            /*
                Button(
                    onClick = //
                )
                {
                    Text(text = "OK")
                }
            */
            LazyColumn()
            {
                itemsIndexed(messagesCout)
                {
                        index, item ->
                    Text(
                        text = item,
                        fontSize = 18.sp,
                        modifier = Modifier.padding(16.dp)
                    )
                }
            }
            TextField(
                modifier = Modifier
                    .width(200.dp)
                    .padding(10.dp),
                textStyle = LocalTextStyle.current.copy(fontSize = 20.sp),
                value = "",
                onValueChange = { newText -> messagesCin = newText }
            )
        }
    }
}

    class MainActivity : ComponentActivity() {
        var pixelsPerDp: Float = 0f
        override fun onCreate(savedInstanceState: Bundle?) {
            super.onCreate(savedInstanceState)
            pixelsPerDp = resources.displayMetrics.density

            setContent {
                OurMobileTheme {
                    Surface(
                        modifier = Modifier.fillMaxSize(),
                        color = MaterialTheme.colorScheme.background
                    ) {
                        MyScreen(pixelsPerDp)
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