package logic.usecase.task

import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import data.customException.PlanMateException
import logic.model.State
import logic.model.Task
import logic.repositories.TasksRepository
import logic.usecase.log.AddTaskLogUseCase
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.assertThrows
import java.util.*
import kotlin.test.Test

class EditTaskUseCaseTest {
	private lateinit var tasksRepository: TasksRepository
	private lateinit var addTaskLogUseCase: AddTaskLogUseCase
	private lateinit var editTaskUseCase: EditTaskUseCase

	@BeforeEach
	fun setup() {
		tasksRepository = mockk(relaxed = true)
		addTaskLogUseCase = mockk()
		editTaskUseCase = EditTaskUseCase(tasksRepository, addTaskLogUseCase)
	}

	@Test
	fun `should call editTask on repository when task is updated`() = runTest {
		// Given
		val originalTask = createValidTask()
		val updatedTask = originalTask.copy(title = "Updated Title")
		coEvery { addTaskLogUseCase.addTaskLog(any()) } returns Unit

		// When
		editTaskUseCase.editTask(newTask = updatedTask)

		// Then
		coVerify(exactly = 1) { addTaskLogUseCase.addTaskLog(any()) }
		coVerify(exactly = 1) { tasksRepository.editTask(updatedTask) }
	}

	@Test
	fun `should successfully edit task with minimal changes`() = runTest {
		// Given
		val originalTask = createValidTask()
		val slightlyModifiedTask = originalTask.copy(description = "Slightly modified description")
		coEvery { addTaskLogUseCase.addTaskLog(any()) } returns Unit

		// When
		editTaskUseCase.editTask(newTask = slightlyModifiedTask)

		// Then
		coVerify(exactly = 1) { addTaskLogUseCase.addTaskLog(any()) }
		coVerify(exactly = 1) { tasksRepository.editTask(slightlyModifiedTask) }
	}


	@Test
	fun `should throw NotFoundException when repository fails to edit task`() = runTest {
		// Given
		val taskToEdit = createValidTask()
		coEvery { tasksRepository.editTask(any()) } throws PlanMateException.NetworkException.DataNotFoundException()

		// When & Then
		coVerify(exactly = 0) { addTaskLogUseCase.addTaskLog(any()) }
		assertThrows<PlanMateException.NetworkException.DataNotFoundException> {
			editTaskUseCase.editTask(newTask = taskToEdit)
		}
	}

	@Test
	fun `should handle editing task with empty optional fields`() = runTest {
		// Given
		val originalTask = createValidTask()
		val taskWithEmptyFields = originalTask.copy(
			description = "",
			title = "Updated Empty Fields Task"
		)
		coEvery { addTaskLogUseCase.addTaskLog(any()) } returns Unit

		// When
		editTaskUseCase.editTask(newTask = taskWithEmptyFields)

		// Then
		coVerify(exactly = 1) { addTaskLogUseCase.addTaskLog(any()) }
		coVerify(exactly = 1) { tasksRepository.editTask(taskWithEmptyFields) }
	}


	private fun createValidTask(): Task {
		return Task(
			id = UUID.randomUUID(),
			title = "Original Title",
			description = "Original Description",
			state = State(id = UUID.randomUUID(), title = "To Do"),
			projectId = UUID.randomUUID()
		)
	}
}