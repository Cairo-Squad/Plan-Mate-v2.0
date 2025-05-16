package logic.usecase

import com.google.common.truth.Truth.assertThat
import data.hashing.MD5PasswordEncryptor
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
    private val passwordEncryptor: PasswordEncryptor = MD5PasswordEncryptor()

    @BeforeEach
    fun setUp() {
        authenticationRepository = mockk(relaxed = true)
        loginUserUseCase = LoginUserUseCase(authenticationRepository,passwordEncryptor)

    }

    @Test
    fun `should return true when valid username and password`() = runTest {
        //Given
        val mockUser = FakeData.mockUsers[0]
        coEvery { authenticationRepository.loginUser(mockUser.name!!, "123456") } returns true

        //when
        val result = loginUserUseCase.login(mockUser.name!!, "123456")

        //Then
        assertThat(result).isTrue()
    }

    @Test
    fun `should return false when  username valid and password is invalid`() = runTest {
        // Given
        val mockUser = FakeData.mockUsers[0]
        coEvery { authenticationRepository.loginUser(mockUser.name!!, "123456789") } returns Unit

        // When
        val result = loginUserUseCase.login(mockUser.name!!, "123456789")

        // Then
        assertThat(result).isFalse()

    }

    @Test
    fun `should return false when  username is invalid and password is valid`() = runTest {
        //Given
        val mockUser = FakeData.mockUsers[1]
        coEvery { authenticationRepository.loginUser("ali", "123456") } returns false

        //when
        val exception = loginUserUseCase.login("ali", "123456")

        //Then
        assertFalse(exception)

    }
}