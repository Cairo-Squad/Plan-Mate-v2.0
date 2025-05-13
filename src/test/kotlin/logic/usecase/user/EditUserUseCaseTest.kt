package logic.usecase.user

import logic.model.UserType
import io.mockk.*
import kotlinx.coroutines.test.runTest
import data.customException.PlanMateException
import logic.model.User
import logic.repositories.AuthenticationRepository
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import java.util.UUID

class EditUserUseCaseTest {

    private lateinit var authenticationRepository: AuthenticationRepository
    private lateinit var editUserUseCase: EditUserUseCase

    @BeforeEach
    fun setUp() {
        authenticationRepository = mockk(relaxed = true)
        editUserUseCase = EditUserUseCase(authenticationRepository)

    }

    @Test
    fun `editUser should return true, when input has changed property or more`() = runTest {
        // Given
        val updateUser =
            User(id = UUID(1, 1), name = "Mohamed", password = "123456", type = UserType.ADMIN)

        // When
        editUserUseCase.editUser(updateUser)

        // Then
        coVerify(exactly = 1) { editUserUseCase.editUser(updateUser) }
    }

    @Test
    fun `editUser should return DtoNotFoundException, when user is not found`() = runTest {
        // Given
        val updatedUser =
            User(id = UUID(2, 2), name = "Mohamed", password = "123456", type = UserType.ADMIN)
     
        coEvery { authenticationRepository.editUser(updatedUser) } throws PlanMateException.NetworkException.DataNotFoundException()

        // When & Then
        assertThrows<PlanMateException.NetworkException.DataNotFoundException> {
            editUserUseCase.editUser(updatedUser)
        }
    }

}