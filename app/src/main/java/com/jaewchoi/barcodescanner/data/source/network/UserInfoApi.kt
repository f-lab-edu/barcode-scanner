package com.jaewchoi.barcodescanner.data.source.network

import retrofit2.http.GET
import retrofit2.http.Header

interface UserInfoApi {
    @GET("userinfo")
    suspend fun getUserInfo(
        @Header("Authorization") bearerToken: String?
    ): UserInfo
}