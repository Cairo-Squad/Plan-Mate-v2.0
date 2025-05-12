package logic.usecase.task

import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import logic.exception.NotFoundException
import logic.model.State
import logic.model.Task
import logic.repositories.TasksRepository
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.assertThrows
import java.util.*
import kotlin.test.Test
import kotlin.test.assertFailsWith
import kotlin.test.assertEquals

class EditTaskUseCaseTest {
	private lateinit var tasksRepository: TasksRepository
	private lateinit var editTaskUseCase: EditTaskUseCase
	
	@BeforeEach
	fun setup() {
		tasksRepository = mockk(relaxed = true)
		editTaskUseCase = EditTaskUseCase(tasksRepository)
	}
	
	@Test
	fun `should call editTask on repository when task is updated`() = runTest {
		// Given
		val originalTask = createValidTask()
		val updatedTask = originalTask.copy(title = "Updated Title")
		
		// When
		editTaskUseCase.editTask(newTask = updatedTask)
		
		// Then
		coVerify(exactly = 1) { tasksRepository.editTask(updatedTask) }
	}
	
	@Test
	fun `should successfully edit task with minimal changes`() = runTest {
		// Given
		val originalTask = createValidTask()
		val slightlyModifiedTask = originalTask.copy(description = "Slightly modified description")
		
		// When
		editTaskUseCase.editTask(newTask = slightlyModifiedTask)
		
		// Then
		coVerify(exactly = 1) { tasksRepository.editTask(slightlyModifiedTask) }
	}
	
	
	@Test
	fun `should throw NotFoundException when repository fails to edit task`() = runTest {
		// Given
		val taskToEdit = createValidTask()
		coEvery { tasksRepository.editTask(any()) } throws NotFoundException()
		
		// When & Then
		assertThrows<NotFoundException> {
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
		
		// When
		editTaskUseCase.editTask(newTask = taskWithEmptyFields)
		
		// Then
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