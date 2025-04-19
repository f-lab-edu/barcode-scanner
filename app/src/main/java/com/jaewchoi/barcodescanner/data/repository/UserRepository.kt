package com.jaewchoi.barcodescanner.data.repository

import com.jaewchoi.barcodescanner.data.source.local.TokenStorage
import com.jaewchoi.barcodescanner.data.source.network.UserInfo
import com.jaewchoi.barcodescanner.domain.model.AuthToken
import com.jaewchoi.barcodescanner.network.UserInfoApi
import javax.inject.Inject

class UserRepository @Inject constructor(
    private val api: UserInfoApi,
    private val tokenStorage: TokenStorage
) {
    private var token: AuthToken? = null

    private suspend fun getToken(): AuthToken {
        return token ?: checkNotNull(tokenStorage.getAuthToken())
    }

    suspend fun fetchGoogleUserInfo(): UserInfo {
        val accessToken = getToken().accessToken
        return api.getUserInfo("Bearer $accessToken")
    }
}