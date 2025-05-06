package logic.usecase

import io.mockk.*
import logic.model.State
import logic.model.Task
import logic.repositories.TasksRepository
import logic.usecase.Log.AddLogUseCase
import logic.usecase.task.EditTaskUseCase
import org.junit.jupiter.api.BeforeEach
import java.util.*
import kotlin.test.Test
import kotlin.test.assertFailsWith


class EditTaskUseCaseTest {

    private lateinit var tasksRepository: TasksRepository
    private lateinit var editTaskUseCase: EditTaskUseCase
    lateinit var  addLogUseCase: AddLogUseCase

    @BeforeEach
    fun setup() {
        tasksRepository = mockk(relaxed = true)
        addLogUseCase=mockk(relaxed =true )
        editTaskUseCase = EditTaskUseCase(tasksRepository,addLogUseCase)
    }

    @Test
    fun `should throw exception when new task is the same as old task`() {
        //Given
        val task = getValidTask()

        //When & Then
        assertFailsWith<IllegalStateException> {
            editTaskUseCase.editTask(newTask = task, oldTask = task)
        }
    }

    @Test
    fun `should throw exception when title is blank`() {
        //Given
        val oldTask = getValidTask()
        val newTask = oldTask.copy(title = "  ")

        //When & Then
        assertFailsWith<IllegalArgumentException> {
            editTaskUseCase.editTask(newTask = newTask, oldTask = oldTask)
        }
    }

    @Test
    fun `should throw exception when description is blank`() {
        //Given
        val oldTask = getValidTask()
        val newTask = oldTask.copy(description = "")

        //When & Then
        assertFailsWith<IllegalArgumentException> {
            editTaskUseCase.editTask(newTask = newTask, oldTask = oldTask)
        }
    }

    @Test
    fun `should call editTask on repository if input is valid and changed`() {
        //Given
        val oldTask = getValidTask()
        val newTask = oldTask.copy(title = "Updated Title")

        //When
        editTaskUseCase.editTask(newTask = newTask, oldTask = oldTask)

        //Then
        verify { tasksRepository.editTask(newTask) }
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