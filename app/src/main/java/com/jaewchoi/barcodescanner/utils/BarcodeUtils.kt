package com.jaewchoi.barcodescanner.utils

import android.webkit.URLUtil
import com.google.mlkit.vision.barcode.common.Barcode

object BarcodeUtils {
    fun extractUrl(barcode: Barcode?): String? {
        val candidate = if (barcode?.valueType == Barcode.TYPE_URL) {
            barcode.url?.url
        } else {
            barcode?.rawValue
        } ?: return null

        if (candidate.isBlank()) return null

        return if (URLUtil.isHttpUrl(candidate) || URLUtil.isHttpsUrl(candidate)) {
            candidate
        } else {
            null
        }
    }

}