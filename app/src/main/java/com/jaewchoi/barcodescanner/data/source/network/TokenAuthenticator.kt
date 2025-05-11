package com.jaewchoi.barcodescanner.data.source.network

import com.jaewchoi.barcodescanner.data.source.local.TokenStorage
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.suspendCancellableCoroutine
import net.openid.appauth.AuthorizationService
import javax.inject.Inject
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException

class TokenAuthenticator @Inject constructor(
    private val authService: AuthorizationService,
    private val tokenStorage: TokenStorage,
) {
    suspend fun <T> withFreshTokenCall(call: suspend (accessToken: String) -> T): T {
        val authState = checkNotNull(tokenStorage.load())
        // performActionWithFreshTokens 는 콜백 기반이므로 suspendCoroutine 로 래핑
        val result: T = suspendCancellableCoroutine { cont ->
            authState.performActionWithFreshTokens(authService) { accessToken, _, ex ->
                if (ex != null) {
                    cont.resumeWithException(ex)  // 리프레시 실패
                } else {
                    // 실제 호출
                    runBlocking {
                        try {
                            val data = call(accessToken.toString())
                            cont.resume(data)
                        } catch (e: Exception) {
                            cont.resumeWithException(e)
                        }
                    }
                }
            }
        }

        tokenStorage.save(authState)
        return result
    }
}
