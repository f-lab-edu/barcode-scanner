package com.jaewchoi.barcodescanner.data.source.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface ScanHistoryDao {

    @Query("SELECT * FROM scanHistory ORDER BY savedTimeMilli DESC")
    suspend fun getAll(): List<ScanHistory>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(scanHistory: ScanHistory)

    @Query("DELETE FROM scanHistory")
    suspend fun deleteAll()

    @Query("DELETE FROM scanHistory WHERE id = :id")
    suspend fun deleteById(id: Long)
}