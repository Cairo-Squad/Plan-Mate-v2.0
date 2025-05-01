package logic.usecase

import com.google.common.truth.Truth.assertThat
import data.dto.UserType
import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import logic.model.User
import org.junit.jupiter.api.assertThrows
import logic.repositories.AuthenticationRepository
import org.junit.jupiter.api.Assertions.*

import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import java.lang.IllegalArgumentException
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
    fun `editUser should return true, when input has changed property or more`() {
        // Given
        val updateUser =
            User(id = UUID(1, 1), name = "Mohamed", password = "123456", type = UserType.ADMIN)
        every { repository.editUser(updateUser.id) } returns true

        // When
        editUserUseCase.editUser(updateUser)

        // Then
        verify(exactly = 1) { repository.editUser(updateUser.id) }
    }

    @Test
    fun `editUser should return IllegalStateException, when user id is not found`() {
        // Given
        val updateUser =
            User(id = UUID(1, 1), name = "Mohamed", password = "123456", type = UserType.ADMIN)
        every { repository.editUser(updateUser.id) } throws  IllegalStateException()

        // When & Then
        assertThrows<IllegalStateException> {
            editUserUseCase.editUser(updateUser)
        }
    }

    @Test
    fun `editUser should return IllegalStateException, when user object is not changed`() {
        // Given
        val updateUser =
            User(id = UUID(1, 1), name = "Mohamed", password = "123456", type = UserType.ADMIN)

        // When
        val result = editUserUseCase.editUser(updateUser)

        // Then
        assertThat(result).isFalse()

    }

    @Test
    fun `editUser should return IllegalArgumentException, when user name is empty`() {
        // Given
        val updateUser =
            User(id = UUID(1, 1), name = "", password = "123456", type = UserType.ADMIN)

        // When & Then
        assertThrows<IllegalArgumentException> {
            editUserUseCase.editUser(updateUser)
        }
    }

    @Test
    fun `editUser should return IllegalArgumentException, when user password is empty`() {
        // Given
        val updateUser =
            User(id = UUID(1, 1), name = "Mohamed", password = "", type = UserType.ADMIN)

        // When & Then
        assertThrows<IllegalArgumentException> {
            editUserUseCase.editUser(updateUser)
        }
    }

}