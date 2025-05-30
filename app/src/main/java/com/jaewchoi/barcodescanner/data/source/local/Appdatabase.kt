package com.jaewchoi.barcodescanner.data.source.local

import androidx.room.Database
import androidx.room.RoomDatabase


@Database(entities = [ScanHistory::class], version = 1, exportSchema = true)
abstract class AppDatabase : RoomDatabase() {
    abstract fun scanHistoryDao(): ScanHistoryDao
}
