package com.jaewchoi.barcodescanner.domain.usecase

import com.jaewchoi.barcodescanner.data.repository.UserRepository
import com.jaewchoi.barcodescanner.data.model.network.UserInfo
import javax.inject.Inject

class FetchGoogleUserUseCase @Inject constructor(
    private val userRepository: UserRepository
) {
    suspend operator fun invoke(): UserInfo {
        return userRepository.fetchGoogleUserInfo()
    }
}