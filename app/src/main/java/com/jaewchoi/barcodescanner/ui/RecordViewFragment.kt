package com.jaewchoi.barcodescanner.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.jaewchoi.barcodescanner.adapters.RecordListAdapters
import com.jaewchoi.barcodescanner.databinding.FragmentRecordViewBinding
import com.jaewchoi.barcodescanner.viewmodels.ScanHistoryViewModel

class RecordViewFragment() : Fragment() {
    val binding by lazy {
        FragmentRecordViewBinding.inflate(layoutInflater).apply {
            lifecycleOwner = this@RecordViewFragment
        }
    }

    val viewModel: ScanHistoryViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding.viewModel = viewModel
        val adapter = RecordListAdapters(true)
        binding.recordList.adapter = adapter

        binding.btnCancel.setOnClickListener {
            findNavController().popBackStack()
        }
        return binding.root
    }
}