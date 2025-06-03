package com.jaewchoi.barcodescanner.ui

import android.app.Dialog
import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.activityViewModels
import com.jaewchoi.barcodescanner.R
import com.jaewchoi.barcodescanner.adapters.RecordListAdapters
import com.jaewchoi.barcodescanner.databinding.FragmentBarcodeDialogBinding
import com.jaewchoi.barcodescanner.viewmodels.CameraViewModel
import com.jaewchoi.barcodescanner.viewmodels.ScanHistoryViewModel
import androidx.core.net.toUri
import com.jaewchoi.barcodescanner.utils.UiEvent
import com.jaewchoi.barcodescanner.utils.openUrlInBrowser

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
            viewModel.onCancelClicked()
        }
        binding.copyButton.setOnClickListener {
            viewModel.onCopyClicked()
        }
        binding.searchSheetButton.setOnClickListener {
            viewModel.onSearchSheetClicked()
        }
        binding.urlButton.setOnClickListener {
            viewModel.onUrlClicked()
        }

        viewModel.uiEvent.observe(viewLifecycleOwner) { event ->
            event.getContentIfNotHandled()?.let { uiEvent ->
                when (uiEvent) {
                    is UiEvent.DismissDialog -> {
                        dismiss()
                    }

                    is UiEvent.ShowToast -> {
                        Toast.makeText(
                            requireContext(),
                            getString(uiEvent.resId),
                            Toast.LENGTH_SHORT
                        ).show()
                    }

                    is UiEvent.CopyToClipboard -> {
                        val clipboard =
                            requireContext().getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
                        val clip = ClipData.newPlainText("barcode", uiEvent.text)
                        clipboard.setPrimaryClip(clip)
                    }

                    is UiEvent.OpenUrl -> {
                        requireContext().openUrlInBrowser(uiEvent.url)
                    }
                }
            }
        }

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
        historyViewModel.fetchHistory()
        viewModel.initBarcode()
        onDialogDismissed()
    }
}
