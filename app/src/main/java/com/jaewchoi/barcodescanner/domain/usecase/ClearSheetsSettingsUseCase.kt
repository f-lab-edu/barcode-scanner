package com.jaewchoi.barcodescanner.domain.usecase

import com.jaewchoi.barcodescanner.data.repository.SettingRepository
import javax.inject.Inject

class ClearSheetsSettingsUseCase @Inject constructor(
    private val repository: SettingRepository
) {
    suspend operator fun invoke() {
        repository.clearSheetsSetting()
    }
}