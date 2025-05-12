package com.jaewchoi.barcodescanner.viewmodels

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.room.PrimaryKey
import com.jaewchoi.barcodescanner.data.source.local.ScanHistory
import com.jaewchoi.barcodescanner.data.source.network.Record
import com.jaewchoi.barcodescanner.domain.usecase.ClearHistoryUseCase
import com.jaewchoi.barcodescanner.domain.usecase.DeleteHistoryUseCase
import com.jaewchoi.barcodescanner.domain.usecase.FetchHistoryUseCase
import com.jaewchoi.barcodescanner.domain.usecase.FetchRecordUseCase
import com.jaewchoi.barcodescanner.ui.model.LoadState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ScanHistoryViewModel @Inject constructor(
    private val fetchHistoryUseCase: FetchHistoryUseCase,
    private val fetchRecordUseCase: FetchRecordUseCase,
    private val deleteHistoryUseCase: DeleteHistoryUseCase,
    private val clearHistoryUseCase: ClearHistoryUseCase
) : ViewModel() {

    private val _histories = MutableLiveData<List<ScanHistory>>(emptyList())
    val histories: LiveData<List<ScanHistory>>
        get() = _histories

    private val _record = MutableLiveData<Record?>(null)
    val record: LiveData<Record?>
        get() = _record

    private val _loadState = MutableLiveData(LoadState.IDLE)
    val loadState: LiveData<LoadState>
        get() = _loadState

    fun initHistories() {
        fetchHistory()
    }

    fun clearAllHistory() {
        viewModelScope.launch(Dispatchers.IO) {
            clearHistoryUseCase()
            fetchHistory()
        }
    }

    fun deleteHistory(id: Long) {
        viewModelScope.launch(Dispatchers.IO) {
            deleteHistoryUseCase(id)
            fetchHistory()
        }
    }

    fun fetchHistory() {
        viewModelScope.launch(Dispatchers.IO) {
            val histories = fetchHistoryUseCase()
            _histories.postValue(histories)
        }
    }

    fun fetchRecord(barcodeValue: String) {
        viewModelScope.launch {
            try {
                _record.value = null
                _loadState.value = LoadState.LOADING
                _record.value = fetchRecordUseCase(barcodeValue)
                _loadState.value = LoadState.SUCCESS
            } catch (e: Exception) {
                _loadState.value = LoadState.ERROR
                Log.e("record_", e.message.toString())
            }
        }
    }
}