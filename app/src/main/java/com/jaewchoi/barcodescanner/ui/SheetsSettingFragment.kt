package com.jaewchoi.barcodescanner.ui

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.jaewchoi.barcodescanner.databinding.FragmentSheetsSettingBinding
import com.jaewchoi.barcodescanner.viewmodels.SettingViewModel
import androidx.core.net.toUri
import com.jaewchoi.barcodescanner.R

class SheetsSettingFragment : Fragment() {
    private val binding by lazy {
        FragmentSheetsSettingBinding.inflate(layoutInflater).apply {
            lifecycleOwner = this@SheetsSettingFragment
        }
    }

    private val viewModel: SettingViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding.viewModel = viewModel
        binding.btnSave.setOnClickListener {
            viewModel.saveSheetsSetting { msg ->
                Toast.makeText(requireContext(), msg, Toast.LENGTH_SHORT).show()
            }
        }

        binding.fabHelp.setOnClickListener {
            val guideUrl = getString(R.string.sheet_setting_guide_url)
            val intent = Intent(Intent.ACTION_VIEW, guideUrl.toUri())
            startActivity(intent)
        }
        return binding.root
    }
}