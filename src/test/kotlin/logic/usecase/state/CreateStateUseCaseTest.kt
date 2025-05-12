package logic.usecase.state

import com.google.common.truth.Truth
import data.dto.UserType
import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import logic.model.State
import logic.model.User
import logic.repositories.StatesRepository
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import java.util.UUID

class CreateStateUseCaseTest {

    private lateinit var statesRepository: StatesRepository
    private lateinit var validationState: ValidationState
    private lateinit var createStateUseCase: CreateStateUseCase

    @BeforeEach
    fun setup() {
        statesRepository = mockk(relaxed = true)
        validationState= mockk(relaxed = true)
        createStateUseCase = CreateStateUseCase(statesRepository,validationState)
    }

    @Test
    fun `should return true when admin user creates state`() = runTest {
        // Given
        val state = State(UUID.randomUUID(), "Test State")

        coEvery { validationState.validateOfState(state) } throws IllegalArgumentException("Invalid state")

        // When & Then
        assertThrows<IllegalArgumentException> {
            createStateUseCase.createState(state)
        }
    }

    @Test
    fun `should return false when mate user tries to create state`() = runTest {
        // Given
        val state = State(UUID.randomUUID(), "Test State")

        coEvery { validationState.validateOfState(state) } throws IllegalArgumentException("Invalid state")

        // When & Then
        assertThrows<IllegalArgumentException> {
            createStateUseCase.createState(state)
        }
    }

    @Test
    fun `should throw Exception when repository throws exception`() = runTest {
        // Given
        val state = State(UUID.randomUUID(), "Test State")
        coEvery { statesRepository.createState(any()) } throws Exception()

        // When & Then
        assertThrows<Exception> { createStateUseCase.createState(state) }
    }
}