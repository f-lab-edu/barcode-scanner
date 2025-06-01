package com.jaewchoi.barcodescanner

import com.jaewchoi.barcodescanner.data.source.local.TokenStorage
import com.jaewchoi.barcodescanner.domain.model.AuthToken
import net.openid.appauth.AuthState


class FakeTokenStorage : TokenStorage {
    private var storedState: AuthState? = null

    override suspend fun save(authState: AuthState) {
        storedState = authState
    }

    override suspend fun load(): AuthState? = storedState

    override suspend fun clearAuthToken() {
        storedState = null
    }

    override suspend fun saveAuthToken(token: AuthToken) {
        TODO("Not yet implemented")
    }

    override suspend fun getAuthToken(): AuthToken? {
        TODO("Not yet implemented")
    }
}