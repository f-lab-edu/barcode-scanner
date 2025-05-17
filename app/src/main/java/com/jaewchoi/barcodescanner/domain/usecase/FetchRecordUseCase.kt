package com.jaewchoi.barcodescanner.domain.usecase

import com.jaewchoi.barcodescanner.data.repository.SheetsRepository
import com.jaewchoi.barcodescanner.data.source.network.Record
import retrofit2.Response
import javax.inject.Inject

class FetchRecordUseCase @Inject constructor(
    private val repository: SheetsRepository
) {
    suspend operator fun invoke(barcode: String): Record {
        return repository.fetchRecord(barcode)
    }
}