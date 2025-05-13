package logic.usecase.user

import logic.model.UserType
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import logic.model.User
import logic.repositories.AuthenticationRepository
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import java.util.UUID
import kotlin.test.assertEquals

class GetAllUsersUseCaseTest {

    private lateinit var authenticationRepository : AuthenticationRepository
    private lateinit var getAllUsersUseCase : GetAllUsersUseCase

    @BeforeEach
    fun setUp() {
        authenticationRepository = mockk()
        getAllUsersUseCase = GetAllUsersUseCase(authenticationRepository)
    }

    @Test
    fun `should return all users from the repository`() = runTest {
        // Given
        val expectedUsers = listOf(
            User(id = UUID.randomUUID(), name = "nour", type = UserType.MATE),
            User(id = UUID.randomUUID(), name = "ali", type = UserType.MATE)
        )
        coEvery { authenticationRepository.getAllUsers() } returns expectedUsers

        // When
        val actualUsers = getAllUsersUseCase.getAllUsers()

        // Then
        assertEquals(expectedUsers, actualUsers)
        coVerify(exactly = 1) { authenticationRepository.getAllUsers() }
    }
}
