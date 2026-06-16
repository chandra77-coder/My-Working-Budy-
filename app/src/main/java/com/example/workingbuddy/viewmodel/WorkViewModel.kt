package com.example.workingbuddy.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.workingbuddy.data.AppDatabase
import com.example.workingbuddy.data.PreferencesManager
import com.example.workingbuddy.data.WorkRepository
import com.example.workingbuddy.model.WorkEntry
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import java.util.*

class WorkViewModel(application: Application) : AndroidViewModel(application) {
    private val repository: WorkRepository
    private val preferencesManager: PreferencesManager
    
    val isDarkMode: StateFlow<Boolean>
    val allEntries: StateFlow<List<WorkEntry>>
    
    init {
        val dao = AppDatabase.getDatabase(application).workEntryDao()
        repository = WorkRepository(dao)
        preferencesManager = PreferencesManager(application)
        
        isDarkMode = preferencesManager.isDarkMode.stateIn(
            viewModelScope, SharingStarted.WhileSubscribed(5000), false
        )
        
        allEntries = repository.allEntries.stateIn(
            viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList()
        )
    }

    fun toggleTheme(enabled: Boolean) {
        viewModelScope.launch {
            preferencesManager.setDarkMode(enabled)
        }
    }

    fun addEntry(
        serviceType: String,
        customName: String?,
        customer: String?,
        notes: String?,
        amount: String,
        isPaid: Boolean
    ) {
        viewModelScope.launch {
            val amt = amount.toDoubleOrNull() ?: 0.0
            val entry = WorkEntry(
                serviceType = serviceType,
                customServiceName = if (serviceType == "Other") customName else null,
                customerName = customer,
                notes = notes,
                amount = amt,
                isPaid = isPaid
            )
            repository.insert(entry)
        }
    }

    fun updateEntry(entry: WorkEntry) {
        viewModelScope.launch {
            repository.update(entry)
        }
    }

    fun deleteEntry(entry: WorkEntry) {
        viewModelScope.launch {
            repository.delete(entry)
        }
    }

    fun clearAllData() {
        viewModelScope.launch {
            repository.clearAll()
        }
    }
    
    fun importData(entries: List<WorkEntry>) {
        viewModelScope.launch {
            repository.insertAll(entries)
        }
    }
}
