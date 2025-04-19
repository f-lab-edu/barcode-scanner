package com.jaewchoi.barcodescanner.di

import android.content.Context
import com.jaewchoi.barcodescanner.data.repository.UserRepository
import com.jaewchoi.barcodescanner.data.source.local.TokenStorage
import com.jaewchoi.barcodescanner.domain.usecase.FetchGoogleUserUseCase
import com.jaewchoi.barcodescanner.network.UserInfoApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import net.openid.appauth.AuthorizationService
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {
    @Provides
    fun provideGoogleUserInfoApi(): UserInfoApi {
        return Retrofit.Builder()
            .baseUrl(USER_INFO_URI)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(UserInfoApi::class.java)
    }

    @Provides
    fun provideUserRepository(api: UserInfoApi, tokenStorage: TokenStorage) =
        UserRepository(api, tokenStorage)

    @Provides
    fun provideFetchGoogleUserUseCase(
        repository: UserRepository
    ): FetchGoogleUserUseCase = FetchGoogleUserUseCase(repository)

    private const val USER_INFO_URI = "https://www.googleapis.com/oauth2/v2/"
}