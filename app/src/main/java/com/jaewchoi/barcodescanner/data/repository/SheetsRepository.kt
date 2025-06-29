package com.jaewchoi.barcodescanner.data.repository

import com.jaewchoi.barcodescanner.data.source.local.SheetsSettingStorage
import com.jaewchoi.barcodescanner.data.model.network.Record
import com.jaewchoi.barcodescanner.data.source.network.RecordApi
import com.jaewchoi.barcodescanner.data.source.network.TokenAuthenticator
import javax.inject.Inject

class SheetsRepository @Inject constructor(
    private val api: RecordApi,
    private val sheetsStorage: SheetsSettingStorage,
) {
    suspend fun fetchRecord(barcode: String): Record {
        val settings = sheetsStorage.getSettings() ?: throw Exception("fetch setting error")
        return api.searchRecordByBarcode(
            barcode,
            settings.fileID,
            settings.sheetsName,
            settings.tableCell,
            settings.fieldCount,
            settings.barcodeColumn
        )
    }
}