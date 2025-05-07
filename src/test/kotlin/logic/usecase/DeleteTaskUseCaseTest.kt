package logic.usecase

import io.mockk.*
import kotlinx.coroutines.test.runTest
import logic.model.State
import logic.model.Task
import logic.repositories.TasksRepository
import logic.usecase.task.DeleteTaskUseCase
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.assertThrows
import java.util.*
import kotlin.test.Test

class DeleteTaskUseCaseTest {

    private lateinit var tasksRepository: TasksRepository
    private lateinit var deleteTaskUseCase: DeleteTaskUseCase

    @BeforeEach
    fun setup() {
        tasksRepository = mockk(relaxed = true)
        deleteTaskUseCase = DeleteTaskUseCase(tasksRepository)
    }

    @Test
    fun `should throw Exception when the tasks repository throws an exception`() = runTest {
        // Given
        coEvery { tasksRepository.deleteTask(any()) } throws Exception()

        // When & Then
        assertThrows<Exception> {
            deleteTaskUseCase.deleteTask(getTask())
        }
    }

    @Test
    fun `should call deleteTask on the tasks repository`() = runTest {
        // When
        deleteTaskUseCase.deleteTask(getTask())

        // Then
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