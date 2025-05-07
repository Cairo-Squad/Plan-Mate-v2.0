package logic.usecase

import com.google.common.truth.Truth.assertThat
import data.dto.UserType
import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import logic.model.State
import logic.model.User
import logic.repositories.StatesRepository
import logic.usecase.state.CreateStateUseCase
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import java.util.*

class CreateStateUseCaseTest {

    private lateinit var statesRepository: StatesRepository
    private lateinit var createStateUseCase: CreateStateUseCase

    @BeforeEach
    fun setup() {
        statesRepository = mockk()
        createStateUseCase = CreateStateUseCase(statesRepository)
    }

    @Test
    fun `should return true when admin user creates state`() = runTest {
        // Given
        val state = State(UUID.randomUUID(), "Test State")
        val user = User(UUID.randomUUID(), "admin", "pw", UserType.ADMIN)
        
        coEvery { statesRepository.createState(any()) } returns true

        // When
        val result = createStateUseCase.createState(state)

        // Then
        assertThat(result).isTrue()
    }

    @Test
    fun `should return false when mate user tries to create state`() = runTest {
        // Given
        val state = State(UUID.randomUUID(), "Test State")
        val user = User(UUID.randomUUID(), "mateUser", "pw", UserType.MATE)

        coEvery { statesRepository.createState(any()) } returns false

        // When
        val result = createStateUseCase.createState(state)

        // Then
        assertThat(result).isFalse()
    }

    @Test
    fun `should throw Exception when repository throws exception`() = runTest {
        // Given
        val state = State(UUID.randomUUID(), "Test State")
        val user = User(UUID.randomUUID(), "mateUser", "pw", UserType.MATE)
        coEvery { statesRepository.createState(any()) } throws Exception()

        // When & Then
        assertThrows<Exception> { createStateUseCase.createState(state) }
    }
}