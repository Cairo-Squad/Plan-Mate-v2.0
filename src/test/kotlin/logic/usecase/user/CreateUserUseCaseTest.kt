package logic.usecase

import com.google.common.truth.Truth.assertThat
import data.dto.UserType
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import logic.exception.EmptyNameException
import logic.exception.EmptyPasswordException
import logic.model.User
import logic.repositories.AuthenticationRepository
import logic.usecase.user.CreateUserUseCase
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
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
    fun `should call repository when all inputs are valid`() {
        // Given
        val user = validUser
        every { repository.createUser(user.id, user.name, user.password, user.type) } returns true

        // When
        val isCreated = createUserUseCase.createUser(user.id, user.name, user.password, user.type)

        // Then
        assertThat(isCreated).isTrue()
    }



    @Test
    fun `should success registration when duplication name but different id`() {
        // Given
        val user = validUser
        val userWithDifferentId = validUser.copy(id = UUID.randomUUID())
        every { repository.createUser(user.id, user.name, user.password, user.type) } returns true
        every { repository.createUser(userWithDifferentId.id, user.name, user.password, user.type) } returns true

        // When
        val firstUser = createUserUseCase.createUser(user.id, user.name, user.password, user.type)
        val secondUser = createUserUseCase.createUser(userWithDifferentId.id, user.name, user.password, user.type)

        // Then
        assertThat(firstUser).isTrue()
        assertThat(secondUser).isTrue()
    }
}