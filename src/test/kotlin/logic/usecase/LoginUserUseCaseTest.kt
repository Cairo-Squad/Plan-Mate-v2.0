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
        val mockUsers=FakeData.mockUsers

        //when
        val result = loginUserUseCase.login(mockUsers[0].name,mockUsers[0].password)

        //Then
        assertThat(result).isTrue()
    }

    @Test
    fun `should return false when  username valid and password is invalid`() {
        //Given
        val mockUsers=FakeData.mockUsers

        //when
        val result = loginUserUseCase.login(mockUsers[0].name,mockUsers[0].password)

        //Then
        assertThat(result).isFalse()
    }

    @Test
    fun `should return false when  username is invalid and password is valid`() {
        //Given
        val mockUsers=FakeData.mockUsers

        //when
        val result = loginUserUseCase.login(mockUsers[0].name,mockUsers[0].password)

        //Then
        assertThat(result).isFalse()
    }
}