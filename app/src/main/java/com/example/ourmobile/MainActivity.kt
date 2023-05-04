package com.example.ourmobile

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.compose.ui.res.dimensionResource
import androidx.activity.compose.setContent
import androidx.annotation.Dimension
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.ourmobile.ui.theme.OurMobileTheme
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.Alignment
import androidx.compose.animation.*
import androidx.compose.animation.core.*
import androidx.compose.animation.core.tween
import androidx.compose.animation.slideInHorizontally
import androidx.compose.runtime.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.TextField

@Composable
fun MyButton()
{
    var showNewScreen by remember { mutableStateOf(false) }

    Box(
        modifier = Modifier
            .wrapContentSize(align = Alignment.TopCenter)
            .padding(top = 20.dp)
    ){
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
            Button(
                onClick = {
                    showNewScreen = true // показываем новый экран
                },
            ) {
                Text("Добавить блоки")
            }
        }
    }
}
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp)
            ){
                MyButton()
                BlockList()
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

val itemsList = mutableStateListOf("")

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun VariableItem()
{
    val VariableType = remember{mutableStateOf("")}
    val VariableName = remember{mutableStateOf("")}
    Card(
        modifier = Modifier.width(500.dp).padding(10.dp),
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
fun VariableAssignment()
{
    val VariableName = remember{mutableStateOf("")}
    val VariableValue = remember{mutableStateOf("")}
    Card(
        modifier = Modifier.width(500.dp).padding(10.dp),
        shape = RoundedCornerShape(15.dp),
    ){
        Box(){
            Row()
            {
                TextField(
                    modifier = Modifier.width(200.dp),
                    textStyle = LocalTextStyle.current.copy(fontSize = 20.sp),
                    value = VariableName.value,
                    onValueChange = {newText -> VariableName.value = newText}
                )
                Text(
                    text = " = ",
                    fontSize = 20.sp
                )
                TextField(
                    modifier = Modifier.width(200.dp),
                    textStyle = LocalTextStyle.current.copy(fontSize = 20.sp),
                    value = VariableValue.value,
                    onValueChange = {newText -> VariableValue.value = newText}
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

@Composable
fun MakeAVariable()
{
    Button(
        onClick = {itemsList.add("1")}
    )
    {
        Text("MakeAVariable", fontSize = 22.sp)
    }
}
@Composable
fun AssignmentAVariable()
{
    Button(
        onClick = {itemsList.add("2")}
    )
    {
        Text("AssignmentAVariable", fontSize = 22.sp)
    }
}
@Composable
fun BlockList()
{
    Column{
        LazyColumn()
        {
            itemsIndexed(itemsList)
            {
                index, item ->
                if(item == "1")
                {
                    VariableItem()
                }
                if(item == "2")
                {
                    VariableAssignment()
                }
            }
        }
    }
}


