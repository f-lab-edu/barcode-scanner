package com.jaewchoi.barcodescanner.viewmodels

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.mlkit.vision.barcode.common.Barcode
import com.jaewchoi.barcodescanner.data.model.network.Record
import com.jaewchoi.barcodescanner.domain.usecase.FetchRecordUseCase
import com.jaewchoi.barcodescanner.domain.usecase.SaveHistoryUseCase
import com.jaewchoi.barcodescanner.ui.model.LoadState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CameraViewModel @Inject constructor(
    private val fetchRecordUseCase: FetchRecordUseCase,
    private val saveHistoryUseCase: SaveHistoryUseCase,
) : ViewModel() {
    private val _barcode = MutableLiveData<Barcode?>(null)
    val barcode: LiveData<Barcode?>
        get() = _barcode
    private val _isFlashOn = MutableLiveData(false)
    val isFlashOn: LiveData<Boolean>
        get() = _isFlashOn

    private val _record = MutableLiveData<Record?>(null)
    val record: LiveData<Record?>
        get() = _record

    private val _loadState = MutableLiveData(LoadState.IDLE)
    val loadState: LiveData<LoadState>
        get() = _loadState

    fun toggleFlash() {
        val value = _isFlashOn.value
        value?.let {
            _isFlashOn.value = !value
        }
    }

    fun setBarcodeData(barcode: Barcode) {
        _barcode.value = barcode
        viewModelScope.launch(Dispatchers.IO) {
            saveHistoryUseCase(barcode)
        }
    }

    fun barcodeDialogDismiss() {
        _loadState.value = LoadState.IDLE
        _barcode.value = null
        _record.value = null
    }

    fun requestRecord() {
        viewModelScope.launch {
            try {
                _loadState.value = LoadState.LOADING
                val barcodeValue = _barcode.value?.rawValue ?: throw Exception("barcode is null")
                _record.value = fetchRecordUseCase(barcodeValue)
                _loadState.value = LoadState.SUCCESS
            } catch (e: Exception) {
                _loadState.value = LoadState.ERROR
                Log.e("record_", e.message.toString())
            }
        }
    }

}