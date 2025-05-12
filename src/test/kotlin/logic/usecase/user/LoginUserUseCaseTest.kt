package logic.usecase

import com.google.common.truth.Truth.assertThat
import data.hashing.PasswordEncryptor
import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import logic.repositories.AuthenticationRepository
import logic.usecase.user.LoginUserUseCase
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import kotlin.test.assertFalse

class LoginUserUseCaseTest {
    private lateinit var authenticationRepository : AuthenticationRepository
    private lateinit var loginUserUseCase : LoginUserUseCase

    @BeforeEach
    fun setUp() {
        authenticationRepository = mockk(relaxed = true)
        loginUserUseCase = LoginUserUseCase(authenticationRepository)

    }

    @Test
    fun `should return true when valid username and password`() = runTest {
        //Given
        val mockUser = FakeData.mockUsers[0]
        coEvery { authenticationRepository.loginUser(mockUser.name!!, mockUser.password!!) } returns true

        //when
        val result = loginUserUseCase.login(mockUser.name!!, mockUser.password!!)

        //Then
        assertThat(result).isTrue()
    }

    @Test
    fun `should return false when  username valid and password is invalid`() = runTest {
        // Given
        val mockUser = FakeData.mockUsers[0]
        coEvery { authenticationRepository.loginUser(mockUser.name!!, "123456789") } returns false

        // When
        val result = loginUserUseCase.login(mockUser.name!!, "123456789")

        // Then
        assertThat(result).isFalse()

    }

    @Test
    fun `should return false when  username is invalid and password is valid`() = runTest {
        //Given
        val mockUser = FakeData.mockUsers[1]
        coEvery { authenticationRepository.loginUser("ali", mockUser.password!!) } returns false

        //when
        val exception = loginUserUseCase.login("ali", mockUser.password!!)

        //Then
        assertFalse(exception)

    }
}