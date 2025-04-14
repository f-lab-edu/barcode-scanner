package com.jaewchoi.barcodescanner

import androidx.camera.core.ExperimentalGetImage
import androidx.camera.core.ImageAnalysis
import androidx.camera.core.ImageProxy
import com.google.mlkit.vision.barcode.BarcodeScanner
import com.google.mlkit.vision.barcode.common.Barcode
import com.google.mlkit.vision.common.InputImage


class BarcodeAnalyzer(
    private val scanner: BarcodeScanner,
    private val onBarcodeDetected: (Barcode) -> Unit
) : ImageAnalysis.Analyzer {

    private var hasBarcodeBeenDetected = false

    @ExperimentalGetImage
    override fun analyze(imageProxy: ImageProxy) {
        if (hasBarcodeBeenDetected) {
            imageProxy.close()
            return
        }

        val mediaImage = imageProxy.image
        if (mediaImage != null) {
            val rotationDegrees = imageProxy.imageInfo.rotationDegrees
            val inputImage = InputImage.fromMediaImage(mediaImage, rotationDegrees)

            scanner.process(inputImage)
                .addOnSuccessListener { barcodes ->
                    if (!hasBarcodeBeenDetected && barcodes.isNotEmpty()) {
                        val firstBarcode = barcodes[0]
                        hasBarcodeBeenDetected = true
                        onBarcodeDetected(firstBarcode)
                        hasBarcodeBeenDetected = false
                    }
                }
                .addOnFailureListener { e ->
                    // 바코드 스캔 실패
                }
                .addOnCompleteListener {
                    imageProxy.close()
                }
        } else {
            imageProxy.close()
        }
    }
}
