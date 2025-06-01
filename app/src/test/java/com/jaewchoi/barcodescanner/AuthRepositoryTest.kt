package com.jaewchoi.barcodescanner

import com.jaewchoi.barcodescanner.data.repository.AuthRepository
import com.jaewchoi.barcodescanner.utils.AuthManager
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.mockk
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertNotNull
import kotlinx.coroutines.test.runTest
import net.openid.appauth.AuthState
import net.openid.appauth.AuthorizationResponse
import org.junit.Before
import org.junit.Test

class AuthRepositoryTest {
    @MockK
    private lateinit var mockAuthManager: AuthManager

    private lateinit var fakeTokenStorage: FakeTokenStorage

    private lateinit var repository: AuthRepository

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        fakeTokenStorage = FakeTokenStorage()
        repository = AuthRepository(mockAuthManager, fakeTokenStorage)
    }

    @Test
    fun `Token 저장 후 load 결과를 필드 단위로 검증`() = runTest {
        // given
        val fakeAccessToken = "fake-access-token-ABC"
        val mockResponse = mockk<AuthorizationResponse>()
        val mockState = mockk<AuthState>(relaxed = true).apply {
            every { accessToken } returns fakeAccessToken
        }

        coEvery { mockAuthManager.exchangeToken(mockResponse) } returns mockState

        // when
        repository.saveAuthResult(mockResponse)

        // then
        coVerify(exactly = 1) { mockAuthManager.exchangeToken(mockResponse) }

        val loadedState = fakeTokenStorage.load()

        assertNotNull(loadedState)

        assertEquals(
            "저장한 accessToken과 load한 accessToken이 같아야 한다",
            fakeAccessToken,
            loadedState!!.accessToken
        )
    }


    @Test
    fun `performLogout clears auth token`() = runTest {
        // given
        val mockResponse = mockk<AuthorizationResponse>()
        val mockState = mockk<AuthState>(relaxed = true)

        coEvery { mockAuthManager.exchangeToken(mockResponse) } returns mockState

        // when
        repository.saveAuthResult(mockResponse)
        repository.performLogout()

        // then
        val loadedState = fakeTokenStorage.load()
        assertEquals(
            "저장된 authState가 삭제되어야 한다.",
            null,
            loadedState
        )
    }
}