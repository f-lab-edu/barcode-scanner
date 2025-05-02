package com.jaewchoi.barcodescanner.viewmodels

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.mlkit.vision.barcode.common.Barcode
import com.jaewchoi.barcodescanner.data.source.local.TokenStorage
import com.jaewchoi.barcodescanner.data.source.network.Record
import com.jaewchoi.barcodescanner.network.RecordApi
import com.jaewchoi.barcodescanner.ui.model.LoadState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CameraViewModel @Inject constructor(
    private val recordApi: RecordApi,
    private val tokenStorage: TokenStorage
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
    }

    fun barcodeDialogDismiss() {
        _loadState.value = LoadState.IDLE
        _barcode.value = null
    }

    fun requestRecord() {
        viewModelScope.launch {
            try {
                _loadState.value = LoadState.LOADING
                val token = tokenStorage.getAuthToken()?.accessToken ?: throw Exception()
                Log.d("product_", ("token : $token"))
                val response = recordApi.searchProductByBarcode("Bearer $token", "8805678")
                Log.d("product_", "response : $response")
                _record.value = response
                _loadState.value = LoadState.SUCCESS
            } catch (e: Exception) {
                _loadState.value = LoadState.ERROR
                Log.e("product_", e.message.toString())
            }
        }
    }

}