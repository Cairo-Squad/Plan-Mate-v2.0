package logic.usecase

import com.google.common.truth.Truth.assertThat
import data.hashing.MD5PasswordEncryptor
import data.hashing.PasswordEncryptor
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import logic.model.UserType
import logic.repositories.AuthenticationRepository
import logic.usecase.user.SignUpUseCase
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class signUpUseCaseTest {
    private lateinit var repository: AuthenticationRepository
    private lateinit var signUpUseCase: SignUpUseCase
    private val passwordEncryptor: PasswordEncryptor = MD5PasswordEncryptor()

    @BeforeEach
    fun setup() {
        repository = mockk(relaxed = true)
        signUpUseCase = SignUpUseCase(repository, passwordEncryptor = passwordEncryptor)
    }

    @Test
    fun `should call repository when all inputs are valid`() = runTest {
        // Given
        coEvery {
            repository.signUp(
                userName = "ahmed",
                userPassword = "123456",
                userType = UserType.MATE
            )
        } returns Unit

        // When
        val isCreated = signUpUseCase.signUp(
            userName = "ahmed",
            userPassword = "123456",
            userType = UserType.MATE
        )

        // Then
        assertThat(isCreated).isEqualTo(Unit)
    }
}