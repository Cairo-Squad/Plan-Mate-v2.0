package logic.usecase

import com.google.common.truth.Truth.assertThat
import data.dto.UserType
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import logic.model.User
import logic.repositories.AuthenticationRepository
import logic.usecase.user.CreateUserUseCase
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import java.util.*

class CreateUserUseCaseTest {
    private lateinit var repository: AuthenticationRepository
    private lateinit var createUserUseCase: CreateUserUseCase
    private val validUser = User(
        id = UUID.randomUUID(),
        name = "ahmed",
        password = "123456",
        type = UserType.ADMIN
    )

    @BeforeEach
    fun setup() {
        repository = mockk(relaxed = true)
        createUserUseCase = CreateUserUseCase(repository)
    }

    @Test
    fun `should call repository when all inputs are valid`() = runTest {
        // Given
        val user = validUser
        coEvery { repository.createUser(validUser) } returns true

        // When
        val isCreated = createUserUseCase.createUser(validUser)

        // Then
        assertThat(isCreated).isTrue()
    }



    @Test
    fun `should success registration when duplication name but different id`() = runTest {
        // Given
        val user = validUser
        val userWithDifferentId = validUser.copy(id = UUID.randomUUID())
        coEvery { repository.createUser(user) } returns true
        coEvery { repository.createUser(userWithDifferentId) } returns true

        // When
        val firstUser = createUserUseCase.createUser(user)
        val secondUser = createUserUseCase.createUser(userWithDifferentId)

        // Then
        assertThat(firstUser).isTrue()
        assertThat(secondUser).isTrue()
    }
}