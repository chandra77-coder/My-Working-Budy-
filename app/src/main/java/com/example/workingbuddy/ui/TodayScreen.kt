package com.example.workingbuddy.ui

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.example.workingbuddy.model.WorkEntry
import com.example.workingbuddy.util.DateUtils
import com.example.workingbuddy.viewmodel.WorkViewModel

@Composable
fun TodayScreen(viewModel: WorkViewModel) {
    val entries by viewModel.allEntries.collectAsState()
    val todayStart = DateUtils.getStartOfDay()
    val todayEntries = entries.filter { it.timestamp >= todayStart }
    
    var showAddDialog by remember { mutableStateOf(false) }

    Scaffold(
        floatingActionButton = {
            ExtendedFloatingActionButton(
                onClick = { showAddDialog = true },
                icon = { Icon(Icons.Default.Add, "Add Entry") },
                text = { Text("New Entry") }
            )
        }
    ) { padding ->
        Column(modifier = Modifier.padding(padding).padding(16.dp)) {
            Text("Today's Work", style = MaterialTheme.typography.headlineMedium)
            Spacer(modifier = Modifier.height(16.dp))
            
            SummaryCards(todayEntries)
            
            Spacer(modifier = Modifier.height(16.dp))
            
            LazyColumn {
                items(todayEntries) { entry ->
                    WorkEntryItem(entry, onTogglePaid = { viewModel.updateEntry(it) })
                }
            }
        }
    }

    if (showAddDialog) {
        AddEntryDialog(
            onDismiss = { showAddDialog = false },
            onAdd = { type, custom, customer, notes, amount, paid ->
                viewModel.addEntry(type, custom, customer, notes, amount, paid)
                showAddDialog = false
            }
        )
    }
}

@Composable
fun SummaryCards(entries: List<WorkEntry>) {
    val totalEarned = entries.filter { it.isPaid }.sumOf { it.amount }
    val totalPending = entries.filter { !it.isPaid }.sumOf { it.amount }
    val entryCount = entries.size
    
    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            Card(modifier = Modifier.weight(1f)) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text("Earned", style = MaterialTheme.typography.labelMedium)
                    Text(DateUtils.formatCurrency(totalEarned), style = MaterialTheme.typography.titleLarge)
                }
            }
            Card(modifier = Modifier.weight(1f)) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text("Pending", style = MaterialTheme.typography.labelMedium)
                    Text(DateUtils.formatCurrency(totalPending), style = MaterialTheme.typography.titleLarge)
                }
            }
        }
        Card(modifier = Modifier.fillMaxWidth()) {
            Row(
                modifier = Modifier.padding(16.dp).fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = androidx.compose.ui.Alignment.CenterVertically
            ) {
                Text("Total Entries", style = MaterialTheme.typography.labelMedium)
                Text(entryCount.toString(), style = MaterialTheme.typography.titleLarge)
            }
        }
    }
}

@Composable
fun WorkEntryItem(entry: WorkEntry, onTogglePaid: (WorkEntry) -> Unit) {
    Card(modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp)) {
        Row(modifier = Modifier.padding(16.dp), horizontalArrangement = Arrangement.SpaceBetween) {
            Column(modifier = Modifier.weight(1f)) {
                val serviceName = if (entry.serviceType == "Other") entry.customServiceName ?: "Other" else entry.serviceType
                Text(serviceName, style = MaterialTheme.typography.titleMedium)
                if (!entry.customerName.isNullOrEmpty()) {
                    Text("Customer: ${entry.customerName}", style = MaterialTheme.typography.bodyMedium)
                }
                Text(DateUtils.formatCurrency(entry.amount), style = MaterialTheme.typography.bodyLarge)
            }
            Column {
                Switch(
                    checked = entry.isPaid,
                    onCheckedChange = { onTogglePaid(entry.copy(isPaid = it)) }
                )
                Text(if (entry.isPaid) "Paid" else "Unpaid", style = MaterialTheme.typography.labelSmall)
            }
        }
    }
}

@Composable
fun AddEntryDialog(onDismiss: () -> Unit, onAdd: (String, String, String, String, String, Boolean) -> Unit) {
    var serviceType by remember { mutableStateOf("PAN Card") }
    var customName by remember { mutableStateOf("") }
    var customerName by remember { mutableStateOf("") }
    var notes by remember { mutableStateOf("") }
    var amount by remember { mutableStateOf("") }
    var isPaid by remember { mutableStateOf(false) }
    
    val services = listOf("PAN Card", "Voter ID", "Aadhaar", "Ration Card", "Swasthya Sathi", "DBT Link", "Annapurna Bhandar", "Other")

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Add New Entry") },
        text = {
            LazyColumn {
                item {
                    Text("Service Type")
                    services.forEach { service ->
                        Row(verticalAlignment = androidx.compose.ui.Alignment.CenterVertically) {
                            RadioButton(selected = serviceType == service, onClick = { serviceType = service })
                            Text(service)
                        }
                    }
                    if (serviceType == "Other") {
                        OutlinedTextField(value = customName, onValueChange = { customName = it }, label = { Text("Service Name") })
                    }
                    OutlinedTextField(value = customerName, onValueChange = { customerName = it }, label = { Text("Customer Name (Optional)") })
                    OutlinedTextField(
                        value = amount,
                        onValueChange = { if (it.isEmpty() || it.toDoubleOrNull() != null) amount = it },
                        label = { Text("Amount") },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal)
                    )
                    OutlinedTextField(value = notes, onValueChange = { notes = it }, label = { Text("Notes (Optional)") })
                    Row(verticalAlignment = androidx.compose.ui.Alignment.CenterVertically) {
                        Checkbox(checked = isPaid, onCheckedChange = { isPaid = it })
                        Text("Paid")
                    }
                }
            }
        },
        confirmButton = {
            Button(
                onClick = { onAdd(serviceType, customName, customerName, notes, amount, isPaid) },
                enabled = amount.isNotEmpty() && (serviceType != "Other" || customName.isNotEmpty())
            ) {
                Text("Add")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) { Text("Cancel") }
        }
    )
}
