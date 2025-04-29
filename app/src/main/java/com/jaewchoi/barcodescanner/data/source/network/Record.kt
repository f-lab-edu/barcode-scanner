package com.jaewchoi.barcodescanner.data.source.network

data class Record(
    // record 동적으로 변경가능 하도록
    val id: String,
    val name: String,
    val price: String,
    val barcode: String
)
