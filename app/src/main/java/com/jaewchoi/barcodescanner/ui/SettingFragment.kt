package com.jaewchoi.barcodescanner.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.jaewchoi.barcodescanner.R
import com.jaewchoi.barcodescanner.databinding.FragmentSettingBinding
import com.jaewchoi.barcodescanner.viewmodels.SettingViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import net.openid.appauth.AuthorizationService
import javax.inject.Inject

@AndroidEntryPoint
class SettingFragment : Fragment() {
    private val binding by lazy {
        FragmentSettingBinding.inflate(layoutInflater).apply {
            lifecycleOwner = this@SettingFragment
        }
    }
    private val viewModel: SettingViewModel by activityViewModels()

    @Inject
    lateinit var authService: AuthorizationService

    private val authLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            viewModel.onAuthResponse(result.data) {
                Toast.makeText(
                    requireContext(),
                    getString(R.string.login_fail_msg), Toast.LENGTH_SHORT
                )
            }
        }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding.viewModel = viewModel

        binding.layoutSheetSetting.setOnClickListener {
            findNavController().navigate(R.id.action_settingFragment_to_sheetsSettingFragment)
        }

        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.authRequest.collect { request ->
                    val requestIntent = authService.getAuthorizationRequestIntent(request)
                    authLauncher.launch(requestIntent)
                }
            }
        }
        return binding.root
    }
}