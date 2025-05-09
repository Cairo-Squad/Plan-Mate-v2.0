package logic.usecase

import com.google.common.truth.Truth.assertThat
import data.hashing.PasswordEncryptor
import io.mockk.every
import io.mockk.mockk
import logic.repositories.AuthenticationRepository
import logic.usecase.user.LoginUserUseCase
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

class LoginUserUseCaseTest {
    private lateinit var authenticationRepository : AuthenticationRepository
    private lateinit var loginUserUseCase : LoginUserUseCase

    @BeforeEach
    fun setUp() {
        authenticationRepository = mockk(relaxed = true)
        loginUserUseCase = LoginUserUseCase(authenticationRepository)

    }

    @Test
    fun `should return true when valid username and password`() {
        //Given
        val mockUser = FakeData.mockUsers[0]
        every { authenticationRepository.loginUser(mockUser.name, mockUser.password) } returns mockUser

        //when
        val result = loginUserUseCase.login(mockUser.name, mockUser.password)

        //Then
        assertThat(result).isEqualTo(FakeData.mockUsers[0])
    }

    @Test
    fun `should return false when  username valid and password is invalid`() {
        //Given
        val mockUser = FakeData.mockUsers[0]
        every { authenticationRepository.loginUser(mockUser.name, "123456789") } returns null

        //when
        val exception = assertThrows<Exception> {
            loginUserUseCase.login(mockUser.name, "123456789")
        }
        //Then
        assertThat(exception).hasMessageThat().contains("Invalid username or password")
    }

    @Test
    fun `should return false when  username is invalid and password is valid`() {
        //Given
        val mockUser = FakeData.mockUsers[1]
        every { authenticationRepository.loginUser("ali", mockUser.password) } returns null

        //when
        val exception = assertThrows<Exception> {
            loginUserUseCase.login("ali", mockUser.password)
        }

        //Then
        assertThat(exception).hasMessageThat().contains("Invalid username or password")
    }
}