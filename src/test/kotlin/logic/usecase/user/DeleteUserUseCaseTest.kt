package logic.usecase.user

import com.google.common.truth.Truth
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import logic.repositories.AuthenticationRepository
import logic.usecase.FakeData
import logic.usecase.FakeData.mockUsers
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import java.util.*

class DeleteUserUseCaseTest {
    private lateinit var authenticationRepository : AuthenticationRepository
    private lateinit var deleteUserUseCase : DeleteUserUseCase

    @BeforeEach
    fun setUp() {
        authenticationRepository = mockk(relaxed = true)
        deleteUserUseCase = DeleteUserUseCase(authenticationRepository)
    }

    @Test
    fun `should return true when userid exist`() = runTest {
        // Given
        val mockUsers= FakeData.mockUsers
        coEvery { authenticationRepository.deleteUser(mockUsers[0].id!!) } returns Unit

        // When
        deleteUserUseCase.deleteUser(mockUsers[0].id!!)

        // Then
        coVerify(exactly = 1) { authenticationRepository.deleteUser(mockUsers[0].id!!) }
    }

    @Test
    fun `should return false when userid not exist`() = runTest {
        // Given
        val notExistUserId = UUID.randomUUID()
        coEvery { authenticationRepository.deleteUser(notExistUserId) } returns Unit

        // When
        val result = deleteUserUseCase.deleteUser(notExistUserId)

        // Then
        coVerify(exactly = 1) { authenticationRepository.deleteUser(notExistUserId) }
    }

    @Test
    fun `should throw exception when authenticationRepository throws error during delete`() = runTest {
        // Given
        val mockUsers = FakeData.mockUsers
        coEvery { authenticationRepository.deleteUser(any()) } throws Exception()

        // When & Then
        assertThrows<Exception> { deleteUserUseCase.deleteUser(mockUsers[0].id!!) }
    }

}