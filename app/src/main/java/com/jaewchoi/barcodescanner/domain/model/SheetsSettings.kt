package com.jaewchoi.barcodescanner.domain.model

data class SheetsSettings(
    val fileID: String,
    val sheetsName: String,
    val tableCell: String,
    val fieldCount: String,
    val barcodeColumn: String
)
