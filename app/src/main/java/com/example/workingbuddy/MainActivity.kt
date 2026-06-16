package com.example.workingbuddy

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.*
import com.example.workingbuddy.ui.*
import com.example.workingbuddy.viewmodel.WorkViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val viewModel: WorkViewModel = viewModel()
            val isDarkMode by viewModel.isDarkMode.collectAsState()

            WorkingBuddyTheme(darkTheme = isDarkMode) {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    MainScreen(viewModel)
                }
            }
        }
    }
}

@Composable
fun MainScreen(viewModel: WorkViewModel) {
    val navController = rememberNavController()
    var showCalculator by remember { mutableStateOf(false) }

    Scaffold(
        bottomBar = {
            NavigationBar {
                val navBackStackEntry by navController.currentBackStackEntryAsState()
                val currentRoute = navBackStackEntry?.destination?.route

                val items = listOf(
                    Triple("today", "Today", Icons.Default.Today),
                    Triple("records", "Records", Icons.Default.List),
                    Triple("charts", "Charts", Icons.Default.BarChart),
                    Triple("settings", "Settings", Icons.Default.Settings)
                )

                items.forEach { (route, label, icon) ->
                    NavigationBarItem(
                        icon = { Icon(icon, contentDescription = label) },
                        label = { Text(label) },
                        selected = currentRoute == route,
                        onClick = {
                            navController.navigate(route) {
                                popUpTo(navController.graph.startDestinationId)
                                launchSingleTop = true
                            }
                        }
                    )
                }
            }
        },
        floatingActionButton = {
            FloatingActionButton(onClick = { showCalculator = true }) {
                Icon(Icons.Default.Calculate, contentDescription = "Calculator")
            }
        }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = "today",
            modifier = Modifier.padding(innerPadding)
        ) {
            composable("today") { TodayScreen(viewModel) }
            composable("records") { RecordsScreen(viewModel) }
            composable("charts") { ChartsScreen(viewModel) }
            composable("settings") { SettingsScreen(viewModel) }
        }

        if (showCalculator) {
            CalculatorDialog(onDismiss = { showCalculator = false })
        }
    }
}
