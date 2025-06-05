package com.jaewchoi.barcodescanner.data.source.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.jaewchoi.barcodescanner.data.model.local.ScanHistory


@Database(entities = [ScanHistory::class], version = 1, exportSchema = true)
abstract class AppDatabase : RoomDatabase() {
    abstract fun scanHistoryDao(): ScanHistoryDao
}
