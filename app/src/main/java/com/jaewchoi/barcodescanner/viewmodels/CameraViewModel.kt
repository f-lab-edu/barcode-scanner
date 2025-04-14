package com.jaewchoi.barcodescanner.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class CameraViewModel : ViewModel() {
    private val _isFlashOn = MutableLiveData(false)

    val isFlashOn: LiveData<Boolean>
        get() = _isFlashOn

    fun toggleFlash() {
        val value = _isFlashOn.value
        value?.let {
            _isFlashOn.value = !value
        }
    }
}