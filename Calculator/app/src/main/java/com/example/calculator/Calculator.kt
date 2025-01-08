package com.example.calculator

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults.buttonColors
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.calculator.ui.theme.Orange
import com.example.calculator.ui.theme.Purple40

@Composable
fun Calculator(modifier: Modifier = Modifier) {
    var first by remember { mutableStateOf("0") }
    var op by remember { mutableStateOf("") }
    var second by remember { mutableStateOf("0") }
    var numnum by remember { mutableStateOf(true) }

    val numclick: (String) -> Unit = { num ->
        if(numnum){
            if(op != "") { first = "0"; op = "" }
            else if(first === "0") { first = num }
            else { first += num }
        }

        else{
            if(second === "0") { second = num }
            else { second += num }
        }
    }

    val opclick: (String) -> Unit = { oper ->
        if(oper !== "="){
            numnum = false
            op = oper
        }

        else{
            var result: Double = 0.0

            when(op){
                "+" -> result = first.toDouble() + second.toDouble()
                "-" -> result = first.toDouble() - second.toDouble()
                "x" -> result = first.toDouble() * second.toDouble()
                "/" -> result = first.toDouble() / second.toDouble()
            }

            if(first.contains(".") || second.contains(".")) { first = result.toString() }
            else { first = result.toInt().toString() }

            second = "0"
            numnum = true
        }
    }

    Column(Modifier.fillMaxSize().padding(24.dp)) {
        Text(if(numnum){ first } else { second }, Modifier.fillMaxWidth().padding(0.dp, 30.dp, 0.dp, 0.dp), textAlign = TextAlign.Right, style = MaterialTheme.typography.displayLarge)//fontSize = 64.sp)

        val intmodifier: Modifier = modifier.aspectRatio(1f).weight(1f).padding(6.dp)
        val color = buttonColors(Purple40)
        val coloralt:ButtonColors = buttonColors(Orange)
        val modifieralt: Modifier = intmodifier.aspectRatio(1f).weight(1f)

        val fontSize = 32.sp

        Row(Modifier.fillMaxWidth().weight(1f), verticalAlignment = Alignment.CenterVertically) {
            Button(modifier = modifieralt, colors = coloralt, onClick = { opclick("+") }) {
                Text("+", fontSize = fontSize)
            }

            Button(modifier = modifieralt, colors = coloralt, onClick = { opclick("-") }) {
                Text("-", fontSize = fontSize)
            }

            Button(modifier = modifieralt, colors = coloralt, onClick = { opclick("x") }) {
                Text("x", fontSize = fontSize)
            }

            Button(modifier = modifieralt, colors = coloralt, onClick = { opclick("/") }) {
                Text("/", fontSize = fontSize)
            }
        }

        Row(Modifier.fillMaxWidth().weight(1f)) {
            Button(modifier = intmodifier, colors = color, onClick = { numclick("7") }) {
                Text("7", fontSize = fontSize)
            }

            Button(modifier = intmodifier, colors = color, onClick = { numclick("8") }) {
                Text("8", fontSize = fontSize)
            }

            Button(modifier = intmodifier, colors = color, onClick = { numclick("9") }) {
                Text("9", fontSize = fontSize)
            }
        }

        Row(Modifier.fillMaxWidth().weight(1f)) {
            Button(modifier = intmodifier, colors = color, onClick = { numclick("4") }) {
                Text("4", fontSize = fontSize)
            }

            Button(modifier = intmodifier, colors = color, onClick = { numclick("5") }) {
                Text("5", fontSize = fontSize)
            }

            Button(modifier = intmodifier, colors = color, onClick = { numclick("6") }) {
                Text("6", fontSize = fontSize)
            }
        }

        Row(Modifier.fillMaxWidth().weight(1f)) {
            Button(modifier = intmodifier, colors = color, onClick = { numclick("1") }) {
                Text("1", fontSize = fontSize)
            }

            Button(modifier = intmodifier, colors = color, onClick = { numclick("2") }) {
                Text("2", fontSize = fontSize)
            }

            Button(modifier = intmodifier, colors = color, onClick = { numclick("3") }) {
                Text("3", fontSize = fontSize)
            }
        }

        Row(Modifier.fillMaxWidth().weight(1f)) {
            Button(modifier = intmodifier, colors = color, onClick = { numclick("0") }) {
                Text("0", fontSize = fontSize)
            }

            Button(modifier = intmodifier, colors = color, onClick = { numclick(".") }) {
                Text(".", fontSize = fontSize)
            }

            Button(modifier = intmodifier, colors = coloralt, onClick = { opclick("=") }) {
                Text("=", fontSize = fontSize)
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun CalculatorTest() {
    Calculator()
}