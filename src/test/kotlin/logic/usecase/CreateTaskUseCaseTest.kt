package logic.usecase

import data.dto.UserType
import io.mockk.*
import kotlinx.coroutines.test.runTest
import logic.exception.EmptyTitleException
import logic.model.State
import logic.model.Task
import logic.model.User
import logic.repositories.TasksRepository
import logic.usecase.Log.AddLogUseCase
import logic.usecase.task.CreateTaskUseCase
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import java.util.*

class CreateTaskUseCaseTest {
    lateinit var createTaskUseCase: CreateTaskUseCase
    lateinit var taskRepository: TasksRepository
    lateinit var  addLogUseCase: AddLogUseCase

    @BeforeEach
    fun setup() {
        
        taskRepository = mockk(relaxed = true)
        addLogUseCase=mockk(relaxed =true )
        createTaskUseCase = CreateTaskUseCase(taskRepository,addLogUseCase)
    }

    @Test
    fun `should create task when passing a valid task`() = runTest {
        //Given
        val validTask = validTask()
        val user = User(UUID.randomUUID(), "mohamed","123456", UserType.ADMIN)

        // When
        createTaskUseCase.createTask(validTask, user)

        // Then
        coVerify { taskRepository.createTask(validTask) }
    }

    @Test
    fun `should throw exception when using a invalid task`() = runTest {
        // Given
        coEvery { taskRepository.createTask(invalidTask()) }
        val user = User(UUID.randomUUID(), "mohamed","123456", UserType.ADMIN)

        // When & Then
        assertThrows<EmptyTitleException> {
            createTaskUseCase.createTask(invalidTask(), user)
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
