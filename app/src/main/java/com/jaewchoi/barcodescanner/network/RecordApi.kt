package com.jaewchoi.barcodescanner.network

import com.jaewchoi.barcodescanner.data.source.network.Record
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query


interface RecordApi {
    @GET("searchProduct")
    suspend fun searchProductByBarcode(
        @Header("Authorization") bearerToken: String,
        @Query("barcode") barcode: String
    ): Record
}