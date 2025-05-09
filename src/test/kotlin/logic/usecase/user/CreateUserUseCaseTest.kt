package logic.usecase.user

import com.google.common.truth.Truth
import data.dto.UserType
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import logic.exception.EmptyNameException
import logic.exception.EmptyPasswordException
import logic.model.User
import logic.repositories.AuthenticationRepository
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import java.util.UUID

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
    fun `should call repository when all inputs are valid`() {
        // Given
        val user = validUser

        // When
        createUserUseCase.createUser(user.id, user.name, user.password, user.type)

        // Then
        verify { repository.createUser(user.id, user.name, user.password, user.type) }
    }

    @Test
    fun `should throw EmptyNameException when name is empty`() {
        // Given
        val user = validUser.copy(name = "")
        every { repository.createUser(user.id, user.name, user.password, user.type) } throws EmptyNameException()

        // When & Then
        assertThrows<EmptyNameException> {
            createUserUseCase.createUser(user.id, user.name, user.password, user.type)
        }
    }

    @Test
    fun `should throw EmptyPasswordException when password is empty`() {
        // Given
        val user = validUser.copy(password = "")
        every { repository.createUser(user.id, user.name, user.password, user.type) } throws EmptyPasswordException()

        // When & Then
        assertThrows<EmptyPasswordException> {
            createUserUseCase.createUser(user.id, user.name, user.password, user.type)
        }
    }

    @Test
    fun `should success registration when duplication name but different id`() {
        // Given
        val user = validUser
        val userWithDifferentId = validUser.copy(id = UUID.randomUUID())

        // When
        val firstUser = createUserUseCase.createUser(user.id, user.name, user.password, user.type)
        val secondUser = createUserUseCase.createUser(userWithDifferentId.id, user.name, user.password, user.type)

        // Then
        Truth.assertThat(firstUser).isNotEqualTo(secondUser)
    }
}