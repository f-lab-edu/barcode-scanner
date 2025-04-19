package com.jaewchoi.barcodescanner.domain.usecase

import android.content.Intent
import com.jaewchoi.barcodescanner.data.repository.AuthRepository
import net.openid.appauth.AuthorizationRequest
import javax.inject.Inject

class RequestGoogleAuthUseCase @Inject constructor(
    private val authRepository: AuthRepository
) {
    operator fun invoke(): AuthorizationRequest {
        return authRepository.createAuthRequest()
    }
}