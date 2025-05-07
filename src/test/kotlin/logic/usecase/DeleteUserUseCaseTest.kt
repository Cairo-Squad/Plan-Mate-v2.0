package logic.usecase

import com.google.common.truth.Truth.assertThat
import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import logic.repositories.AuthenticationRepository
import logic.usecase.user.DeleteUserUseCase
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
        val mockUsers=FakeData.mockUsers
        coEvery { authenticationRepository.deleteUser(mockUsers[0].id) } returns true

        // When
        val result = deleteUserUseCase.deleteUser(mockUsers[0].id)

        // Then
        assertThat(result).isTrue()
    }

    @Test
    fun `should return false when userid not exist`() = runTest {
        // Given
        val notExistUserId = UUID.randomUUID()
        coEvery { authenticationRepository.deleteUser(notExistUserId) } returns false

        // When
        val result = deleteUserUseCase.deleteUser(notExistUserId)

        // Then
        assertThat(result).isFalse()
    }

    @Test
    fun `should throw exception when authenticationRepository throws error during delete`() = runTest {
        // Given
        val mockUsers = FakeData.mockUsers
        coEvery { authenticationRepository.deleteUser(any()) } throws Exception()

        // When & Then
        assertThrows<Exception> { deleteUserUseCase.deleteUser(mockUsers[0].id) }
    }
}
