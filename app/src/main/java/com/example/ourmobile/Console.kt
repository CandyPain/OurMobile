package com.example.ourmobile

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

var messagesCout = mutableListOf<String>()
var valuesCout = mutableListOf<Int>()
var messagesCin: String = ""

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ConsoleScreen()
{
    Card(
        //visible
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
                modifier = Modifier.width(200.dp).padding(10.dp),
                textStyle = LocalTextStyle.current.copy(fontSize = 20.sp),
                value = "",
                onValueChange = { newText -> messagesCin = newText }
            )
        }
    }
}