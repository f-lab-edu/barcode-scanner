package com.jaewchoi.barcodescanner.domain.usecase

import com.google.mlkit.vision.barcode.common.Barcode
import com.jaewchoi.barcodescanner.data.repository.ScanHistoryRepository
import com.jaewchoi.barcodescanner.data.model.local.ScanHistory
import com.jaewchoi.barcodescanner.utils.BarcodeUtils
import javax.inject.Inject

class SaveHistoryUseCase @Inject  constructor(
    private val repository: ScanHistoryRepository
) {
    suspend operator fun invoke(barcode: Barcode) {
        val history = ScanHistory(
            barcode = barcode.rawValue ?: "",
            url = BarcodeUtils.extractUrl(barcode),
            savedTimeMilli = System.currentTimeMillis()
        )
        repository.insertHistory(history)
    }
}