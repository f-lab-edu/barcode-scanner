package com.jaewchoi.barcodescanner.ui

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.jaewchoi.barcodescanner.R
import com.jaewchoi.barcodescanner.adapters.HistoryListAdapter
import com.jaewchoi.barcodescanner.databinding.FragmentScanHistoryBinding
import com.jaewchoi.barcodescanner.utils.openUrlInBrowser
import com.jaewchoi.barcodescanner.viewmodels.ScanHistoryViewModel

class ScanHistoryFragment : Fragment() {
    private val binding by lazy {
        FragmentScanHistoryBinding.inflate(layoutInflater).apply {
            lifecycleOwner = this@ScanHistoryFragment
        }
    }

    private val viewModel: ScanHistoryViewModel by activityViewModels()


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding.viewModel = viewModel
        val adapter = HistoryListAdapter(
            { id -> viewModel.deleteHistory(id) },
            { barcodeValue ->
                findNavController().navigate(R.id.action_scanHistoryFragment_to_recordViewFragment)
                viewModel.fetchRecord(barcodeValue)
            },
            { url ->
                if (url.isNullOrBlank()) {
                    Toast.makeText(
                        requireContext(),
                        getString(R.string.fail_open_url),
                        Toast.LENGTH_SHORT
                    ).show()
                } else {
                    requireContext().openUrlInBrowser(url)
                }
            }
        )
        binding.historyList.adapter = adapter
        return binding.root
    }
}