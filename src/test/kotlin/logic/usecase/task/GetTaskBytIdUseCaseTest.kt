package logic.usecase.task

import com.google.common.truth.Truth.assertThat
import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import logic.model.State
import logic.model.Task
import logic.repositories.TasksRepository
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import java.util.*
import kotlin.NoSuchElementException

class GetTaskByIdUseCaseTest {
    private lateinit var getTaskByIdUseCase: GetTaskBytIdUseCase
    private lateinit var taskRepository: TasksRepository

    @BeforeEach
    fun setup() {
        taskRepository = mockk(relaxed = true)
        getTaskByIdUseCase = GetTaskBytIdUseCase(taskRepository)
    }

    @Test
    fun `should return task when it exists`() = runTest {
        val expectedTask = validTask()
        // Given
        coEvery { taskRepository.getTaskById(expectedTask.id) } returns expectedTask

        // When
        val actualResult = getTaskByIdUseCase.getTaskById(expectedTask.id)

        // Then
        assertThat(actualResult).isEqualTo(expectedTask)
    }

    @Test
    fun `should throw exception when task does not exist`() = runTest {
        // Given
        val taskId = UUID.randomUUID()
        coEvery { taskRepository.getTaskById(taskId) } throws NoSuchElementException("Task not found")

        // When & Then
        val exception = assertThrows<NoSuchElementException> {
            getTaskByIdUseCase.getTaskById(taskId)
        }
        assertThat(exception.message).isEqualTo("Task not found")
    }

    @Test
    fun `should handle repository failure gracefully`() = runTest {
        // Given
        val taskId = UUID.randomUUID()
        coEvery { taskRepository.getTaskById(taskId) } throws RuntimeException("Unexpected error")

        // When & Then
        val exception = assertThrows<RuntimeException> {
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
