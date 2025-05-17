package logic.usecase.task

import io.mockk.*
import kotlinx.coroutines.test.runTest
import logic.model.State
import logic.model.Task
import logic.repositories.TasksRepository
import logic.usecase.log.AddTaskLogUseCase
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.assertThrows
import java.util.UUID
import kotlin.test.Test

class DeleteTaskUseCaseTest {
    private lateinit var tasksRepository: TasksRepository
    private lateinit var addTaskLogUseCase: AddTaskLogUseCase
    private lateinit var deleteTaskUseCase: DeleteTaskUseCase

    @BeforeEach
    fun setup() {
        tasksRepository = mockk(relaxed = true)
        addTaskLogUseCase = mockk()
        deleteTaskUseCase = DeleteTaskUseCase(tasksRepository, addTaskLogUseCase)
    }

    @Test
    fun `should throw Exception when the tasks repository throws an exception`() = runTest {
        // Given
        coEvery { tasksRepository.deleteTask(any()) } throws Exception()

        // When & Then
        coVerify(exactly = 0) { addTaskLogUseCase.addTaskLog(any()) }
        assertThrows<Exception> {
            deleteTaskUseCase.deleteTask(getTask())
        }
    }

    @Test
    fun `should call deleteTask on the tasks repository`() = runTest {
        // When
        coEvery { addTaskLogUseCase.addTaskLog(any()) } returns Unit
        deleteTaskUseCase.deleteTask(getTask())

        // Then
        coVerify(exactly = 1) { addTaskLogUseCase.addTaskLog(any()) }
        coVerify { tasksRepository.deleteTask(any()) }
    }

    private fun getTask(): Task {
        return Task(
            id = UUID.randomUUID(),
            title = "Task 1",
            description = "",
            state = State(id = UUID.randomUUID(), title = "Done"),
            projectId = UUID.randomUUID()
        )
    }
}