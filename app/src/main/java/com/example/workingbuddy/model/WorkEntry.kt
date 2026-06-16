package com.example.workingbuddy.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.Date

@Entity(tableName = "work_entries")
data class WorkEntry(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val serviceType: String,
    val customServiceName: String? = null,
    val customerName: String? = null,
    val notes: String? = null,
    val amount: Double = 0.0,
    val isPaid: Boolean = false,
    val timestamp: Long = System.currentTimeMillis()
)
