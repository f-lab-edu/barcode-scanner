package com.jaewchoi.barcodescanner

import com.jaewchoi.barcodescanner.data.source.local.TokenStorage
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import net.openid.appauth.AuthState


class FakeTokenStorage {
    private val mutex = Mutex()
    private var storedState: AuthState? = null

/*    override suspend fun save(authState: AuthState) {
        mutex.withLock {
            storedState = authState
        }
    }

    override suspend fun load(): AuthState? {
        return mutex.withLock {
            storedState
        }
    }

    override suspend fun clearAuthToken() {
        mutex.withLock {
            storedState = null
        }
    }*/
}