package com.jaewchoi.barcodescanner

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class BarcodeScannerApplication : Application() {
    override fun onCreate() {
        super.onCreate()
    }
}