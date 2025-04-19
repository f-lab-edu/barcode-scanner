package com.jaewchoi.barcodescanner.domain.model

data class AuthToken(
    val accessToken: String,
    val refreshToken: String? = null
)