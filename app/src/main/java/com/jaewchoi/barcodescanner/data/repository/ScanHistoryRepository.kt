package com.jaewchoi.barcodescanner.data.repository

import com.jaewchoi.barcodescanner.data.source.local.ScanHistory
import com.jaewchoi.barcodescanner.data.source.local.ScanHistoryDao
import javax.inject.Inject

class ScanHistoryRepository @Inject constructor(
    private val dao: ScanHistoryDao
) {
    suspend fun getAllHistories(): List<ScanHistory> {
        return dao.getAll()
    }

    suspend fun insertHistory(history: ScanHistory) {
        dao.insert(history)
    }

    suspend fun clearAllHistories() {
        dao.deleteAll()
    }

    suspend fun deleteHistory(id: Long) {
        dao.deleteById(id)
    }
}
