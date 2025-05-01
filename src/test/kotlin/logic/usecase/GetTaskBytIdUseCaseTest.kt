package logic.usecase

import com.google.common.truth.Truth.assertThat
import io.mockk.every
import io.mockk.mockk
import logic.model.State
import logic.model.Task
import logic.repositories.TasksRepository
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import java.util.*
import kotlin.NoSuchElementException

class GetTaskBytIdUseCaseTest {
    lateinit var getTaskByIdUseCase: GetTaskBytIdUseCase
    lateinit var taskRepository: TasksRepository

    @BeforeEach
    fun setup() {
        taskRepository = mockk(relaxed = true)
        getTaskByIdUseCase = GetTaskBytIdUseCase(taskRepository)
    }

    @Test
    fun `should return task when it exists`() {
        // Given
        every { taskRepository.getTaskById(validTask().id) } returns validTask()

        // When
        val actualResult = getTaskByIdUseCase.getTaskById(validTask().id)

        // Then
        assertThat(actualResult).isEqualTo(validTask())
    }

    @Test
    fun `should throw exception when task does not exist`() {
        // Given
        every { taskRepository.getTaskById(taskId = UUID.randomUUID()) } throws NoSuchElementException("Task not found")

        // When & Then
        val exception = org.junit.jupiter.api.assertThrows<NoSuchElementException> {
            getTaskByIdUseCase.getTaskById(UUID.randomUUID())
        }

        assertThat(exception.message).isEqualTo("Task not found")
    }

    @Test
    fun `should handle repository failure gracefully`() {
        // Given
        val taskId = UUID.randomUUID()
        every { taskRepository.getTaskById(taskId) } throws RuntimeException("Unexpected error")

        // When & Then
        val exception = org.junit.jupiter.api.assertThrows<RuntimeException> {
            getTaskByIdUseCase.getTaskById(taskId)
        }

        assertThat(exception.message).isEqualTo("Unexpected error")
    }

    private fun validTask(): Task {
        return Task(
            id = UUID.randomUUID(),
            title = "Sample Task",
            description = "This is a test task",
            state = State(id = UUID.randomUUID(), title = "TODO"),
            projectId = UUID.randomUUID()
        )
    }
}