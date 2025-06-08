package com.jaewchoi.barcodescanner.domain.usecase

import com.jaewchoi.barcodescanner.data.repository.AuthRepository
import javax.inject.Inject

class PerformLogoutUseCase @Inject constructor(
    private val authRepository: AuthRepository
){
    suspend operator fun invoke() {
        authRepository.performLogout()
    }
}