package com.jaewchoi.barcodescanner

import com.jaewchoi.barcodescanner.data.repository.AuthRepository
import com.jaewchoi.barcodescanner.data.source.local.TokenStorage
import com.jaewchoi.barcodescanner.utils.AuthManager
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.MockK
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import net.openid.appauth.AuthState
import net.openid.appauth.AuthorizationResponse
import org.junit.Before
import org.junit.Test

class AuthRepositoryTest {
    @MockK
    lateinit var mockAuthManager: AuthManager

    @MockK(relaxed = true)
    lateinit var mockTokenStorage: TokenStorage

    private lateinit var repository: AuthRepository

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        repository = AuthRepository(mockAuthManager, mockTokenStorage)
    }

    @Test
    fun `Token 저장 후 load 결과 테스트`() = runTest {
        // given
        val fakeResponse = mockk<AuthorizationResponse>()
        val fakeAuthState = mockk<AuthState>()
        coEvery { mockAuthManager.exchangeToken(fakeResponse) } returns fakeAuthState
        coEvery { mockTokenStorage.save(fakeAuthState) } returns Unit

        // when
        repository.saveAuthResult(fakeResponse)

        // then
        coVerify { mockAuthManager.exchangeToken(fakeResponse) }
        coVerify { mockTokenStorage.save(fakeAuthState) }
    }

    @Test
    fun `performLogout clears auth token`() = runTest {
        // when
        repository.performLogout()

        // then
        coVerify { mockTokenStorage.clearAuthToken() }
    }
}