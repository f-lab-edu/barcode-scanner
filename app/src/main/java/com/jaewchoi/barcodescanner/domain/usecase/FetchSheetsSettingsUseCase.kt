package com.jaewchoi.barcodescanner.domain.usecase

import com.jaewchoi.barcodescanner.data.repository.SettingRepository
import com.jaewchoi.barcodescanner.domain.model.SheetsSettings
import javax.inject.Inject

class FetchSheetsSettingsUseCase @Inject constructor(
    private val repository: SettingRepository
) {
    suspend operator fun invoke(): SheetsSettings? {
        return repository.getSheetsSetting()
    }
}