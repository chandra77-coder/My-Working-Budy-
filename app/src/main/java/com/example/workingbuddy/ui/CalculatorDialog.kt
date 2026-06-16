package com.example.workingbuddy.ui

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog

@Composable
fun CalculatorDialog(onDismiss: () -> Unit) {
    var display by remember { mutableStateOf("0") }
    var operand1 by remember { mutableStateOf<Double?>(null) }
    var pendingOp by remember { mutableStateOf<String?>(null) }

    Dialog(onDismissRequest = onDismiss) {
        Card(
            modifier = Modifier.fillMaxWidth().padding(16.dp),
            elevation = CardDefaults.cardElevation(8.dp)
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text(
                    text = display,
                    style = MaterialTheme.typography.headlineLarge,
                    modifier = Modifier.fillMaxWidth().padding(bottom = 16.dp)
                )

                val buttons = listOf(
                    listOf("7", "8", "9", "/"),
                    listOf("4", "5", "6", "*"),
                    listOf("1", "2", "3", "-"),
                    listOf("C", "0", "=", "+")
                )

                buttons.forEach { row ->
                    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceEvenly) {
                        row.forEach { label ->
                            Button(
                                onClick = {
                                    when {
                                        label == "C" -> {
                                            display = "0"
                                            operand1 = null
                                            pendingOp = null
                                        }
                                        label in listOf("+", "-", "*", "/") -> {
                                            operand1 = display.toDoubleOrNull()
                                            pendingOp = label
                                            display = "0"
                                        }
                                        label == "=" -> {
                                            val op2 = display.toDoubleOrNull() ?: 0.0
                                            val result = when (pendingOp) {
                                                "+" -> (operand1 ?: 0.0) + op2
                                                "-" -> (operand1 ?: 0.0) - op2
                                                "*" -> (operand1 ?: 0.0) * op2
                                                "/" -> if (op2 != 0.0) (operand1 ?: 0.0) / op2 else 0.0
                                                else -> op2
                                            }
                                            display = result.toString()
                                            operand1 = null
                                            pendingOp = null
                                        }
                                        else -> {
                                            display = if (display == "0") label else display + label
                                        }
                                    }
                                },
                                modifier = Modifier.weight(1f).padding(4.dp)
                            ) {
                                Text(label)
                            }
                        }
                    }
                }
                
                Button(
                    onClick = onDismiss,
                    modifier = Modifier.fillMaxWidth().padding(top = 16.dp)
                ) {
                    Text("Close")
                }
            }
        }
    }
}
