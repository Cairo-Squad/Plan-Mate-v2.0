package logic.usecase

import io.mockk.every
import io.mockk.mockk
import logic.model.State
import logic.model.Task
import logic.repositories.TasksRepository
import logic.usecase.task.CreateTaskUseCase
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import java.util.*

class CreateTaskUseCaseTest {
    lateinit var createTaskUseCase: CreateTaskUseCase
    lateinit var taskRepository: TasksRepository

    @BeforeEach
    fun setup() {
        taskRepository = mockk(relaxed = true)
        createTaskUseCase = CreateTaskUseCase(taskRepository)
    }

    @Test
    fun `should return success when using a valid task`() {
        //Given
        every { taskRepository.createTask(validTask()) } returns Result.success(Unit)

        // When
        val actualResult = createTaskUseCase.createTask(validTask())

        // Then
        assertTrue(actualResult.isSuccess)
    }

    @Test
    fun `should return failure when using a invalid task`() {
        // Given
        every { taskRepository.createTask(invalidTask()) } returns Result.failure(Exception())

        // When
        val actualResult = createTaskUseCase.createTask(invalidTask())

        // Then
        assertTrue(actualResult.isFailure)
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

    private fun invalidTask(): Task {
        return Task(
            id = UUID.randomUUID(),
            title = "",
            description = "This is an invalid test task",
            state = State(id = UUID.randomUUID(), title = "TODO"),
            projectId = UUID.randomUUID()
        )
    }


}
