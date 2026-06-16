package com.example.workingbuddy.data

import com.example.workingbuddy.model.WorkEntry
import kotlinx.coroutines.flow.Flow

class WorkRepository(private val workEntryDao: WorkEntryDao) {
    val allEntries: Flow<List<WorkEntry>> = workEntryDao.getAllEntries()

    fun getEntriesInRange(startTime: Long, endTime: Long): Flow<List<WorkEntry>> {
        return workEntryDao.getEntriesInRange(startTime, endTime)
    }

    suspend fun insert(entry: WorkEntry) {
        workEntryDao.insertEntry(entry)
    }

    suspend fun update(entry: WorkEntry) {
        workEntryDao.updateEntry(entry)
    }

    suspend fun delete(entry: WorkEntry) {
        workEntryDao.deleteEntry(entry)
    }

    suspend fun clearAll() {
        workEntryDao.clearAll()
    }
    
    suspend fun insertAll(entries: List<WorkEntry>) {
        workEntryDao.insertAll(entries)
    }
}
