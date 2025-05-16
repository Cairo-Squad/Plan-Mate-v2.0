package logic.usecase.task

import com.google.common.truth.Truth.assertThat
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import logic.model.State
import logic.model.Task
import logic.repositories.TasksRepository
import org.junit.jupiter.api.BeforeEach
import java.util.*
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith

class CreateTaskUseCaseTest {
	private lateinit var tasksRepository: TasksRepository
	private lateinit var validationTask: ValidationTask
	private lateinit var createTaskUseCase: CreateTaskUseCase
	
	@BeforeEach
	fun setup() {
		tasksRepository = mockk()
		validationTask = mockk()
		createTaskUseCase = CreateTaskUseCase(tasksRepository, validationTask)
	}
	
	@Test
	fun `should create task successfully when validation passes`() = runTest {
		// Given
		val taskToCreate = createValidTask()
		
		coEvery { validationTask.validateCreateTask(taskToCreate) } returns Unit
		coEvery { tasksRepository.createTask(taskToCreate) } returns taskToCreate.id!!
		
		// When
		createTaskUseCase.createTask(taskToCreate)
		
		// Then
		coVerify(exactly = 1) { validationTask.validateCreateTask(taskToCreate) }
		coVerify(exactly = 1) { tasksRepository.createTask(taskToCreate) }
	}
	
	@Test
	fun `should throw exception when task validation fails`() = runTest {
		// Given
		val taskToCreate = createValidTask()
		val validationException = IllegalArgumentException("Invalid task")
		coEvery { validationTask.validateCreateTask(taskToCreate) } throws validationException
		
		// When & Then
		val exception = assertFailsWith<IllegalArgumentException> {
			createTaskUseCase.createTask(taskToCreate)
		}
		coVerify(exactly = 1) { validationTask.validateCreateTask(taskToCreate) }
		coVerify(exactly = 0) { tasksRepository.createTask(any()) }
		assertEquals("Invalid task", exception.message)
	}
	
	@Test
	fun `should handle task with minimal valid fields`() = runTest {
		// Given
		val minimalTask = createValidTask().copy(description = "")
		coEvery { validationTask.validateCreateTask(minimalTask) } returns Unit
		coEvery { tasksRepository.createTask(minimalTask) } returns minimalTask.id!!
		
		// When
		createTaskUseCase.createTask(minimalTask)
		
		// Then
		coVerify(exactly = 1) { validationTask.validateCreateTask(minimalTask) }
		coVerify(exactly = 1) { tasksRepository.createTask(minimalTask) }
	}
	
	private fun createValidTask(): Task {
		return Task(
			id = UUID.randomUUID(),
			title = "Test Task",
			description = "Test Description",
			state = State(id = UUID.randomUUID(), title = "To Do"),
			projectId = UUID.randomUUID()
		)
	}
}