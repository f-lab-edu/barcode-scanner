package com.jaewchoi.barcodescanner.data.repository

import com.jaewchoi.barcodescanner.data.source.local.TokenStorage
import com.jaewchoi.barcodescanner.utils.AuthManager
import net.openid.appauth.AuthorizationRequest
import net.openid.appauth.AuthorizationResponse
import javax.inject.Inject

class AuthRepository @Inject constructor(
    private val authManager: AuthManager,
    private val tokenStorage: TokenStorage,
) {
    fun createAuthRequest(): AuthorizationRequest {
        return authManager.createAuthRequest()
    }

    suspend fun handleAuthResult(authResponse: AuthorizationResponse) {
        val token = authManager.exchangeToken(authResponse)
        tokenStorage.saveAuthToken(token)
    }

    suspend fun performLogout() {
        tokenStorage.clearAuthToken()
    }
}