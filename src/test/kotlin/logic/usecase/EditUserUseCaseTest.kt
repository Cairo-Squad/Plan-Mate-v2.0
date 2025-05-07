package logic.usecase

import data.dto.UserType
import io.mockk.*
import kotlinx.coroutines.test.runTest
import logic.exception.DtoNotFoundException
import logic.exception.EmptyNameException
import logic.exception.EmptyPasswordException
import logic.exception.EntityNotChangedException
import logic.model.User
import logic.repositories.AuthenticationRepository
import logic.usecase.user.EditUserUseCase
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import java.util.UUID

class EditUserUseCaseTest {

    private lateinit var repository: AuthenticationRepository
    private lateinit var editUserUseCase: EditUserUseCase

    @BeforeEach
    fun setUp() {
        repository = mockk(relaxed = true)
        editUserUseCase = EditUserUseCase(repository)

    }

    @Test
    fun `editUser should return true, when input has changed property or more`() = runTest {
        // Given
        val updateUser =
            User(id = UUID(1, 1), name = "Mohamed", password = "123456", type = UserType.ADMIN)
        val originalUser =
            User(id = UUID(1, 1), name = "Ahmed", password = "123456", type = UserType.ADMIN)


        // When
        editUserUseCase.editUser(updateUser, originalUser)

        // Then
        coVerify(exactly = 1) { editUserUseCase.editUser(updateUser, originalUser) }
    }

    @Test
    fun `editUser should return DtoNotFoundException, when user is not found`() = runTest {
        // Given
        val updatedUser =
            User(id = UUID(2, 2), name = "Mohamed", password = "123456", type = UserType.ADMIN)
        val originalUser =
            User(id = UUID(1, 1), name = "Mohamed", password = "123456", type = UserType.ADMIN)
        coEvery { repository.editUser(updatedUser) } throws DtoNotFoundException()

        // When & Then
        assertThrows<DtoNotFoundException> {
            editUserUseCase.editUser(updatedUser, originalUser)
        }
    }

    @Test
    fun `editUser should return EntityNotChangedException, when user is not changed`() = runTest {
        // Given
        val updatedUser =
            User(id = UUID(1, 1), name = "Mohamed", password = "123456", type = UserType.ADMIN)
        val originalUser =
            User(id = UUID(1, 1), name = "Mohamed", password = "123456", type = UserType.ADMIN)

        // When & Then
        assertThrows<EntityNotChangedException> {
            editUserUseCase.editUser(updatedUser, originalUser)
        }
    }

    @Test
    fun `editUser should return EmptyNameException, when user name is empty`() = runTest {
        // Given
        val updatedUser =
            User(id = UUID(1, 1), name = "", password = "123456", type = UserType.ADMIN)
        val originalUser =
            User(id = UUID(1, 1), name = "Mohamed", password = "123456", type = UserType.ADMIN)

        // When & Then
        assertThrows<EmptyNameException> {
            editUserUseCase.editUser(updatedUser, originalUser)
        }
    }

    @Test
    fun `editUser should return EmptyPasswordException, when user password is empty`() = runTest {
        // Given
        val updatedUser =
            User(id = UUID(1, 1), name = "Mohamed", password = "", type = UserType.ADMIN)
        val originalUser =
            User(id = UUID(1, 1), name = "Mohamed", password = "123456", type = UserType.ADMIN)

        // When & Then
        assertThrows<EmptyPasswordException> {
            editUserUseCase.editUser(updatedUser, originalUser)
        }
    }
}