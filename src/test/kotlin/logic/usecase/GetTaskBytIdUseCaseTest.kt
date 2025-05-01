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

class GetTaskByIdUseCaseTest {
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
        val actualTask = getTaskByIdUseCase.getTaskById(validTask().id)

        // Then
        assertThat(actualTask).isEqualTo(validTask())
    }

    @Test
    fun `should throw exception when task does not exist`() {
        // Given
        val taskId = UUID.randomUUID()
        every { taskRepository.getTaskById(taskId) } throws NoSuchElementException("Task not found")

        // When & Then
        val exception = org.junit.jupiter.api.assertThrows<NoSuchElementException> {
            getTaskByIdUseCase.getTaskById(taskId)
        }

        assertThat(exception.message).isEqualTo("Task not found")
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
