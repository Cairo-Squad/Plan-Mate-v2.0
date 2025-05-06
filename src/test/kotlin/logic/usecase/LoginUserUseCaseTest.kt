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
    private lateinit var passwordEncryptor: PasswordEncryptor
    @BeforeEach
    fun setUp() {
        authenticationRepository = mockk(relaxed = true)
        passwordEncryptor = mockk(relaxed = true)
        loginUserUseCase = LoginUserUseCase(authenticationRepository,passwordEncryptor)

    }
    @Test
    fun `should return true when valid username and password`() {
        //Given
        val mockUsers = FakeData.mockUsers
        every { authenticationRepository.getAllUsers() } returns FakeData.mockUsers
        every { passwordEncryptor.hashPassword(mockUsers[0].password) } returns mockUsers[0].password

        //when
        val result = loginUserUseCase.login(mockUsers[0].name, mockUsers[0].password)

        //Then
        assertThat(result).isEqualTo(FakeData.mockUsers[0])
    }

    @Test
    fun `should return false when  username valid and password is invalid`() {
        //Given
        val mockUsers = FakeData.mockUsers
        every { authenticationRepository.getAllUsers() } returns FakeData.mockUsers
        every { passwordEncryptor.hashPassword("123456789") } returns "123456789"

        //when
        val exception = assertThrows<Exception> {
            loginUserUseCase.login(mockUsers[0].name, "123456789")
        }
        //Then
        assertThat(exception).hasMessageThat().contains("Invalid username or password")
    }

    @Test
    fun `should return false when  username is invalid and password is valid`() {
        //Given
        val mockUsers = FakeData.mockUsers
        every { authenticationRepository.getAllUsers() } returns FakeData.mockUsers
        every { passwordEncryptor.hashPassword(mockUsers[0].password) } returns mockUsers[0].password

        //when
        val exception = assertThrows<Exception> {
            loginUserUseCase.login("ali", mockUsers[0].password)
        }

        //Then
        assertThat(exception).hasMessageThat().contains("Invalid username or password")
    }

    @Test
    fun `should throw exception when repository throws error`() {
        //Given
        every { authenticationRepository.getAllUsers() } throws RuntimeException("during get data error")

        //when&thenn
       assertThrows<Exception> {
            loginUserUseCase.login("nour", "12345")
        }
    }
}