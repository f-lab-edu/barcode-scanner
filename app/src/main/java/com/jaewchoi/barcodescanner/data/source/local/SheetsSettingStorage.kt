package com.jaewchoi.barcodescanner.data.source.local

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.jaewchoi.barcodescanner.domain.model.SheetsSettings
import kotlinx.coroutines.flow.first
import javax.inject.Inject

private val Context.sheetsSettingDataStore: DataStore<Preferences> by preferencesDataStore(name = "sheet_setting_prefs")

class SheetsSettingStorage @Inject constructor(
    private val context: Context
) {
    private object Keys {
        val FILE_ID = stringPreferencesKey("file_id")
        val SHEETS_NAME = stringPreferencesKey("sheets_name")
        val TABLE_CELL = stringPreferencesKey("table_cell")
        val FIELD_COUNT = stringPreferencesKey("field_count")
        val BARCODE_COLUMN = stringPreferencesKey("barcode_column")
    }

    suspend fun saveSettings(sheetsSettings: SheetsSettings) {
        context.sheetsSettingDataStore.edit { prefs ->
            prefs[Keys.FILE_ID] = sheetsSettings.fileID
            prefs[Keys.SHEETS_NAME] = sheetsSettings.sheetsName
            prefs[Keys.TABLE_CELL] = sheetsSettings.tableCell
            prefs[Keys.FIELD_COUNT] = sheetsSettings.fieldCount
            prefs[Keys.BARCODE_COLUMN] = sheetsSettings.barcodeColumn
        }
    }

    suspend fun clearSettings() {
        context.sheetsSettingDataStore.edit { prefs ->
            prefs.remove(Keys.FILE_ID)
            prefs.remove(Keys.SHEETS_NAME)
            prefs.remove(Keys.TABLE_CELL)
            prefs.remove(Keys.FIELD_COUNT)
            prefs.remove(Keys.BARCODE_COLUMN)
        }
    }

    suspend fun getSettings(): SheetsSettings? {
        val prefs = context.sheetsSettingDataStore.data.first()
        val fieldID = prefs[Keys.FILE_ID] ?: return null
        val sheetsName = prefs[Keys.SHEETS_NAME] ?: return null
        val fieldCount = prefs[Keys.FIELD_COUNT] ?: return null
        val tableCell = prefs[Keys.TABLE_CELL] ?: return null
        val barcodeColumn = prefs[Keys.BARCODE_COLUMN] ?: return null
        return SheetsSettings(
            fieldID,
            sheetsName,
            tableCell,
            fieldCount,
            barcodeColumn
        )
    }
}