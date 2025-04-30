package com.jaewchoi.barcodescanner.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.camera.core.Camera
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageAnalysis
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.core.content.ContextCompat
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.google.mlkit.vision.barcode.BarcodeScanner
import com.google.mlkit.vision.barcode.BarcodeScannerOptions
import com.google.mlkit.vision.barcode.BarcodeScanning
import com.google.mlkit.vision.barcode.common.Barcode
import com.jaewchoi.barcodescanner.R
import com.jaewchoi.barcodescanner.databinding.FragmentCameraBinding
import com.jaewchoi.barcodescanner.utils.BarcodeAnalyzer
import com.jaewchoi.barcodescanner.viewmodels.CameraViewModel
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class CameraFragment : Fragment() {
    private val binding by lazy {
        FragmentCameraBinding.inflate(layoutInflater).apply {
            lifecycleOwner = this@CameraFragment
        }
    }
    private val viewModel: CameraViewModel by activityViewModels()
    private val cameraExecutor: ExecutorService by lazy { Executors.newSingleThreadExecutor() }
    private var camera: Camera? = null
    private lateinit var barcodeScanner: BarcodeScanner
    private var cameraProvider: ProcessCameraProvider? = null
    private var barcodeAnalyzer: BarcodeAnalyzer? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding.viewModel = viewModel
        startCamera()
        initFlash()
        binding.btnSetting.setOnClickListener {
            findNavController().navigate(R.id.action_cameraFragment_to_settingFragment)
        }
        return binding.root
    }

    private fun startCamera() {
        val context = requireContext()
        val options = BarcodeScannerOptions.Builder()
            .setBarcodeFormats(Barcode.FORMAT_ALL_FORMATS)
            .build()
        val cameraProviderFuture = ProcessCameraProvider.getInstance(context)

        barcodeScanner = BarcodeScanning.getClient(options)
        barcodeAnalyzer = BarcodeAnalyzer(barcodeScanner) { barcode -> scanBarcode(barcode) }

        cameraProviderFuture.addListener({
            cameraProvider = cameraProviderFuture.get()
            bindCameraUseCases()
        }, ContextCompat.getMainExecutor(context))
    }

    private fun initFlash() {
        binding.btnFlash.setOnClickListener {
            if (camera?.cameraInfo?.hasFlashUnit() == true) {
                viewModel.toggleFlash()
            } else {
                Toast.makeText(requireContext(), "device does not have flash", Toast.LENGTH_SHORT)
                    .show()
            }
        }
        viewModel.isFlashOn.observe(viewLifecycleOwner) { isFlashOn ->
            camera?.cameraControl?.enableTorch(isFlashOn)
        }
    }

    private fun bindCameraUseCases() {
        val preview = Preview.Builder().build().also {
            it.surfaceProvider = binding.cameraPreview.surfaceProvider
        }
        val imageAnalysis = ImageAnalysis.Builder()
            .setBackpressureStrategy(ImageAnalysis.STRATEGY_KEEP_ONLY_LATEST)
            .build()
            .also {
                it.setAnalyzer(
                    cameraExecutor,
                    barcodeAnalyzer ?: BarcodeAnalyzer(barcodeScanner) { barcode ->
                        scanBarcode(
                            barcode
                        )
                    })
            }
        val cameraSelector = CameraSelector.DEFAULT_BACK_CAMERA

        try {
            camera = cameraProvider?.bindToLifecycle(
                this, cameraSelector, preview, imageAnalysis
            )
        } catch (exc: Exception) {
            exc.printStackTrace()
        }
    }

    private fun scanBarcode(barcode: Barcode) {
        val dialogFragment = BarcodeDialogFragment(barcode) { bindCameraUseCases() }
        cameraProvider?.unbindAll()
        dialogFragment.show(parentFragmentManager, "BarcodeDialog")
    }

    override fun onDestroy() {
        super.onDestroy()
        cameraExecutor.shutdown()
        barcodeScanner.close()
    }
}