package com.jaewchoi.barcodescanner.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.jaewchoi.barcodescanner.databinding.FragmentSheetsSettingBinding
import com.jaewchoi.barcodescanner.domain.model.SheetsSettings
import com.jaewchoi.barcodescanner.viewmodels.SettingViewModel

class SheetsSettingFragment : Fragment() {
    private val binding by lazy {
        FragmentSheetsSettingBinding.inflate(layoutInflater).apply {
            lifecycleOwner = this@SheetsSettingFragment
        }
    }

    private val viewModel: SettingViewModel by activityViewModels()

    /*
    파일 ID, 시트 이름, 필드 개수, 필드 시작 주소
    저장 버튼 누를시 설정 저장(local data store)
    해당 설정시 모든 내용이 설정되어야 한다.
    로그아웃시 모든 설정 내용 삭제
     */

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
        return binding.root
    }
}