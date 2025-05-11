package com.jaewchoi.barcodescanner.data.source.network

import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query


interface RecordApi {
    @GET("searchRecord")
    suspend fun searchRecordByBarcode(
        @Header("Authorization") bearerToken: String,
        @Query("barcode") barcode: String,
        @Query("fileID") fileID: String,
        @Query("sheetsName") sheetsName: String,
        @Query("tableCell") fieldAddress: String,
        @Query("fieldCount") fieldCount: String,
        @Query("barcodeColumn") barcodeColumn: String,
    ): Record
}