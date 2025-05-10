package logic.usecase.task

import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import logic.exception.NotFoundException
import logic.model.State
import logic.model.Task
import logic.repositories.TasksRepository
import logic.usecase.log.AddLogUseCase
import org.junit.jupiter.api.BeforeEach
import java.util.*
import kotlin.test.Test
import kotlin.test.assertFailsWith

class EditTaskUseCaseTest {

    private lateinit var tasksRepository: TasksRepository
    private lateinit var editTaskUseCase: EditTaskUseCase
    lateinit var addLogUseCase: AddLogUseCase

    @BeforeEach
    fun setup() {
        tasksRepository = mockk(relaxed = true)
        addLogUseCase = mockk(relaxed = true)
        editTaskUseCase = EditTaskUseCase(tasksRepository, addLogUseCase)
    }

    @Test
    fun `should throw NotFoundException when repository throw exception`() = runTest {
        //Given
        val oldTask = getValidTask()
        val newTask = oldTask.copy(description = "")

        //When & Then
        assertFailsWith<NotFoundException> {
            editTaskUseCase.editTask(newTask = newTask, oldTask = oldTask)
        }
    }

    @Test
    fun `should call editTask on repository if input is valid and changed`() = runTest {
        //Given
        val oldTask = getValidTask()
        val newTask = oldTask.copy(title = "Updated Title")

        //When
        editTaskUseCase.editTask(newTask = newTask, oldTask = oldTask)

        //Then
        coVerify { tasksRepository.editTask(newTask) }
    }

    private fun getValidTask(): Task {
        return Task(
            id = UUID.randomUUID(),
            title = "Title",
            description = "Description",
            state = State(id = UUID.randomUUID(), title = "To Do"),
            projectId = UUID.randomUUID()
        )
    }
}