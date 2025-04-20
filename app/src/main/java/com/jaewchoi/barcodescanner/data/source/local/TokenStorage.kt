package com.jaewchoi.barcodescanner.data.source.local

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.jaewchoi.barcodescanner.domain.model.AuthToken
import kotlinx.coroutines.flow.first
import javax.inject.Inject

val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "auth_prefs")

class TokenStorage @Inject constructor(
    private val context: Context
) {

    private object Keys {
        val ACCESS_TOKEN = stringPreferencesKey("accessToken")
        val REFRESH_TOKEN = stringPreferencesKey("refreshToken")
    }

    companion object {
        private const val AUTH_TOKEN_ALIAS = "auth_token_alias"
    }

    suspend fun saveAuthToken(token: AuthToken) {
        context.dataStore.edit { prefs ->
            prefs[Keys.ACCESS_TOKEN] =
                KeyStoreCryptoHelper.encrypt(AUTH_TOKEN_ALIAS, token.accessToken)
            token.refreshToken?.let {
                prefs[Keys.REFRESH_TOKEN] = KeyStoreCryptoHelper.encrypt(
                    AUTH_TOKEN_ALIAS, it
                )
            }
        }
    }

    suspend fun clearAuthToken() {
        context.dataStore.edit { prefs ->
            prefs.remove(Keys.ACCESS_TOKEN)
            prefs.remove(Keys.REFRESH_TOKEN)
        }
    }

    suspend fun getAuthToken(): AuthToken? {
        val prefs = context.dataStore.data.first()
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