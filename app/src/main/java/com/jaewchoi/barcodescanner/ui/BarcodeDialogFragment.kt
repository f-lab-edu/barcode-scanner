package com.jaewchoi.barcodescanner.ui

import android.app.Dialog
import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.DialogInterface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.activityViewModels
import com.google.mlkit.vision.barcode.common.Barcode
import com.jaewchoi.barcodescanner.R
import com.jaewchoi.barcodescanner.adapters.RecordListAdapters
import com.jaewchoi.barcodescanner.databinding.FragmentBarcodeDialogBinding
import com.jaewchoi.barcodescanner.viewmodels.CameraViewModel
import com.jaewchoi.barcodescanner.viewmodels.ScanHistoryViewModel

class BarcodeDialogFragment(
    private val onDialogDismissed: () -> Unit
) : DialogFragment() {

    private val binding by lazy {
        FragmentBarcodeDialogBinding.inflate(layoutInflater).apply {
            lifecycleOwner = this@BarcodeDialogFragment
        }
    }

    private val viewModel: CameraViewModel by activityViewModels()
    private val historyViewModel: ScanHistoryViewModel by activityViewModels()

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        setStyle(STYLE_NORMAL, R.style.FullScreenDialogTheme)
        return super.onCreateDialog(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding.viewModel = viewModel

        val recyclerAdapter = RecordListAdapters()
        binding.recordList.adapter = recyclerAdapter

        binding.cancelButton.setOnClickListener {
            dismiss()
        }
        binding.copyButton.setOnClickListener {
            val text = viewModel.barcode.value?.rawValue
            if (text == null) {
                Toast.makeText(
                    requireContext(),
                    getString(R.string.fail_copy_msg),
                    Toast.LENGTH_SHORT
                ).show()
                return@setOnClickListener
            }
            val clipboard =
                requireContext().getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
            val clip = ClipData.newPlainText("barcode", text)
            clipboard.setPrimaryClip(clip)
        }
        binding.searchSheetButton.setOnClickListener {
            viewModel.requestRecord()
        }
        // 다른 버튼의 클릭 리스너도 추후 구현 예정

        return binding.root
    }

    override fun onStart() {
        super.onStart()
        dialog?.window?.setLayout(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.MATCH_PARENT
        )
    }

    override fun onDismiss(dialog: DialogInterface) {
        super.onDismiss(dialog)
        historyViewModel.initHistories()
        viewModel.barcodeDialogDismiss()
        onDialogDismissed()
    }
}
