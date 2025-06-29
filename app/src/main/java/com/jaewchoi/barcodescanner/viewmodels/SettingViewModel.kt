package com.jaewchoi.barcodescanner.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jaewchoi.barcodescanner.domain.model.SheetsSettings
import com.jaewchoi.barcodescanner.domain.usecase.FetchSheetsSettingsUseCase
import com.jaewchoi.barcodescanner.domain.usecase.SaveSheetsSettingsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SettingViewModel @Inject constructor(
    private val saveSheetsSettingsUseCase: SaveSheetsSettingsUseCase,
    private val fetchSheetsSettingsUseCase: FetchSheetsSettingsUseCase,
) : ViewModel() {

    private val _sheetsSettings = MutableLiveData<SheetsSettings?>(null)

    fun initSettings() {
        viewModelScope.launch {
            try {
                val settings = fetchSheetsSettingsUseCase()
                _sheetsSettings.postValue(settings)
            } catch (e: Exception) {

            }
        }
    }

    fun saveSheetsSetting(onCallback: (String) -> Unit) {
        viewModelScope.launch {
            try {
                val settings = _sheetsSettings.value
                checkNotNull(settings)
                saveSheetsSettingsUseCase(settings)
                onCallback("Save success.")
            } catch (e: Exception) {
                onCallback("Save fail.")
            }
        }
    }

    /**
     * bindText's two-way data binding getter setter
     */
    var fileID: String
        get() = _sheetsSettings.value?.fileID.orEmpty()
        set(value) {
            val current = _sheetsSettings.value
            _sheetsSettings.value = current?.copy(fileID = value)
                ?: SheetsSettings(value, "", "", "", "")
        }

    var sheetName: String
        get() = _sheetsSettings.value?.sheetsName.orEmpty()
        set(value) {
            val current = _sheetsSettings.value
            _sheetsSettings.value = current?.copy(sheetsName = value)
                ?: SheetsSettings("", value, "", "", "")
        }

    var tableCell: String
        get() = _sheetsSettings.value?.tableCell.orEmpty()
        set(value) {
            val current = _sheetsSettings.value
            _sheetsSettings.value = current?.copy(tableCell = value)
                ?: SheetsSettings("", "", value, "", "")
        }

    var fieldCount: String
        get() = _sheetsSettings.value?.fieldCount.orEmpty()
        set(value) {
            val current = _sheetsSettings.value
            _sheetsSettings.value = current?.copy(fieldCount = value)
                ?: SheetsSettings("", "", "", value, "")
        }

    var barcodeColumn: String
        get() = _sheetsSettings.value?.barcodeColumn.orEmpty()
        set(value) {
            val current = _sheetsSettings.value
            _sheetsSettings.value = current?.copy(barcodeColumn = value)
                ?: SheetsSettings("", "", "", "", value)
        }
}