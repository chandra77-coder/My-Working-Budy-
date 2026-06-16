package com.example.workingbuddy.data

import androidx.room.*
import com.example.workingbuddy.model.WorkEntry
import kotlinx.coroutines.flow.Flow

@Dao
interface WorkEntryDao {
    @Query("SELECT * FROM work_entries ORDER BY timestamp DESC")
    fun getAllEntries(): Flow<List<WorkEntry>>

    @Query("SELECT * FROM work_entries WHERE timestamp >= :startTime AND timestamp <= :endTime ORDER BY timestamp DESC")
    fun getEntriesInRange(startTime: Long, endTime: Long): Flow<List<WorkEntry>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertEntry(entry: WorkEntry)

    @Update
    suspend fun updateEntry(entry: WorkEntry)

    @Delete
    suspend fun deleteEntry(entry: WorkEntry)

    @Query("DELETE FROM work_entries")
    suspend fun clearAll()
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(entries: List<WorkEntry>)
}
