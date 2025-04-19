package com.jaewchoi.barcodescanner.viewmodels

import android.content.Intent
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.map
import androidx.lifecycle.viewModelScope
import com.jaewchoi.barcodescanner.data.source.network.UserInfo
import com.jaewchoi.barcodescanner.domain.usecase.FetchGoogleUserUseCase
import com.jaewchoi.barcodescanner.domain.usecase.HandleGoogleAuthUseCase
import com.jaewchoi.barcodescanner.domain.usecase.PerformLogoutUseCase
import com.jaewchoi.barcodescanner.domain.usecase.RequestGoogleAuthUseCase
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
) : ViewModel() {

    private val _authRequest = Channel<AuthorizationRequest>(Channel.BUFFERED)
    val authRequest = _authRequest.receiveAsFlow()

    private val _userInfo = MutableLiveData<UserInfo>(null)
    val userInfo: LiveData<UserInfo>
        get() = _userInfo

    val isLogin: LiveData<Boolean> = _userInfo.map { it != null }

    fun initSettings() {
        viewModelScope.launch {
            try {
                _userInfo.postValue(fetchGoogleUserUseCase())
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
                _userInfo.postValue(fetchGoogleUserUseCase())

            } catch (e: Exception) {
                // 로그인 실패
                failCallback()
            }
        }
    }
}