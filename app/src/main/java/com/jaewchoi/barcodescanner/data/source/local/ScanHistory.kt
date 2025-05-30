package com.jaewchoi.barcodescanner.data.source.local

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "scanHistory")
data class ScanHistory(
    @PrimaryKey(autoGenerate = true) val id: Long = 0L,
    val barcode: String,
    val url: String?,
    val savedTimeMilli: Long,
)