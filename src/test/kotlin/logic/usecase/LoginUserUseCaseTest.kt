import com.google.common.truth.Truth.assertThat
import io.mockk.every
import io.mockk.mockk
import logic.repositories.AuthenticationRepository
import logic.usecase.FakeData
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
        val mockUsers = FakeData.mockUsers
        every { authenticationRepository.getAllUsers() } returns FakeData.mockUsers

        //when
        val result = loginUserUseCase.login(mockUsers[0].name, mockUsers[0].password)

        //Then
        assertThat(result).isTrue()
    }

    @Test
    fun `should return false when  username valid and password is invalid`() {
        //Given
        val mockUsers = FakeData.mockUsers
        every { authenticationRepository.getAllUsers() } returns FakeData.mockUsers

        //when
        val result = loginUserUseCase.login(mockUsers[0].name, "123456789")

        //Then
        assertThat(result).isFalse()
    }

    @Test
    fun `should return false when  username is invalid and password is valid`() {
        //Given
        val mockUsers = FakeData.mockUsers
        every { authenticationRepository.getAllUsers() } returns FakeData.mockUsers

        //when
        val result = loginUserUseCase.login("ali", mockUsers[0].password)

        //Then
        assertThat(result).isFalse()
    }

    @Test
    fun `should throw exception when repository throws error`() {
        //Given
        every { authenticationRepository.getAllUsers() } throws RuntimeException("during get data error")

        //when
        val exception = assertThrows<Exception> {
            loginUserUseCase.login("nour", "12345")
        }

        //Then
        assertThat(exception).hasMessageThat().isEqualTo("error  during login")
    }
}