package logic.usecase.user

import data.dto.UserType
import io.mockk.*
import kotlinx.coroutines.test.runTest
import logic.exception.NotFoundException
import logic.model.User
import logic.repositories.AuthenticationRepository
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import java.util.UUID

class GetCurrentUserUseCaseTest {

    private lateinit var authenticationRepository: AuthenticationRepository
    private lateinit var getCurrentUserUseCase: GetCurrentUserUseCase

    @BeforeEach
    fun setUp() {
        authenticationRepository = mockk(relaxed = true)
        getCurrentUserUseCase = GetCurrentUserUseCase(authenticationRepository)
    }

    @Test
    fun ` should getCurrentUser return current user when it exists(successful)`() = runTest {
        // Given
        val user = User(id = UUID(1, 1), name = "nour", password = "123456", type = UserType.ADMIN)
        coEvery { authenticationRepository.getCurrentUser() } returns user

        // When
        val result = getCurrentUserUseCase.getCurrentUser()

        // Then
        assert(result != null)
        coVerify(exactly = 1) { authenticationRepository.getCurrentUser() }
    }

    @Test
    fun `getCurrentUser should throw DtoNotFoundException when user is not found`() = runTest {
        // Given
        coEvery { authenticationRepository.getCurrentUser() } throws NotFoundException()

        // When & Then
        assertThrows<NotFoundException> {
            getCurrentUserUseCase.getCurrentUser()
        }
    }
}
