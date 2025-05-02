package logic.usecase.project

import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import logic.exception.DtoNotFoundException
import logic.exception.EmptyNameException
import logic.model.State
import logic.repositories.ProjectsRepository
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import java.util.UUID

class EditStateUseCaseTest {

    lateinit var repository: ProjectsRepository
    lateinit var editStateUseCase: EditStateUseCase

    @BeforeEach
    fun setup() {
        repository = mockk(relaxed = true)
        editStateUseCase = EditStateUseCase(repository)
    }

    @Test
    fun `editState should return unit, when title has changed`() {
        // Given
        val updateState = State(UUID(1, 1), "In Progress")
        val originalState = State(UUID(1, 1), "Done")

        // When
        editStateUseCase.editState(updateState, originalState)

        // Then
        verify(exactly = 1) { editStateUseCase.editState(updateState, originalState) }
    }

    @Test
    fun `editState should return DtoNotFoundException, when user is not found`() {
        // Given
        val updatedState = State(id = UUID(2, 2), title = "In Progress")
        val originalState = State(id = UUID(1, 1), title = "Done")
        every { repository.editState(updatedState) } throws DtoNotFoundException()

        // When & Then
        assertThrows<DtoNotFoundException> {
            editStateUseCase.editState(updatedState, originalState)
        }
    }

    @Test
    fun `editState should return EmptyNameException, when user title is empty`() {
        // Given
        val updatedUser = State(id = UUID(1, 1), title = "")
        val originalUser = State(id = UUID(1,1), title = "In Progress")
        // When & Then
        assertThrows<EmptyNameException> {
            editStateUseCase.editState(updatedUser, originalUser)
        }
    }

}