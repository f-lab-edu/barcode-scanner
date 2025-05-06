package com.jaewchoi.barcodescanner.data.repository

import com.jaewchoi.barcodescanner.data.source.local.SheetsSettingStorage
import com.jaewchoi.barcodescanner.domain.model.SheetsSettings
import javax.inject.Inject

class SettingRepository @Inject constructor(
    private val sheetsStorage: SheetsSettingStorage
) {

    suspend fun getSheetsSetting() = sheetsStorage.getSettings()

    suspend fun clearSheetsSetting() {
        sheetsStorage.clearSettings()
    }

    suspend fun saveSheetsSetting(settings: SheetsSettings) {
        sheetsStorage.saveSettings(settings)
    }
}