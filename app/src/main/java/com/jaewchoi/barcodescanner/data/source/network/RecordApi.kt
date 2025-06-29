package com.jaewchoi.barcodescanner.data.source.network

import com.jaewchoi.barcodescanner.data.model.network.Record
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query


interface RecordApi {
    @GET("searchRecord")
    suspend fun searchRecordByBarcode(
        @Query("barcode") barcode: String,
        @Query("fileID") fileID: String,
        @Query("sheetsName") sheetsName: String,
        @Query("tableCell") fieldAddress: String,
        @Query("fieldCount") fieldCount: String,
        @Query("barcodeColumn") barcodeColumn: String,
    ): Record
}