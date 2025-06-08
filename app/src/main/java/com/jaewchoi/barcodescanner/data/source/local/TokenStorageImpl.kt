package com.jaewchoi.barcodescanner.data.source.local

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.jaewchoi.barcodescanner.domain.model.AuthToken
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.first
import net.openid.appauth.AuthState
import javax.inject.Inject

private val Context.tokenDataStore: DataStore<Preferences> by preferencesDataStore(name = "auth_prefs")

class TokenStorageImpl @Inject constructor(
    @ApplicationContext private val context: Context
) : TokenStorage {

    private object Keys {
        val ACCESS_TOKEN = stringPreferencesKey("accessToken")
        val REFRESH_TOKEN = stringPreferencesKey("refreshToken")
        val KEY_STATE = stringPreferencesKey("keyState")
    }

    companion object {
        private const val AUTH_TOKEN_ALIAS = "auth_token_alias"
    }

    override suspend fun save(authState: AuthState) {
        val stateJson = authState.jsonSerialize().toString()
        context.tokenDataStore.edit { prefs ->
            prefs[Keys.KEY_STATE] =
                KeyStoreCryptoHelper.encrypt(AUTH_TOKEN_ALIAS, stateJson)
        }
    }

    override suspend fun load(): AuthState? {
        val prefs = context.tokenDataStore.data.first()
        val encAuthState = prefs[Keys.KEY_STATE] ?: return null
        val authStateJson = KeyStoreCryptoHelper.decrypt(AUTH_TOKEN_ALIAS, encAuthState)
        return AuthState.jsonDeserialize(authStateJson)
    }

    override suspend fun saveAuthToken(token: AuthToken) {
        context.tokenDataStore.edit { prefs ->
            prefs[Keys.ACCESS_TOKEN] =
                KeyStoreCryptoHelper.encrypt(AUTH_TOKEN_ALIAS, token.accessToken)
            token.refreshToken?.let {
                prefs[Keys.REFRESH_TOKEN] = KeyStoreCryptoHelper.encrypt(
                    AUTH_TOKEN_ALIAS, it
                )
            }
        }
    }

    override suspend fun clearAuthToken() {
        context.tokenDataStore.edit { prefs ->
            prefs.remove(Keys.ACCESS_TOKEN)
            prefs.remove(Keys.REFRESH_TOKEN)
        }
    }

    override suspend fun getAuthToken(): AuthToken? {
        val prefs = context.tokenDataStore.data.first()
        val encAccess = prefs[Keys.ACCESS_TOKEN] ?: return null

        val access = KeyStoreCryptoHelper.decrypt(AUTH_TOKEN_ALIAS, encAccess)
        val refresh =
            prefs[Keys.REFRESH_TOKEN]?.let { KeyStoreCryptoHelper.decrypt(AUTH_TOKEN_ALIAS, it) }

        return AuthToken(
            accessToken = access,
            refreshToken = refresh
        )
    }
}