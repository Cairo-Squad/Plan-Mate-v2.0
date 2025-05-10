package logic.usecase.state

import io.mockk.*
import kotlinx.coroutines.test.runTest
import logic.exception.NotFoundException
import logic.exception.EmptyNameException
import logic.model.State
import logic.repositories.StatesRepository
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import java.util.UUID

class EditStateUseCaseTest {

    private lateinit var repository: StatesRepository
    private lateinit var editStateUseCase: EditStateUseCase

    @BeforeEach
    fun setup() {
        repository = mockk(relaxed = true)
        editStateUseCase = EditStateUseCase(repository)
    }

    @Test
    fun `editState should return unit, when title has changed`() = runTest {
        // Given
        val updateState = State(UUID(1, 1), "In Progress")

        // When
        editStateUseCase.editState(updateState)

        // Then
        coVerify(exactly = 1) { editStateUseCase.editState(updateState,) }
    }

    @Test
    fun `editState should return DtoNotFoundException, when state is not found`() = runTest {
        // Given
        val updatedState = State(id = UUID(2, 2), title = "In Progress")
        coEvery { repository.editState(updatedState) } throws NotFoundException()

        // When & Then
        assertThrows<NotFoundException> {
            editStateUseCase.editState(updatedState)
        }
    }
}