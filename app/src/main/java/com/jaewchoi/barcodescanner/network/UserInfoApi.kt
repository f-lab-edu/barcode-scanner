package com.jaewchoi.barcodescanner.network

import com.jaewchoi.barcodescanner.data.source.network.UserInfo
import retrofit2.http.GET
import retrofit2.http.Header

interface UserInfoApi {
    @GET("userinfo")
    suspend fun getUserInfo(
        @Header("Authorization") bearerToken: String
    ): UserInfo
}