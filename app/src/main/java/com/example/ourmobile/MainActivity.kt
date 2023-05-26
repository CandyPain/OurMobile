package com.example.ourmobile

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.*
import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.*
import androidx.compose.ui.window.Dialog
import com.example.ourmobile.ui.theme.*
import java.io.*

var cardIdCounter = 0
var NextStep = false;
var GlobalDebugMod = false;
var DebugID = 0;
var WaitConsole = false;
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
    var width: Dp,
    var isDragging: MutableState<Boolean> = mutableStateOf(false),
    var childId: MutableState<Int> = mutableStateOf(-1),
    var bordersize: MutableState<Dp> = mutableStateOf(0.dp),
)

data class VariableAssignmentClass(
    var offsetX: MutableState<Float> = mutableStateOf(0f),
    var offsetY: MutableState<Float> = mutableStateOf(0f),
    var isDragging: MutableState<Boolean> = mutableStateOf(false),
    var variableName: MutableState<String> = mutableStateOf(""),
    var variableValue: MutableState<String> = mutableStateOf(""),
    var thisID: Int,
    var childId: MutableState<Int> = mutableStateOf(-1),
    var bordersize: MutableState<Dp> = mutableStateOf(0.dp),
)

data class StructBlockClass(
    var offsetX: MutableState<Float> = mutableStateOf(0f),
    var offsetY: MutableState<Float> = mutableStateOf(0f),
    var isDragging: MutableState<Boolean> = mutableStateOf(false),
    var Name: MutableState<String> = mutableStateOf(""),
    var StrObject: MutableState<String> = mutableStateOf(""),
    var thisID: Int,
    var childId: MutableState<Int> = mutableStateOf(-1),
    var bordersize: MutableState<Dp> = mutableStateOf(0.dp),
    var ShowDialog: MutableState<Boolean> = mutableStateOf(false)
)

data class StructVarBlockClass(
    var offsetX: MutableState<Float> = mutableStateOf(0f),
    var offsetY: MutableState<Float> = mutableStateOf(0f),
    var isDragging: MutableState<Boolean> = mutableStateOf(false),
    var Name: MutableState<String> = mutableStateOf(""),
    var Type: MutableState<String> = mutableStateOf(""),
    var thisID: Int,
    var childId: MutableState<Int> = mutableStateOf(-1),
    var bordersize: MutableState<Dp> = mutableStateOf(0.dp),
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
    var bordersize: MutableState<Dp> = mutableStateOf(0.dp),
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
    var bordersize: MutableState<Dp> = mutableStateOf(0.dp),
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
    var bordersize: MutableState<Dp> = mutableStateOf(0.dp),
)

data class ArrayVaribleClass(
    var offsetX: MutableState<Float> = mutableStateOf(0f),
    var offsetY: MutableState<Float> = mutableStateOf(0f),
    var isDragging: MutableState<Boolean> = mutableStateOf(false),
    var expanded:  MutableState<Boolean> = mutableStateOf(false),
    var variableName: MutableState<String> = mutableStateOf(""),
    var selectedType: MutableState<String> = mutableStateOf(""),
    var count: MutableState<String> = mutableStateOf(""),
    var thisID: Int,
    var childId: MutableState<Int> = mutableStateOf(-1),
    var bordersize: MutableState<Dp> = mutableStateOf(0.dp),
)

data class CinBlockClass(
    var offsetX: MutableState<Float> = mutableStateOf(0f),
    var offsetY: MutableState<Float> = mutableStateOf(0f),
    var isDragging: MutableState<Boolean> = mutableStateOf(false),
    var variableName: MutableState<String> = mutableStateOf(""),
    var thisID: Int,
    var childId: MutableState<Int> = mutableStateOf(-1),
    var bordersize: MutableState<Dp> = mutableStateOf(0.dp),
)

data class CoutBlockClass(
    var offsetX: MutableState<Float> = mutableStateOf(0f),
    var offsetY: MutableState<Float> = mutableStateOf(0f),
    var isDragging: MutableState<Boolean> = mutableStateOf(false),
    var variableName: MutableState<String> = mutableStateOf(""),
    var thisID: Int,
    var childId: MutableState<Int> = mutableStateOf(-1),
    var bordersize: MutableState<Dp> = mutableStateOf(0.dp),
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
    var bordersize: MutableState<Dp> = mutableStateOf(0.dp),
)
data class EndBlockClass(
    var offsetX: MutableState<Float> = mutableStateOf(0f),
    var offsetY: MutableState<Float> = mutableStateOf(0f),
    var thisID: Int,
    var isDragging: MutableState<Boolean> = mutableStateOf(false),
    var childId: MutableState<Int> = mutableStateOf(-1),
    var bordersize: MutableState<Dp> = mutableStateOf(0.dp),
)

data class FunctionBlockClass(
    var offsetX: MutableState<Float> = mutableStateOf(0f),
    var offsetY: MutableState<Float> = mutableStateOf(0f),
    var thisID: Int,
    var isDragging: MutableState<Boolean> = mutableStateOf(false),
    var childId: MutableState<Int> = mutableStateOf(-1),
    var FunctionName:MutableState<String> = mutableStateOf(""),
    var FunctionParams:MutableState<String> = mutableStateOf(""),
    var selectedType: MutableState<String> = mutableStateOf(""),
    var expanded: MutableState<Boolean> = mutableStateOf(false),
    var bordersize: MutableState<Dp> = mutableStateOf(0.dp),
)

data class DoFunctionBlockClass(
    var offsetX: MutableState<Float> = mutableStateOf(0f),
    var offsetY: MutableState<Float> = mutableStateOf(0f),
    var thisID: Int,
    var isDragging: MutableState<Boolean> = mutableStateOf(false),
    var childId: MutableState<Int> = mutableStateOf(-1),
    var FunctionName:MutableState<String> = mutableStateOf(""),
    var FunctionParams:MutableState<String> = mutableStateOf(""),
    var bordersize: MutableState<Dp> = mutableStateOf(0.dp),
)

data class ReturnBlockClass(
    var offsetX: MutableState<Float> = mutableStateOf(0f),
    var offsetY: MutableState<Float> = mutableStateOf(0f),
    var thisID: Int,
    var isDragging: MutableState<Boolean> = mutableStateOf(false),
    var childId: MutableState<Int> = mutableStateOf(-1),
    var ReturnString:MutableState<String> = mutableStateOf(""),
    var bordersize: MutableState<Dp> = mutableStateOf(0.dp),
)

data class BreakBlockClass(
    var offsetX: MutableState<Float> = mutableStateOf(0f),
    var offsetY: MutableState<Float> = mutableStateOf(0f),
    var thisID: Int,
    var isDragging: MutableState<Boolean> = mutableStateOf(false),
    var childId: MutableState<Int> = mutableStateOf(-1),

    var bordersize: MutableState<Dp> = mutableStateOf(0.dp),
)
data class ContinueBlockClass(
    var offsetX: MutableState<Float> = mutableStateOf(0f),
    var offsetY: MutableState<Float> = mutableStateOf(0f),
    var thisID: Int,
    var isDragging: MutableState<Boolean> = mutableStateOf(false),
    var childId: MutableState<Int> = mutableStateOf(-1),

    var bordersize: MutableState<Dp> = mutableStateOf(0.dp),
)

data class TablePair(
    var key:MutableState<String> = mutableStateOf(""),
    var value:MutableState<String> = mutableStateOf(""),
)


var TypeVaribleList = mutableListOf<TypeVaribleClass>()
var ArrayVaribleList = mutableListOf<ArrayVaribleClass>()
var VariableAssignmentList = mutableListOf<VariableAssignmentClass>()
var IfBlockList = mutableListOf<IfBlockClass>()
var CardList = mutableListOf<CardClass>()
var ForBlockList = mutableListOf<ForBlockClass>()
var CinBlockList = mutableListOf<CinBlockClass>()
var CoutBlockList = mutableListOf<CoutBlockClass>()
var BeginBlockList = mutableListOf<BeginBlockClass>()
var EndBlockList = mutableListOf<EndBlockClass>()
var EndBeginBlockList = mutableListOf<EndBeginBlockClass>()
var FunctionBlockList = mutableListOf<FunctionBlockClass>()
var DoFunctionBlockList = mutableListOf<DoFunctionBlockClass>()
var ReturnBlockList = mutableListOf<ReturnBlockClass>()
var StructBlockList = mutableListOf<StructBlockClass>()
var StructVarBlockList = mutableListOf<StructVarBlockClass>()
var BreakBlockList = mutableListOf<BreakBlockClass>()
var ContinueBlockList = mutableListOf<ContinueBlockClass>()
var DebugList = mutableListOf<TablePair>()

var SaveTypeVaribleList = mutableListOf<TypeVaribleClass>()
var SaveArrayVaribleList = mutableListOf<ArrayVaribleClass>()
var SaveVariableAssignmentList = mutableListOf<VariableAssignmentClass>()
var SaveIfBlockList = mutableListOf<IfBlockClass>()
var SaveCardList = mutableListOf<CardClass>()
var SaveForBlockList = mutableListOf<ForBlockClass>()
var SaveCinBlockList = mutableListOf<CinBlockClass>()
var SaveCoutBlockList = mutableListOf<CoutBlockClass>()
var SaveBeginBlockList = mutableListOf<BeginBlockClass>()
var SaveEndBlockList = mutableListOf<EndBlockClass>()
var SaveEndBeginBlockList = mutableListOf<EndBeginBlockClass>()
var SaveFunctionBlockList = mutableListOf<FunctionBlockClass>()
var SaveDoFunctionBlockList = mutableListOf<DoFunctionBlockClass>()
var SaveReturnBlockList = mutableListOf<ReturnBlockClass>()
var SaveStructBlockList = mutableListOf<ReturnBlockClass>()
var SaveDebugList = mutableListOf<TablePair>()

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TableScreen() {
    Column {
        repeat(5) { rowIndex ->
            Row(
                Modifier
                    .fillMaxWidth()
                    .padding(4.dp)) {
                TextField(
                    value = DebugList[rowIndex].key.value,
                    onValueChange = { DebugList[rowIndex].key.value = it },
                    label = { Text("Key") },
                    modifier = Modifier
                        .weight(1f)
                        .padding(4.dp)
                )
                TextField(
                    value = DebugList[rowIndex].value.value,
                    onValueChange = { DebugList[rowIndex].value.value = it },
                    label = { Text("Value") },
                    modifier = Modifier
                        .weight(1f)
                        .padding(4.dp),
                    enabled = false
                )
            }
        }
    }
}
// Функция для сохранения списка в формате JSON
/*
fun <T> saveListToJson(list: List<T>, fileName: String, context: Context) {
val gson = Gson()
val json = gson.toJson(list)
val file = File(context.filesDir, fileName)
file.writeText(json)
}
// Функция для чтения списка из формата JSON
fun <T> readListFromJson(fileName: String, context: Context): List<T> {
val file = File(context.filesDir, fileName)
val json = file.readText()
val gson = Gson()
val type: Type = object : TypeToken<List<T>>() {}.type
return gson.fromJson(json, type)
}


 */

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MyScreen(pixelsPerDp: Float) {
    var showNewScreen by remember { mutableStateOf(false) }
    var ConsoleIsVisible by remember { mutableStateOf(false) }
    var DebugMode by remember { mutableStateOf(false) }
    var FirstTime by remember { mutableStateOf(true) }
    var delete by remember { mutableStateOf(false) }
    var fromConsole by remember { mutableStateOf("") }
    val showDialog = remember { mutableStateOf(false) }
    val saveButtonClicked = remember { mutableStateOf(false) }
    val openButtonClicked = remember { mutableStateOf(false) }
    // методы для добавления новой карточки в список
    fun TypeVaribleListAddCard() {
        TypeVaribleList.add(TypeVaribleClass(thisID = cardIdCounter))
        TypeVaribleList.last().offsetY.value = 300f;
        CardList.add(CardClass(childId = TypeVaribleList.last().childId,isDragging = TypeVaribleList.last().isDragging, offsetX = TypeVaribleList.last().offsetX, offsetY = TypeVaribleList.last().offsetY,thisID = cardIdCounter, width = 500.dp, height = 80.dp, bordersize = TypeVaribleList.last().bordersize))
        cardIdCounter++;
    }
    fun ArrayVaribleListAddCard() {
        ArrayVaribleList.add(ArrayVaribleClass(thisID = cardIdCounter))
        ArrayVaribleList.last().offsetY.value = 300f;
        CardList.add(CardClass(childId = ArrayVaribleList.last().childId,isDragging = ArrayVaribleList.last().isDragging, offsetX = ArrayVaribleList.last().offsetX, offsetY = ArrayVaribleList.last().offsetY,thisID = cardIdCounter, width = 250.dp, height = 200.dp, bordersize = ArrayVaribleList.last().bordersize))
        cardIdCounter++;
    }

    fun VariableAssignmentListAddCard() {
        VariableAssignmentList.add(VariableAssignmentClass(thisID = cardIdCounter))
        VariableAssignmentList.last().offsetY.value = 300f;
        CardList.add(CardClass(childId = VariableAssignmentList.last().childId,isDragging = VariableAssignmentList.last().isDragging, offsetX = VariableAssignmentList.last().offsetX, offsetY = VariableAssignmentList.last().offsetY,thisID = cardIdCounter,  width = 500.dp, height = 80.dp,  bordersize = VariableAssignmentList.last().bordersize))
        cardIdCounter++;
    }
    fun IfBlockListAddCard() {
        IfBlockList.add(IfBlockClass(thisID = cardIdCounter))
        IfBlockList.last().offsetY.value = 300f;
        CardList.add(CardClass(childId = IfBlockList.last().childId,isDragging = IfBlockList.last().isDragging, offsetX = IfBlockList.last().offsetX, offsetY = IfBlockList.last().offsetY,thisID = cardIdCounter,width = 500.dp, height = 150.dp,bordersize = IfBlockList.last().bordersize))
        cardIdCounter++;
        //BeginBlockList.add(BeginBlockClass(thisID = cardIdCounter))
        //CardList.add(CardClass(childId = BeginBlockList.last().childId,isDragging = BeginBlockList.last().isDragging, offsetX = BeginBlockList.last().offsetX, offsetY = BeginBlockList.last().offsetY,thisID = cardIdCounter,width = 500, height = 80.dp))
        //cardIdCounter++;
        EndBlockList.add(EndBlockClass(thisID = cardIdCounter))
        EndBlockList.last().offsetY.value = 300f;
        CardList.add(CardClass(childId = EndBlockList.last().childId,isDragging = EndBlockList.last().isDragging, offsetX = EndBlockList.last().offsetX, offsetY = EndBlockList.last().offsetY,thisID = cardIdCounter,width = 200.dp, height = 45.dp, bordersize = EndBlockList.last().bordersize))
        cardIdCounter++;
    }
    fun ForBlockListAddCard() {
        ForBlockList.add(ForBlockClass(thisID = cardIdCounter))
        ForBlockList.last().offsetY.value = 300f;
        CardList.add(CardClass(childId = ForBlockList.last().childId,isDragging = ForBlockList.last().isDragging, offsetX = ForBlockList.last().offsetX, offsetY = ForBlockList.last().offsetY,thisID = cardIdCounter,width = 250.dp, height = 225.dp, bordersize = ForBlockList.last().bordersize))
        cardIdCounter++;
        //BeginBlockList.add(BeginBlockClass(thisID = cardIdCounter))
        //CardList.add(CardClass(childId = BeginBlockList.last().childId,isDragging = BeginBlockList.last().isDragging, offsetX = BeginBlockList.last().offsetX, offsetY = BeginBlockList.last().offsetY,thisID = cardIdCounter,width = 500, height = 80.dp))
        //cardIdCounter++;
        EndBlockList.add(EndBlockClass(thisID = cardIdCounter))
        EndBlockList.last().offsetY.value = 300f;
        CardList.add(CardClass(childId = EndBlockList.last().childId,isDragging = EndBlockList.last().isDragging, offsetX = EndBlockList.last().offsetX, offsetY = EndBlockList.last().offsetY,thisID = cardIdCounter,width = 200.dp, height = 45.dp, bordersize = EndBlockList.last().bordersize))
        cardIdCounter++;
    }

    fun CinBlockListAddCard() {
        CinBlockList.add(CinBlockClass(thisID = cardIdCounter))
        CinBlockList.last().offsetY.value = 300f;
        CardList.add(CardClass(childId = CinBlockList.last().childId,isDragging = CinBlockList.last().isDragging, offsetX = CinBlockList.last().offsetX, offsetY = CinBlockList.last().offsetY,thisID = cardIdCounter,width = 300.dp, height = 80.dp,bordersize = CinBlockList.last().bordersize))
        cardIdCounter++;
    }

    fun CoutBlockListAddCard() {
        CoutBlockList.add(CoutBlockClass(thisID = cardIdCounter))
        CoutBlockList.last().offsetY.value = 300f;
        CardList.add(CardClass(childId = CoutBlockList.last().childId,isDragging = CoutBlockList.last().isDragging, offsetX = CoutBlockList.last().offsetX, offsetY = CoutBlockList.last().offsetY,thisID = cardIdCounter,width = 300.dp, height = 80.dp, bordersize = CoutBlockList.last().bordersize))
        cardIdCounter++;

    }
    fun FunctionBlockListAddCard()
    {
        FunctionBlockList.add(FunctionBlockClass(thisID = cardIdCounter))
        FunctionBlockList.last().offsetY.value = 300f;
        CardList.add(CardClass(childId = FunctionBlockList.last().childId,isDragging =  FunctionBlockList.last().isDragging, offsetX =  FunctionBlockList.last().offsetX, offsetY =  FunctionBlockList.last().offsetY,thisID = cardIdCounter,width = 500.dp, height = 90.dp, bordersize = FunctionBlockList.last().bordersize))
        cardIdCounter++;
        ReturnBlockList.add(ReturnBlockClass(thisID = cardIdCounter))
        ReturnBlockList.last().offsetY.value = 300f;
        CardList.add(CardClass(childId = ReturnBlockList.last().childId,isDragging = ReturnBlockList.last().isDragging, offsetX = ReturnBlockList.last().offsetX, offsetY =ReturnBlockList.last().offsetY,thisID = cardIdCounter,width = 200.dp, height = 80.dp, bordersize =ReturnBlockList.last().bordersize))
        cardIdCounter++;
    }
    fun DoFunctionBlockListAddCard()
    {
        DoFunctionBlockList.add(DoFunctionBlockClass(thisID = cardIdCounter))
        DoFunctionBlockList.last().offsetY.value = 300f;
        CardList.add(CardClass(childId = DoFunctionBlockList.last().childId,isDragging =  DoFunctionBlockList.last().isDragging, offsetX =  DoFunctionBlockList.last().offsetX, offsetY =  DoFunctionBlockList.last().offsetY,thisID = cardIdCounter,width = 350.dp, height = 90.dp, bordersize =DoFunctionBlockList.last().bordersize))
        cardIdCounter++;
    }
    fun StructBlockListAddCard()
    {
        StructBlockList.add(StructBlockClass(thisID = cardIdCounter))
        StructBlockList.last().offsetY.value = 300f;
        CardList.add(CardClass(childId = StructBlockList.last().childId,isDragging =  StructBlockList.last().isDragging, offsetX =  StructBlockList.last().offsetX, offsetY =  StructBlockList.last().offsetY,thisID = cardIdCounter,width = 350.dp, height = 150.dp, bordersize = StructBlockList.last().bordersize))
        cardIdCounter++;
    }
    fun StructVarBlockListAddCard()
    {
        StructVarBlockList.add(StructVarBlockClass(thisID = cardIdCounter))
        StructVarBlockList.last().offsetY.value = 300f;
        CardList.add(CardClass(childId = StructVarBlockList.last().childId,isDragging =  StructVarBlockList.last().isDragging, offsetX =  StructVarBlockList.last().offsetX, offsetY =  StructVarBlockList.last().offsetY,thisID = cardIdCounter,width = 350.dp, height = 130.dp, bordersize = StructVarBlockList.last().bordersize))
        cardIdCounter++;
    }
    fun BreakBlockListAddCard()
    {
        BreakBlockList.add(BreakBlockClass(thisID = cardIdCounter))
        BreakBlockList.last().offsetY.value = 300f;
        CardList.add(CardClass(childId = BreakBlockList.last().childId,isDragging =  BreakBlockList.last().isDragging, offsetX =  BreakBlockList.last().offsetX, offsetY =  BreakBlockList.last().offsetY,thisID = cardIdCounter,width = 200.dp, height = 45.dp, bordersize = BreakBlockList.last().bordersize))
        cardIdCounter++;
    }

    fun ContinieBlockListAddCard()
    {
        ContinueBlockList.add(ContinueBlockClass(thisID = cardIdCounter))
        ContinueBlockList.last().offsetY.value = 300f;
        CardList.add(CardClass(childId = ContinueBlockList.last().childId,isDragging =  ContinueBlockList.last().isDragging, offsetX =  ContinueBlockList.last().offsetX, offsetY =  ContinueBlockList.last().offsetY,thisID = cardIdCounter,width = 200.dp, height = 45.dp, bordersize = ContinueBlockList.last().bordersize))
        cardIdCounter++;
    }

    @Composable
    fun ButtonInfoRow(icon: ImageVector, title: String, description: String) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(
                icon,
                contentDescription = null,
                modifier = Modifier.size(24.dp)
            )
            Spacer(modifier = Modifier.width(8.dp))
            Column {
                Text(text = title,color = White, fontWeight = FontWeight.Bold)
                Text(text = description, color = White)
            }
        }
    }
    @Composable
    fun ButtonInfoDialog(onDismiss: () -> Unit) {
        Dialog(
            onDismissRequest = onDismiss,
            content = {
                Box(modifier = Modifier
                    .padding(16.dp)
                    ) {
                    Column {
                        Text(text = "Функции кнопок", fontWeight = FontWeight.Bold, color = White)
                        Spacer(modifier = Modifier.height(16.dp))
                        ButtonInfoRow(Icons.Default.PlayArrow, "Запуск", "Запуск приложения.",)
                        ButtonInfoRow(Icons.Default.List, "Консоль", "Открыть консоль.")
                        ButtonInfoRow(Icons.Default.Delete, "Удалить", "Очистка проекта")
                        ButtonInfoRow(Icons.Default.Add, "Отладка", "Открыть панель отладки")
                        ButtonInfoRow(Icons.Default.KeyboardArrowDown, "Шаг", "В режиме отладки - шаг")
                        Button(
                            onClick = onDismiss,
                        ) {
                            Text(text = "Закрыть")

                        }
                    }
                    Spacer(modifier = Modifier.height(16.dp))
                }
            }
        )
    }
    fun saveData()
    {

            SaveTypeVaribleList.clear()
            SaveTypeVaribleList.addAll(TypeVaribleList)

            SaveArrayVaribleList.clear()
            SaveArrayVaribleList.addAll(ArrayVaribleList)

            SaveVariableAssignmentList.clear()
            SaveVariableAssignmentList.addAll(VariableAssignmentList)

            SaveIfBlockList.clear()
            SaveIfBlockList.addAll(IfBlockList)

            SaveCardList.clear()
            SaveCardList.addAll(CardList)

            SaveForBlockList.clear()
            SaveForBlockList.addAll(ForBlockList)

            SaveCinBlockList.clear()
            SaveCinBlockList.addAll(CinBlockList)

            SaveCoutBlockList.clear()
            SaveCoutBlockList.addAll(CoutBlockList)

            SaveBeginBlockList.clear()
            SaveBeginBlockList.addAll(BeginBlockList)

            SaveEndBlockList.clear()
            SaveEndBlockList.addAll(EndBlockList)

            SaveEndBeginBlockList.clear()
            SaveEndBeginBlockList.addAll(EndBeginBlockList)

            SaveFunctionBlockList.clear()
            SaveFunctionBlockList.addAll(FunctionBlockList)

            SaveDoFunctionBlockList.clear()
            SaveDoFunctionBlockList.addAll(DoFunctionBlockList)

            SaveReturnBlockList.clear()
            SaveReturnBlockList.addAll(ReturnBlockList)
    }


    fun closeDialog() {
        showDialog.value = false
    }

    fun DeleteAll()
    {
        TypeVaribleList.clear()
        ArrayVaribleList.clear()
        VariableAssignmentList.clear()
        IfBlockList.clear()
        ForBlockList.clear()
        CinBlockList.clear()
        CoutBlockList.clear()
        BeginBlockList.clear()
        EndBlockList.clear()
        FunctionBlockList.clear()
        DoFunctionBlockList.clear()
        ReturnBlockList.clear()
        CardList.clear();
        CardList.add(
            CardClass(
                childId = EndBeginBlockList[0].childId,
                isDragging = EndBeginBlockList[0].isDragging,
                offsetX = EndBeginBlockList[0].offsetX,
                offsetY = EndBeginBlockList[0].offsetY,
                thisID = 0,
                width = 300.dp,
                height = 60.dp
            )
        )
        CardList.add(
            CardClass(
                childId = EndBeginBlockList[1].childId,
                isDragging = EndBeginBlockList[1].isDragging,
                offsetX = EndBeginBlockList[1].offsetX,
                offsetY = EndBeginBlockList[1].offsetY,
                thisID = 1,
                width = 300.dp,
                height = 60.dp
            )
        )
        cardIdCounter = 2;
        ConsoleIsVisible = !ConsoleIsVisible
        ConsoleIsVisible = !ConsoleIsVisible
    }
    LazyColumn(modifier = Modifier.fillMaxSize())
    {
        items(1) { item ->
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(10000.dp)
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
                    if(myGlobalNumber != 0)
                    {
                        for(card in CardList)
                        {
                            card.offsetY.value -= 1500f;
                        }
                    }
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
                    if (myGlobalNumber == 7) {
                        ArrayVaribleListAddCard()
                        myGlobalNumber = 0;
                    }
                    if(myGlobalNumber == 8)
                    {
                        FunctionBlockListAddCard()
                        myGlobalNumber = 0;
                    }
                    if(myGlobalNumber == 9)
                    {
                        DoFunctionBlockListAddCard()
                        myGlobalNumber = 0;
                    }
                    if(myGlobalNumber == 10)
                    {
                        StructBlockListAddCard()
                        myGlobalNumber = 0;
                    }
                    if(myGlobalNumber == 11)
                    {
                        StructVarBlockListAddCard()
                        myGlobalNumber = 0;
                    }
                    if(myGlobalNumber == 12)
                    {
                        BreakBlockListAddCard()
                        myGlobalNumber = 0;
                    }
                    if(myGlobalNumber == 13)
                    {
                        ContinieBlockListAddCard()
                        myGlobalNumber = 0;
                    }
                    if (FirstTime == true) {
                        EndBeginBlockList.add(EndBeginBlockClass(thisID = cardIdCounter))
                        EndBeginBlockList[0].offsetY.value = 900f;
                        CardList.add(
                            CardClass(
                                childId = EndBeginBlockList.last().childId,
                                isDragging = EndBeginBlockList.last().isDragging,
                                offsetX = EndBeginBlockList.last().offsetX,
                                offsetY = EndBeginBlockList.last().offsetY,
                                thisID = 0,
                                width = 300.dp,
                                height = 60.dp
                            )
                        )
                        cardIdCounter++
                        EndBeginBlockList.add(EndBeginBlockClass(thisID = cardIdCounter))
                        EndBeginBlockList[1].offsetY.value = 1300f;
                        CardList.add(
                            CardClass(
                                childId = EndBeginBlockList.last().childId,
                                isDragging = EndBeginBlockList.last().isDragging,
                                offsetX = EndBeginBlockList.last().offsetX,
                                offsetY = EndBeginBlockList.last().offsetY,
                                thisID = 1,
                                width = 300.dp,
                                height = 60.dp
                            )
                        )

                        cardIdCounter++
                        repeat(5)
                        {
                            DebugList.add(TablePair())
                        }
                        FirstTime = false
                    }
                    Box(modifier = Modifier.fillMaxWidth(),contentAlignment = Alignment.Center)
                    {
                        Column(verticalArrangement = Arrangement.Center)
                        {
                            Row()
                            {
                                Button(
                                    onClick = {
                                        for (card in CardList) {
                                            card.offsetY.value += 1500f;
                                        }

                                        showNewScreen = true // показываем новый экран
                                    },
                                ) {
                                    Text("Добавить блоки")
                                }
                                IconButton(onClick = {
                                    TypeVaribleList.clear()
                                    TypeVaribleList.addAll(SaveTypeVaribleList)

                                    ArrayVaribleList.clear()
                                    ArrayVaribleList.addAll(SaveArrayVaribleList)

                                    VariableAssignmentList.clear()
                                    VariableAssignmentList.addAll(SaveVariableAssignmentList)

                                    IfBlockList.clear()
                                    IfBlockList.addAll(SaveIfBlockList)

                                    CardList.clear()
                                    CardList.addAll(SaveCardList)

                                    ForBlockList.clear()
                                    ForBlockList.addAll(SaveForBlockList)

                                    CinBlockList.clear()
                                    CinBlockList.addAll(SaveCinBlockList)

                                    CoutBlockList.clear()
                                    CoutBlockList.addAll(SaveCoutBlockList)

                                    BeginBlockList.clear()
                                    BeginBlockList.addAll(SaveBeginBlockList)

                                    EndBlockList.clear()
                                    EndBlockList.addAll(SaveEndBlockList)

                                    EndBeginBlockList.clear()
                                    EndBeginBlockList.addAll(SaveEndBeginBlockList)

                                    FunctionBlockList.clear()
                                    FunctionBlockList.addAll(SaveFunctionBlockList)

                                    DoFunctionBlockList.clear()
                                    DoFunctionBlockList.addAll(SaveDoFunctionBlockList)

                                    ReturnBlockList.clear()
                                    ReturnBlockList.addAll(SaveReturnBlockList)
                                })
                                {
                                    Icon(Icons.Filled.Favorite, contentDescription = null)
                                }
                            }
                            Row()
                            {
                                IconButton(onClick = {
                                    showDialog.value = true;
                                })
                                {
                                    Icon(Icons.Filled.Info, contentDescription = null)
                                }
                                IconButton(onClick = {
                                    runApp()
                                    ConsoleIsVisible = true
                                })
                                {
                                    Icon(Icons.Filled.PlayArrow, contentDescription = null)
                                }
                                IconButton(onClick = {
                                    if(ConsoleIsVisible == true)
                                    {
                                        for(card in CardList)
                                        {
                                            card.offsetY.value -= 600f;
                                        }
                                    }
                                    else
                                    {
                                        for(card in CardList)
                                        {
                                            card.offsetY.value += 600f;
                                        }
                                    }
                                    ConsoleIsVisible = !ConsoleIsVisible
                                })
                                {
                                    Icon(Icons.Filled.List, contentDescription = null)
                                }
                                IconButton(onClick = {
                                    DeleteAll()
                                })
                                {
                                    Icon(Icons.Filled.Delete, contentDescription = null)
                                }
                                IconButton(onClick = {saveButtonClicked.value = true })
                                {
                                    Icon(Icons.Filled.Check, contentDescription = null)
                                }
                                IconButton(onClick = {
                                    if(DebugMode == true)
                                    {
                                        for(card in CardList)
                                        {
                                            card.offsetY.value -= 900f;
                                        }
                                    }
                                    else
                                    {
                                        for(card in CardList)
                                        {
                                            card.offsetY.value += 900f;
                                        }
                                    }
                                    DebugMode = !DebugMode
                                    GlobalDebugMod = !GlobalDebugMod

                                })
                                {
                                    Icon(Icons.Filled.Add, contentDescription = null)
                                }
                                IconButton(onClick = {
                                    if(DebugID < commandListID.size) {
                                        CardList[commandListID[DebugID]].bordersize.value = 3.dp
                                        if (DebugID != 0) {
                                            CardList[commandListID[DebugID - 1]].bordersize.value =
                                                0.dp
                                        }
                                        NextStep = true;
                                    }
                                })
                                {
                                    Icon(Icons.Filled.KeyboardArrowDown, contentDescription = null)
                                }

                            }
                            LaunchedEffect(saveButtonClicked.value) {
                                if (saveButtonClicked.value) {
                                    // Вызываем метод сохранения данных
                                    saveData()
                                    // Сбрасываем состояние нажатия кнопки
                                    saveButtonClicked.value = false
                                }
                            }
                            if(ConsoleIsVisible)
                            {
                                Card(
                                    modifier = Modifier
                                        .padding(16.dp)
                                        .width(500.dp)
                                        .height(200.dp),
                                    shape = RectangleShape
                                ) {
                                    Box(
                                        modifier = Modifier
                                            .width(500.dp)
                                            .height(200.dp)
                                            .background(Color.Black)
                                    )
                                    {
                                        Column(
                                            modifier = Modifier
                                                .width(500.dp)
                                                .background(Color.Black)
                                        )
                                        {
                                            LazyColumn(
                                                modifier = Modifier
                                                    .width(500.dp)
                                                    .background(Color.Black)
                                            ) {
                                                itemsIndexed(messagesCout) { _, item ->
                                                    Text(
                                                        text = item,
                                                        color = White
                                                    )
                                                }
                                            }
                                            Row(

                                            )
                                            {
                                                TextField(
                                                    modifier = Modifier
                                                        .width(300.dp),
                                                    textStyle = LocalTextStyle.current.copy(fontSize = 15.sp),
                                                    value = fromConsole,
                                                    onValueChange = { newText ->
                                                        fromConsole = newText
                                                    },
                                                    colors = TextFieldDefaults.textFieldColors(
                                                        containerColor = Color.Black,
                                                        textColor = White
                                                    )
                                                )
                                                IconButton(onClick = {
                                                    messagesCin = fromConsole;
                                                    WaitConsole = true;
                                                })
                                                {
                                                    Icon(Icons.Filled.Check, tint = Color.Green,contentDescription = null)
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                            if(DebugMode)
                            {
                                TableScreen()
                            }
                        }
                    }
                }
                if (showDialog.value) {
                    ButtonInfoDialog(onDismiss = { closeDialog() })
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
                        bordersize = card.bordersize
                    )

                }
                for (card in ArrayVaribleList) {
                    ArrayVariableReal(
                        offsetX = card.offsetX,
                        offsetY = card.offsetY,
                        isDragging = card.isDragging,
                        variableName = card.variableName,
                        expanded = card.expanded,
                        selectedType = card.selectedType,
                        thisID = card.thisID,
                        CardList = CardList,
                        count = card.count,
                        bordersize = card.bordersize,
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
                for (card in StructBlockList) {
                    StructBlockReal(
                        offsetX = card.offsetX,
                        offsetY = card.offsetY,
                        isDragging = card.isDragging,
                        Name = card.Name,
                        StrObjects = card.StrObject,
                        thisID = card.thisID,
                        CardList = CardList,
                        bordersize = card.bordersize,
                        ShowAllert = card.ShowDialog,
                    )
                }
                for (card in StructVarBlockList) {
                    StructVarBlockReal(
                        offsetX = card.offsetX,
                        offsetY = card.offsetY,
                        isDragging = card.isDragging,
                        Name = card.Name,
                        Type = card.Type,
                        thisID = card.thisID,
                        CardList = CardList,
                        bordersize = card.bordersize,
                    )
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
                        bordersize = card.bordersize,
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
                        bordersize = card.bordersize,
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
                        bordersize = card.bordersize,
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
                        bordersize = card.bordersize,
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
                        bordersize = card.bordersize,
                    )
                }
                for (card in BeginBlockList) {
                    BeginBlockReal(
                        offsetX = card.offsetX,
                        offsetY = card.offsetY,
                        isDragging = card.isDragging,
                        thisID = card.thisID,
                        CardList = CardList,
                        bordersize = card.bordersize,
                    )
                }
                for (card in EndBlockList) {
                    EndBlockReal(
                        offsetX = card.offsetX,
                        offsetY = card.offsetY,
                        isDragging = card.isDragging,
                        thisID = card.thisID,
                        CardList = CardList,
                        bordersize = card.bordersize,
                    )
                }
                for (card in FunctionBlockList) {
                    FunctionBlockReal(
                        offsetX = card.offsetX,
                        offsetY = card.offsetY,
                        isDragging = card.isDragging,
                        thisID = card.thisID,
                        CardList = CardList,
                        FunctionName = card.FunctionName,
                        FunctionParams = card.FunctionParams,
                        expanded = card.expanded,
                        selectedType = card.selectedType,
                        bordersize = card.bordersize,
                    )
                }
                for (card in DoFunctionBlockList) {
                    DoFunctionBlockReal(
                        offsetX = card.offsetX,
                        offsetY = card.offsetY,
                        isDragging = card.isDragging,
                        thisID = card.thisID,
                        CardList = CardList,
                        FunctionName = card.FunctionName,
                        FunctionParams = card.FunctionParams,
                        bordersize = card.bordersize,
                    )
                }
                for (card in ReturnBlockList) {
                    ReturnBlockReal(
                        offsetX = card.offsetX,
                        offsetY = card.offsetY,
                        isDragging = card.isDragging,
                        thisID = card.thisID,
                        CardList = CardList,
                        ReturnString = card.ReturnString,
                        bordersize = card.bordersize,
                    )
                }
                for (card in BreakBlockList) {
                    BreakBlockReal(
                        offsetX = card.offsetX,
                        offsetY = card.offsetY,
                        isDragging = card.isDragging,
                        thisID = card.thisID,
                        CardList = CardList,
                        bordersize = card.bordersize,
                    )
                }
                for (card in ContinueBlockList) {
                    ContinueBlockReal(
                        offsetX = card.offsetX,
                        offsetY = card.offsetY,
                        isDragging = card.isDragging,
                        thisID = card.thisID,
                        CardList = CardList,
                        bordersize = card.bordersize,
                    )
                }
                /*

                 */
                if (NeedClear.IdToClear != -1) {
                    if (NeedClear.WhatList == 1) {
                        TypeVaribleList.removeIf { it.thisID == NeedClear.IdToClear }
                        CardList.removeIf { it.thisID == NeedClear.IdToClear }
                        NeedClear.IdToClear = -1;
                    }
                }
                val MagnitRange = 80;
                var cardHeightInPixels = 0
                var cardWidthInPixels = 0
                var center = 0f;
                var HasChild = false;
                //Магниты
                // LocalDensity.current.run { MagnitRange.toDp().to }
                if (CardList.all { it.isDragging.value == false }) {
                    for (i in 0 until CardList.size) {
                        HasChild = false;
                        cardHeightInPixels =
                            LocalDensity.current.run { CardList[i].height.toPx() }.toInt()
                        cardWidthInPixels =
                            LocalDensity.current.run { CardList[i].width.toPx() }.toInt()
                        for (j in 0 until CardList.size) {
                            if (i != j && CardList[i].offsetY.value < CardList[j].offsetY.value && CardList[j].offsetY.value - (CardList[i].offsetY.value + cardHeightInPixels) < MagnitRange) {
                                CardList[j].offsetY.value -= CardList[j].offsetY.value - (CardList[i].offsetY.value + cardHeightInPixels)
                                center = CardList[i].offsetX.value + (cardWidthInPixels / 2)
                                cardWidthInPixels =
                                    LocalDensity.current.run { CardList[j].width.toPx() }
                                        .toInt()
                                CardList[j].offsetX.value = CardList[i].offsetX.value
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