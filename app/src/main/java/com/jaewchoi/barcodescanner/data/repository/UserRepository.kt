package com.jaewchoi.barcodescanner.data.repository

import com.jaewchoi.barcodescanner.data.source.local.TokenStorage
import com.jaewchoi.barcodescanner.data.source.network.TokenAuthenticator
import com.jaewchoi.barcodescanner.data.source.network.UserInfo
import com.jaewchoi.barcodescanner.domain.model.AuthToken
import com.jaewchoi.barcodescanner.data.source.network.UserInfoApi
import javax.inject.Inject

class UserRepository @Inject constructor(
    private val api: UserInfoApi,
    private val tokenAuthenticator: TokenAuthenticator,
) {
    suspend fun fetchGoogleUserInfo(): UserInfo {
        return tokenAuthenticator.withFreshTokenCall { accessToken ->
            api.getUserInfo("Bearer $accessToken")
        }
    }
}