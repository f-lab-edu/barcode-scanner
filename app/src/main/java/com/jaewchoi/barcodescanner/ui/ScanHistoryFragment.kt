package com.jaewchoi.barcodescanner.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.core.view.MenuProvider
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
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
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        binding.viewModel = viewModel

        setListAdapter()
        setToolbarMenu()
        return binding.root
    }

    private fun setToolbarMenu() {
        requireActivity().addMenuProvider(
            object : MenuProvider {
                override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                    menuInflater.inflate(R.menu.toolbar_menu, menu)
                    val actionView = menu.findItem(R.id.action_clear_history).actionView
                    actionView?.findViewById<Button>(R.id.btn_clear)?.setOnClickListener {
                        viewModel.clearAllHistory()
                    }
                }

                override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
                    return when (menuItem.itemId) {
                        R.id.action_clear_history -> {
                            viewModel.clearAllHistory()
                            true
                        }

                        else -> false
                    }
                }
            }, viewLifecycleOwner, Lifecycle.State.RESUMED
        )
    }


    private fun setListAdapter() {
        val adapter = HistoryListAdapter({ id -> viewModel.deleteHistory(id) }, { barcodeValue ->
            findNavController().navigate(R.id.action_scanHistoryFragment_to_recordViewFragment)
            viewModel.fetchRecord(barcodeValue)
        }, { url ->
            if (url.isNullOrBlank()) {
                Toast.makeText(
                    requireContext(), getString(R.string.fail_open_url), Toast.LENGTH_SHORT
                ).show()
            } else {
                requireContext().openUrlInBrowser(url)
            }
        })
        binding.historyList.adapter = adapter
    }
}
