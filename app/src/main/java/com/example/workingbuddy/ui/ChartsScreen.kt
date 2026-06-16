package com.example.workingbuddy.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import java.util.Calendar
import com.example.workingbuddy.model.WorkEntry
import com.example.workingbuddy.util.DateUtils
import com.example.workingbuddy.viewmodel.WorkViewModel

@Composable
fun ChartsScreen(viewModel: WorkViewModel) {
    val entries by viewModel.allEntries.collectAsState()

    LazyColumn(modifier = Modifier.padding(16.dp)) {
        item {
            Text("Summary & Analytics", style = MaterialTheme.typography.headlineMedium)
            Spacer(modifier = Modifier.height(16.dp))
            
            OverallSummary(entries)
            Spacer(modifier = Modifier.height(24.dp))
            
            Text("Weekly Income", style = MaterialTheme.typography.titleMedium)
            WeeklyIncomeChart(entries)
            Spacer(modifier = Modifier.height(24.dp))
            
            Text("Service Breakdown", style = MaterialTheme.typography.titleMedium)
            ServiceBreakdown(entries)
        }
    }
}

@Composable
fun WeeklyIncomeChart(entries: List<WorkEntry>) {
    val cal = Calendar.getInstance()
    val dayLabels = listOf("Sun", "Mon", "Tue", "Wed", "Thu", "Fri", "Sat")
    val dailyIncome = FloatArray(7)

    entries.filter { it.timestamp >= DateUtils.getStartOfWeek() }.forEach { entry ->
        cal.timeInMillis = entry.timestamp
        val day = cal.get(Calendar.DAY_OF_WEEK) - 1
        dailyIncome[day] += entry.amount.toFloat()
    }

    val maxIncome = dailyIncome.maxOrNull() ?: 1f
    
    Card(modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp)) {
        Row(
            modifier = Modifier.padding(16.dp).fillMaxWidth().height(150.dp),
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.Bottom
        ) {
            dailyIncome.forEachIndexed { index, income ->
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Box(
                        modifier = Modifier
                            .width(20.dp)
                            .height((income / maxIncome * 100).dp.coerceAtLeast(4.dp))
                            .background(MaterialTheme.colorScheme.primary)
                    )
                    Text(dayLabels[index], style = MaterialTheme.typography.labelSmall)
                }
            }
        }
    }
}

@Composable
fun OverallSummary(entries: List<WorkEntry>) {
    val totalEarned = entries.filter { it.isPaid }.sumOf { it.amount }
    val totalPending = entries.filter { !it.isPaid }.sumOf { it.amount }
    val totalJobs = entries.size
    val topService = entries.groupBy { it.serviceType }.maxByOrNull { it.value.size }?.key ?: "N/A"

    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            SummaryItem("Total Jobs", totalJobs.toString(), Modifier.weight(1f))
            SummaryItem("Top Service", topService, Modifier.weight(1f))
        }
        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            SummaryItem("Total Earned", DateUtils.formatCurrency(totalEarned), Modifier.weight(1f), Color(0xFF4CAF50))
            SummaryItem("Total Pending", DateUtils.formatCurrency(totalPending), Modifier.weight(1f), Color(0xFFF44336))
        }
    }
}

@Composable
fun SummaryItem(label: String, value: String, modifier: Modifier = Modifier, color: Color = MaterialTheme.colorScheme.onSurface) {
    Card(modifier = modifier) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(label, style = MaterialTheme.typography.labelSmall)
            Text(value, style = MaterialTheme.typography.titleLarge, color = color)
        }
    }
}

@Composable
fun EarningsBarChart(entries: List<WorkEntry>) {
    val paid = entries.filter { it.isPaid }.sumOf { it.amount }.toFloat()
    val pending = entries.filter { !it.isPaid }.sumOf { it.amount }.toFloat()
    val max = maxOf(paid + pending, 1f)

    Column(modifier = Modifier.fillMaxWidth().padding(vertical = 16.dp)) {
        Row(modifier = Modifier.fillMaxWidth().height(40.dp)) {
            if (paid > 0) {
                Box(modifier = Modifier.weight(paid).fillMaxHeight().background(Color(0xFF4CAF50)))
            }
            if (pending > 0) {
                Box(modifier = Modifier.weight(pending).fillMaxHeight().background(Color(0xFFF44336)))
            }
        }
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
            Text("Paid: " + DateUtils.formatCurrency(paid.toDouble()), color = Color(0xFF4CAF50), style = MaterialTheme.typography.labelSmall)
            Text("Pending: " + DateUtils.formatCurrency(pending.toDouble()), color = Color(0xFFF44336), style = MaterialTheme.typography.labelSmall)
        }
    }
}

@Composable
fun ServiceBreakdown(entries: List<WorkEntry>) {
    val breakdown = entries.groupBy { it.serviceType }.mapValues { it.value.size }
    val total = entries.size.toFloat()

    Card(modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp)) {
        Column(modifier = Modifier.padding(16.dp)) {
            breakdown.forEach { (service, count) ->
                Column(modifier = Modifier.padding(vertical = 4.dp)) {
                    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                        Text(service, style = MaterialTheme.typography.labelMedium)
                        Text("${(count / total * 100).toInt()}%", style = MaterialTheme.typography.labelSmall)
                    }
                    LinearProgressIndicator(
                        progress = count / total,
                        modifier = Modifier.fillMaxWidth().height(8.dp),
                        color = MaterialTheme.colorScheme.secondary,
                        trackColor = MaterialTheme.colorScheme.surfaceVariant
                    )
                }
            }
        }
    }
}
