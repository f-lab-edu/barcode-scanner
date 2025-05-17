package com.jaewchoi.barcodescanner.di

import android.content.Context
import com.jaewchoi.barcodescanner.data.repository.AuthRepository
import com.jaewchoi.barcodescanner.data.source.local.TokenStorage
import com.jaewchoi.barcodescanner.data.source.network.TokenAuthenticator
import com.jaewchoi.barcodescanner.utils.AuthManager
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import net.openid.appauth.AuthorizationService

@Module
@InstallIn(SingletonComponent::class)
object AuthModule {
    @Provides
    fun provideAuthService(@ApplicationContext context: Context) =
        AuthorizationService(context)


    @Provides
    fun provideAuthManager(authService: AuthorizationService) =
        AuthManager(authService)

    @Provides
    fun provideTokenStorage(@ApplicationContext context: Context) =
        TokenStorage(context)

    @Provides
    fun provideAuthRepository(authManager: AuthManager, tokenStorage: TokenStorage) =
        AuthRepository(authManager, tokenStorage)

    @Provides
    fun provideTokenAuthenticator(authService: AuthorizationService, tokenStorage: TokenStorage) =
        TokenAuthenticator(authService, tokenStorage)

}
