package com.example.workingbuddy.ui

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.workingbuddy.util.DateUtils
import com.example.workingbuddy.viewmodel.WorkViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RecordsScreen(viewModel: WorkViewModel) {
    val entries by viewModel.allEntries.collectAsState()
    var filter by remember { mutableStateOf("All") }
    
    val filteredEntries = when (filter) {
        "Today" -> entries.filter { it.timestamp >= DateUtils.getStartOfDay() }
        "This Week" -> entries.filter { it.timestamp >= DateUtils.getStartOfWeek() }
        "This Month" -> entries.filter { it.timestamp >= DateUtils.getStartOfMonth() }
        else -> entries
    }

    Column(modifier = Modifier.padding(16.dp)) {
        Text("Work Records", style = MaterialTheme.typography.headlineMedium)
        
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceEvenly) {
            listOf("All", "Today", "This Week", "This Month").forEach { label ->
                FilterChip(
                    selected = filter == label,
                    onClick = { filter = label },
                    label = { Text(label) }
                )
            }
        }
        
        Spacer(modifier = Modifier.height(8.dp))
        
        LazyColumn {
            items(filteredEntries) { entry ->
                WorkEntryItem(entry, onTogglePaid = { viewModel.updateEntry(it) })
            }
        }
    }
}
