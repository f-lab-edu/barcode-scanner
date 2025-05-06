package com.jaewchoi.barcodescanner.domain.usecase

import com.jaewchoi.barcodescanner.data.repository.ScanHistoryRepository
import javax.inject.Inject

class ClearHistoryUseCase @Inject constructor(
    private val repository: ScanHistoryRepository
) {
    suspend operator fun invoke() {
        repository.clearAllHistories()
    }
}