package com.jaewchoi.barcodescanner.domain.usecase

import com.jaewchoi.barcodescanner.data.repository.AuthRepository
import net.openid.appauth.AuthorizationResponse
import javax.inject.Inject

class HandleGoogleAuthUseCase @Inject constructor(
    private val authRepository: AuthRepository
) {
    suspend operator fun invoke(authResponse: AuthorizationResponse) {
        authRepository.handleAuthResult(authResponse)
    }
}