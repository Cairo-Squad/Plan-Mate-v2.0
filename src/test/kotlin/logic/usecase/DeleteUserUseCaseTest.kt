package logic.usecase

import com.google.common.truth.Truth.assertThat
import io.mockk.every
import io.mockk.mockk
import logic.repositories.AuthenticationRepository
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
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
    fun `should return true when userid exist`() {
        // Given
        val mockUsers=FakeData.mockUsers

        // When
        val result = deleteUserUseCase.deleteUser(mockUsers[0].id)

        // Then
        assertThat(result).isTrue()
    }

    @Test
    fun `should return false when userid not exist`() {
        // Given
        val notExistUserId = UUID.randomUUID()

        // When
        val result = deleteUserUseCase.deleteUser(notExistUserId)

        // Then
        assertThat(result).isFalse()
    }
}
