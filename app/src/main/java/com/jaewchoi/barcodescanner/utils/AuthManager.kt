package com.jaewchoi.barcodescanner.utils

import android.net.Uri
import com.jaewchoi.barcodescanner.BuildConfig
import com.jaewchoi.barcodescanner.domain.model.AuthToken
import kotlinx.coroutines.suspendCancellableCoroutine
import net.openid.appauth.AuthorizationRequest
import net.openid.appauth.AuthorizationResponse
import net.openid.appauth.AuthorizationService
import net.openid.appauth.AuthorizationServiceConfiguration
import net.openid.appauth.ResponseTypeValues
import javax.inject.Inject
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException

class AuthManager @Inject constructor(
    private val authService: AuthorizationService
) {
    private val serviceConfig = AuthorizationServiceConfiguration(
        Uri.parse(AUTHORIZATION_ENDPOINT),
        Uri.parse(TOKEN_ENDPOINT)
    )
    private val redirectUri = Uri.parse(REDIRECT_URI)

    private val authRequest = AuthorizationRequest.Builder(
        serviceConfig,
        BuildConfig.OAUTH_CLIENT_ID,
        ResponseTypeValues.CODE,
        redirectUri
    ).setScopes("email", "profile").build()

    fun createAuthRequest() = authRequest

    suspend fun exchangeToken(
        authResponse: AuthorizationResponse
    ): AuthToken = suspendCancellableCoroutine { cont ->
        val tokenRequest = authResponse.createTokenExchangeRequest()

        authService.performTokenRequest(tokenRequest) { tokenResponse, authException ->
            if (tokenResponse != null) {
                val accessToken = checkNotNull(tokenResponse.accessToken) {
                    "Access token is null"
                }
                val refreshToken = tokenResponse.refreshToken
                cont.resume(AuthToken(accessToken, refreshToken))
            } else {
                cont.resumeWithException(
                    authException ?: Exception("Token exchange failed")
                )
            }
        }
    }

    companion object {
        private const val AUTHORIZATION_ENDPOINT = "https://accounts.google.com/o/oauth2/v2/auth"
        private const val TOKEN_ENDPOINT = "https://oauth2.googleapis.com/token"
        private const val REDIRECT_URI = "com.jaewchoi.barcodescanner:/oauth2redirect"
    }
}