package logic.usecase

import com.google.common.truth.Truth.assertThat
import logic.model.UserType
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import logic.model.User
import logic.repositories.AuthenticationRepository
import logic.usecase.user.SignUpUseCase
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import java.util.*

class signUpUseCaseTest {
    private lateinit var repository: AuthenticationRepository
    private lateinit var signUpUseCase: SignUpUseCase
    private val validUser = User(
        id = UUID.randomUUID(),
        name = "ahmed",
        password = "123456",
        type = UserType.ADMIN
    )

    @BeforeEach
    fun setup() {
        repository = mockk(relaxed = true)
        signUpUseCase = SignUpUseCase(repository)
    }

    @Test
    fun `should call repository when all inputs are valid`() = runTest {
        // Given
        val user = validUser
        coEvery { repository.signUp(validUser) } returns true

        // When
        val isCreated = signUpUseCase.signUp(validUser)

        // Then
        assertThat(isCreated).isTrue()
    }



    @Test
    fun `should success registration when duplication name but different id`() = runTest {
        // Given
        val user = validUser
        val userWithDifferentId = validUser.copy(id = UUID.randomUUID())
        coEvery { repository.signUp(user) } returns true
        coEvery { repository.signUp(userWithDifferentId) } returns true

        // When
        val firstUser = signUpUseCase.signUp(user)
        val secondUser = signUpUseCase.signUp(userWithDifferentId)

        // Then
        assertThat(firstUser).isTrue()
        assertThat(secondUser).isTrue()
    }
}