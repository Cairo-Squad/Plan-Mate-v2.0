package logic.usecase

import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import logic.exception.EmptyTitleException
import logic.model.State
import logic.model.Task
import logic.repositories.TasksRepository
import logic.usecase.task.CreateTaskUseCase
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import java.util.*

class CreateTaskUseCaseTest {
    private lateinit var createTaskUseCase: CreateTaskUseCase
    private lateinit var taskRepository: TasksRepository

    @BeforeEach
    fun setup() {
        taskRepository = mockk(relaxed = true)
        createTaskUseCase = CreateTaskUseCase(taskRepository)
    }

    @Test
    fun `should create task when passing a valid task`() {
        //Given
        val validTask = validTask()

        // When
        createTaskUseCase.createTask(validTask)

        // Then
        verify { taskRepository.createTask(validTask) }
    }

    @Test
    fun `should throw exception when using a invalid task`() {
        // Given
        every { taskRepository.createTask(invalidTask()) }

        // When && Then
        assertThrows<EmptyTitleException> {
            createTaskUseCase.createTask(invalidTask())
        }
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
