package com.jaewchoi.barcodescanner

import android.app.Dialog
import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.DialogInterface
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.google.mlkit.vision.barcode.common.Barcode
import com.jaewchoi.barcodescanner.databinding.FragmentBarcodeDialogBinding

class BarcodeDialogFragment(
    private val barcode: Barcode,
    private val onDialogDismissed: () -> Unit
) : DialogFragment() {

    private val binding by lazy {
        FragmentBarcodeDialogBinding.inflate(layoutInflater).apply {
            lifecycleOwner = this@BarcodeDialogFragment
        }
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        setStyle(STYLE_NORMAL, R.style.FullScreenDialogTheme)
        return super.onCreateDialog(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding.formatText.text = barcode.format.toString()
        binding.valueText.text = barcode.rawValue

        binding.cancelButton.setOnClickListener {
            dismiss()
        }
        binding.copyButton.setOnClickListener {
            val text = barcode.rawValue

            val clipboard = requireContext().getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
            val clip = ClipData.newPlainText("barcode", text)
            clipboard.setPrimaryClip(clip)
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
        onDialogDismissed()
    }
}
