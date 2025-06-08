package com.jaewchoi.barcodescanner.viewmodels

import android.content.Intent
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.map
import androidx.lifecycle.viewModelScope
import com.jaewchoi.barcodescanner.data.model.network.UserInfo
import com.jaewchoi.barcodescanner.domain.model.SheetsSettings
import com.jaewchoi.barcodescanner.domain.usecase.FetchGoogleUserUseCase
import com.jaewchoi.barcodescanner.domain.usecase.FetchSheetsSettingsUseCase
import com.jaewchoi.barcodescanner.domain.usecase.HandleGoogleAuthUseCase
import com.jaewchoi.barcodescanner.domain.usecase.PerformLogoutUseCase
import com.jaewchoi.barcodescanner.domain.usecase.RequestGoogleAuthUseCase
import com.jaewchoi.barcodescanner.domain.usecase.SaveSheetsSettingsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import net.openid.appauth.AuthorizationException
import net.openid.appauth.AuthorizationRequest
import net.openid.appauth.AuthorizationResponse
import javax.inject.Inject

@HiltViewModel
class SettingViewModel @Inject constructor(
    private val requestGoogleAuthUseCase: RequestGoogleAuthUseCase,
    private val handleGoogleAuthUseCase: HandleGoogleAuthUseCase,
    private val fetchGoogleUserUseCase: FetchGoogleUserUseCase,
    private val performLogoutUseCase: PerformLogoutUseCase,
    private val saveSheetsSettingsUseCase: SaveSheetsSettingsUseCase,
    private val fetchSheetsSettingsUseCase: FetchSheetsSettingsUseCase,
) : ViewModel() {

    private val _authRequest = Channel<AuthorizationRequest>(Channel.BUFFERED)
    val authRequest = _authRequest.receiveAsFlow()

    private val _userInfo = MutableLiveData<UserInfo>(null)
    val userInfo: LiveData<UserInfo>
        get() = _userInfo

    val isLogin: LiveData<Boolean> = _userInfo.map { it != null }

    private val _sheetsSettings = MutableLiveData<SheetsSettings?>(null)

    fun initSettings() {
        viewModelScope.launch {
            try {
                val settings = fetchSheetsSettingsUseCase()
                _sheetsSettings.postValue(settings)
                val userinfo = fetchGoogleUserUseCase()
                _userInfo.postValue(userinfo)
            } catch (e: Exception) {
                _userInfo.value = null
            }
        }
    }

    fun onLoginClicked() {
        viewModelScope.launch {
            val request = requestGoogleAuthUseCase()
            _authRequest.send(request)
        }
    }

    fun onLogoutClicked() {
        viewModelScope.launch {
            performLogoutUseCase()
            _userInfo.postValue(null)
        }
    }

    fun onAuthResponse(result: Intent?, failCallback: () -> Unit) {
        viewModelScope.launch {
            try {
                if (result == null) throw Exception("auth intent is null")

                val authResponse = AuthorizationResponse.fromIntent(result)
                    ?: throw AuthorizationException.fromIntent(result)
                        ?: Exception("response is null")

                handleGoogleAuthUseCase(authResponse)
                val userinfo = fetchGoogleUserUseCase()
                _userInfo.postValue(userinfo)

            } catch (e: Exception) {
                // 로그인 실패
                failCallback()
            }
        }
    }

    fun saveSheetsSetting(onCallback: (String) -> Unit) {
        viewModelScope.launch {
            try {
                val settings = _sheetsSettings.value
                checkNotNull(settings)
                saveSheetsSettingsUseCase(settings)
                onCallback("Save success.")
            } catch (e: Exception) {
                onCallback("Save fail.")
            }
        }
    }

    /**
     * bindText's two-way data binding getter setter
     */
    var fileID: String
        get() = _sheetsSettings.value?.fileID.orEmpty()
        set(value) {
            val current = _sheetsSettings.value
            _sheetsSettings.value = current?.copy(fileID = value)
                ?: SheetsSettings(value, "", "", "", "")
        }

    var sheetName: String
        get() = _sheetsSettings.value?.sheetsName.orEmpty()
        set(value) {
            val current = _sheetsSettings.value
            _sheetsSettings.value = current?.copy(sheetsName = value)
                ?: SheetsSettings("", value, "", "", "")
        }

    var tableCell: String
        get() = _sheetsSettings.value?.tableCell.orEmpty()
        set(value) {
            val current = _sheetsSettings.value
            _sheetsSettings.value = current?.copy(tableCell = value)
                ?: SheetsSettings("", "", value, "", "")
        }

    var fieldCount: String
        get() = _sheetsSettings.value?.fieldCount.orEmpty()
        set(value) {
            val current = _sheetsSettings.value
            _sheetsSettings.value = current?.copy(fieldCount = value)
                ?: SheetsSettings("", "", "", value, "")
        }

    var barcodeColumn: String
        get() = _sheetsSettings.value?.barcodeColumn.orEmpty()
        set(value) {
            val current = _sheetsSettings.value
            _sheetsSettings.value = current?.copy(barcodeColumn = value)
                ?: SheetsSettings("", "", "", "", value)
        }
}