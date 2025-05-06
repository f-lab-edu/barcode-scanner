package com.jaewchoi.barcodescanner.domain.usecase

import com.jaewchoi.barcodescanner.data.repository.ScanHistoryRepository
import javax.inject.Inject

class DeleteHistoryUseCase @Inject constructor(
    private val repository: ScanHistoryRepository
) {
    suspend operator fun invoke(id: Long) {
        repository.deleteHistory(id)
    }
}