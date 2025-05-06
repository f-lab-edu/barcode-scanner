package com.jaewchoi.barcodescanner.domain.usecase

import com.jaewchoi.barcodescanner.data.repository.SettingRepository
import com.jaewchoi.barcodescanner.domain.model.SheetsSettings
import com.jaewchoi.barcodescanner.utils.SettingValidator
import javax.inject.Inject

class SaveSheetsSettingsUseCase @Inject constructor(
    private val repository: SettingRepository
) {
    suspend operator fun invoke(settings: SheetsSettings) {
        if (!SettingValidator.checkSheetsSettingValid(settings))
            throw Exception("valid error")
        repository.saveSheetsSetting(settings)
    }
}