package com.jaewchoi.barcodescanner.domain.usecase

import com.jaewchoi.barcodescanner.data.repository.ScanHistoryRepository
import com.jaewchoi.barcodescanner.data.source.local.ScanHistory
import javax.inject.Inject

class SaveHistoryUseCase @Inject constructor(
    private val repository: ScanHistoryRepository
) {
    suspend operator fun invoke(barcodeValue: String) {
        val history = ScanHistory(
            barcode = barcodeValue,
            savedTimeMilli = System.currentTimeMillis()
        )
        repository.insertHistory(history)
    }
}