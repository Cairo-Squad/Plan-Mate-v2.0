package logic.usecase

import com.google.common.truth.Truth.assertThat
import data.hashing.MD5PasswordEncryptor
import data.hashing.PasswordEncryptor
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import logic.repositories.AuthenticationRepository
import logic.usecase.user.LoginUserUseCase
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

class LoginUserUseCaseTest {
    private lateinit var authenticationRepository: AuthenticationRepository
    private lateinit var loginUserUseCase: LoginUserUseCase
    private val passwordEncryptor: PasswordEncryptor = MD5PasswordEncryptor()

    @BeforeEach
    fun setUp() {
        authenticationRepository = mockk(relaxed = true)
        loginUserUseCase = LoginUserUseCase(authenticationRepository, passwordEncryptor)

    }

    @Test
    fun `should return nothing when valid username and password`() = runTest {
        //Given
        val mockUser = FakeData.mockUsers[0]
        coEvery { authenticationRepository.loginUser(mockUser.name!!, "123456") } returns Unit

        //when
        val result = loginUserUseCase.login(mockUser.name!!, "123456")

        //Then
        assertThat(result).isEqualTo(Unit)
    }

    @Test
    fun `should throw Exception when  invalid userName or password `() = runTest {
        // Given
        val mockUser = FakeData.mockUsers[0]
        coEvery { authenticationRepository.loginUser(any(), any()) } throws Exception()

        // Then
        assertThrows<Exception> {
            loginUserUseCase.login(mockUser.name!!, "12")
        }

    }
}