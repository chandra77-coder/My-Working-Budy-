package com.example.workingbuddy.ui

import android.content.Context
import android.os.Environment
import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.example.workingbuddy.model.WorkEntry
import com.example.workingbuddy.viewmodel.WorkViewModel
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.io.File
import java.io.FileOutputStream

@Composable
fun SettingsScreen(viewModel: WorkViewModel) {
    val isDarkMode by viewModel.isDarkMode.collectAsState()
    val entries by viewModel.allEntries.collectAsState()
    val context = LocalContext.current
    var showClearConfirm by remember { mutableStateOf(false) }

    Column(modifier = Modifier.padding(16.dp)) {
        Text("Settings", style = MaterialTheme.typography.headlineMedium)
        Spacer(modifier = Modifier.height(24.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text("Dark Mode")
            Switch(checked = isDarkMode, onCheckedChange = { viewModel.toggleTheme(it) })
        }

        Divider(modifier = Modifier.padding(vertical = 16.dp))

        Button(
            onClick = { exportBackup(context, entries) },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Export Backup (JSON)")
        }

        Spacer(modifier = Modifier.height(8.dp))

        var jsonToImport by remember { mutableStateOf("") }
        var showImportDialog by remember { mutableStateOf(false) }

        Button(
            onClick = { showImportDialog = true },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Import Backup (Paste JSON)")
        }

        if (showImportDialog) {
            AlertDialog(
                onDismissRequest = { showImportDialog = false },
                title = { Text("Import Data") },
                text = {
                    Column {
                        Text("Paste the JSON content from your backup file here:")
                        OutlinedTextField(
                            value = jsonToImport,
                            onValueChange = { jsonToImport = it },
                            modifier = Modifier.fillMaxWidth().height(200.dp),
                            label = { Text("JSON Data") }
                        )
                    }
                },
                confirmButton = {
                    Button(onClick = {
                        try {
                            val listType = object : TypeToken<List<WorkEntry>>() {}.type
                            val importedEntries: List<WorkEntry> = Gson().fromJson(jsonToImport, listType)
                            viewModel.importData(importedEntries)
                            Toast.makeText(context, "Imported ${importedEntries.size} entries", Toast.LENGTH_SHORT).show()
                            showImportDialog = false
                        } catch (e: Exception) {
                            Toast.makeText(context, "Invalid JSON", Toast.LENGTH_SHORT).show()
                        }
                    }) {
                        Text("Import")
                    }
                },
                dismissButton = {
                    TextButton(onClick = { showImportDialog = false }) { Text("Cancel") }
                }
            )
        }

        Spacer(modifier = Modifier.height(24.dp))

        Button(
            onClick = { showClearConfirm = true },
            colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.error),
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Clear All Data")
        }
    }

    if (showClearConfirm) {
        AlertDialog(
            onDismissRequest = { showClearConfirm = false },
            title = { Text("Clear All Data?") },
            text = { Text("This will permanently delete all your work entries. This action cannot be undone.") },
            confirmButton = {
                Button(
                    onClick = {
                        viewModel.clearAllData()
                        showClearConfirm = false
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.error)
                ) {
                    Text("Delete Everything")
                }
            },
            dismissButton = {
                TextButton(onClick = { showClearConfirm = false }) { Text("Cancel") }
            }
        )
    }
}

fun exportBackup(context: Context, entries: List<WorkEntry>) {
    try {
        val gson = Gson()
        val json = gson.toJson(entries)
        val fileName = "working_buddy_backup_${System.currentTimeMillis()}.json"
        val downloadsDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS)
        val file = File(downloadsDir, fileName)
        
        FileOutputStream(file).use { 
            it.write(json.toByteArray())
        }
        Toast.makeText(context, "Backup saved to Downloads: $fileName", Toast.LENGTH_LONG).show()
    } catch (e: Exception) {
        Toast.makeText(context, "Export failed: ${e.message}", Toast.LENGTH_LONG).show()
    }
}
