package com.jaewchoi.barcodescanner.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.jaewchoi.barcodescanner.databinding.FragmentRecordDialogBinding

class RecordDialogFragment : DialogFragment() {
    private val binding by lazy {
        FragmentRecordDialogBinding.inflate(layoutInflater).apply {
            lifecycleOwner = this@RecordDialogFragment
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding.btnCancel.setOnClickListener {
            dismiss()
        }
        return binding.root
    }
}