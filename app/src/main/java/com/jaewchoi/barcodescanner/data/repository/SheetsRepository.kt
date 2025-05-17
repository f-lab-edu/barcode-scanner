package com.jaewchoi.barcodescanner.data.repository

import com.jaewchoi.barcodescanner.data.source.local.SheetsSettingStorage
import com.jaewchoi.barcodescanner.data.source.local.TokenStorage
import com.jaewchoi.barcodescanner.data.source.network.Record
import com.jaewchoi.barcodescanner.domain.model.AuthToken
import com.jaewchoi.barcodescanner.data.source.network.RecordApi
import com.jaewchoi.barcodescanner.data.source.network.TokenAuthenticator
import javax.inject.Inject

class SheetsRepository @Inject constructor(
    private val api: RecordApi,
    private val sheetsStorage: SheetsSettingStorage,
    private val tokenAuthenticator: TokenAuthenticator,
) {
    suspend fun fetchRecord(barcode: String): Record {
        val settings = sheetsStorage.getSettings() ?: throw Exception("fetch setting error")
        return tokenAuthenticator.withFreshTokenCall { accessToken ->
            api.searchRecordByBarcode(
                "Bearer $accessToken",
                barcode,
                settings.fileID,
                settings.sheetsName,
                settings.tableCell,
                settings.fieldCount,
                settings.barcodeColumn
            )
        }
    }
}