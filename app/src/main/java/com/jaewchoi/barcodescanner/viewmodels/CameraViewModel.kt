package com.jaewchoi.barcodescanner.viewmodels

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jaewchoi.barcodescanner.data.source.local.TokenStorage
import com.jaewchoi.barcodescanner.network.RecordApi
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CameraViewModel @Inject constructor(
    private val productApi: RecordApi,
    private val tokenStorage: TokenStorage
) : ViewModel() {
//    private val _barcode = MutableLiveData("")
//    val barcode: LiveData<String>
//        get() = _barcode
    private val _isFlashOn = MutableLiveData(false)

    val isFlashOn: LiveData<Boolean>
        get() = _isFlashOn

    fun toggleFlash() {
        val value = _isFlashOn.value
        value?.let {
            _isFlashOn.value = !value
        }
    }

    fun getProduct() {
        viewModelScope.launch {
            try {
                val token = tokenStorage.getAuthToken()?.accessToken ?: throw Exception()
                Log.d("product_", ("token : $token"))
                val response = productApi.searchProductByBarcode("Bearer $token", "8805678")
                Log.d("product_", "response : $response")
            } catch (e: Exception) {
                Log.e("product_", e.message.toString())
            }
        }
    }

}