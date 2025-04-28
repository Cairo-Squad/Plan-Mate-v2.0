import com.google.common.truth.Truth.assertThat
import io.mockk.every
import io.mockk.mockk
import logic.repositories.AuthenticationRepository
import logic.usecase.FakeData
import logic.usecase.LoginUserUseCase
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

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
        every { authenticationRepository.getUser() } returns FakeData.mockUsers

        //when
        val result = loginUserUseCase.login("nour", "123456")

        //Then
        assertThat(result).isTrue()
    }

    @Test
    fun `should return false when  username valid and password is invalid`() {
        //Given
        every { authenticationRepository.getUser() } returns FakeData.mockUsers

        //when
        val result = loginUserUseCase.login("nour", "1234564")

        //Then
        assertThat(result).isFalse()
    }

    @Test
    fun `should return false when  username is invalid and password is valid`() {
        //Given
        every { authenticationRepository.getUser() } returns FakeData.mockUsers

        //when
        val result = loginUserUseCase.login("ali", "123456")

        //Then
        assertThat(result).isFalse()
    }
}