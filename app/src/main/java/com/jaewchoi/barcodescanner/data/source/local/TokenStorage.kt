package com.jaewchoi.barcodescanner.data.source.local

import com.jaewchoi.barcodescanner.domain.model.AuthToken
import net.openid.appauth.AuthState


/**
 * 인증 토큰을 저장·로딩·삭제 하는 기능을 정의한 interface.
 */
interface TokenStorage {
    /**
     * AuthState 전체(JSON)를 암호화해서 저장
     */
    suspend fun save(authState: AuthState)

    /**
     * 저장된 AuthState(JSON)를 복호화해서 반환.
     * 저장된 게 없으면 null
     */
    suspend fun load(): AuthState?

    /**
     * 액세스/리프레시 토큰만 별도로 저장
     */
    suspend fun saveAuthToken(token: AuthToken)

    /**
     * 저장된 액세스/리프레시 토큰을 복호화해서 반환.
     * 저장된 게 없으면 null
     */
    suspend fun getAuthToken(): AuthToken?

    /**
     * 액세스/리프레시 토큰만 삭제
     */
    suspend fun clearAuthToken()
}