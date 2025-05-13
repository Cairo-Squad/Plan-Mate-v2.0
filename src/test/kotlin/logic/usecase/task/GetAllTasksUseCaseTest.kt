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

class GetAllTasksUseCaseTest {
	private lateinit var tasksRepository: TasksRepository
	private lateinit var getAllTasksUseCase: GetAllTasksUseCase
	
	@BeforeEach
	fun setup() {
		tasksRepository = mockk()
		getAllTasksUseCase = GetAllTasksUseCase(tasksRepository)
	}
	
	@Test
	fun `should return all tasks from repository`() = runTest {
		// Given
		val expectedTasks = listOf(
			createTask("Task 1"),
			createTask("Task 2"),
			createTask("Task 3")
		)
		coEvery { tasksRepository.getAllTasks() } returns expectedTasks
		
		// When
		val result = getAllTasksUseCase.getAllTasks()
		
		// Then
		coVerify(exactly = 1) { tasksRepository.getAllTasks() }
		assertThat(result).isEqualTo(expectedTasks)
	}
	
	@Test
	fun `should return empty list when repository returns no tasks`() = runTest {
		// Given
		val emptyList = emptyList<Task>()
		coEvery { tasksRepository.getAllTasks() } returns emptyList
		
		// When
		val result = getAllTasksUseCase.getAllTasks()
		
		// Then
		coVerify(exactly = 1) { tasksRepository.getAllTasks() }
		assertThat(result).isEmpty()
	}
	
	@Test
	fun `should propagate exceptions from repository`() = runTest {
		// Given
		val exception = RuntimeException("Database error")
		coEvery { tasksRepository.getAllTasks() } throws exception
		
		// When & Then
		try {
			getAllTasksUseCase.getAllTasks()
			assert(false) { "Expected exception was not thrown" }
		} catch (e: RuntimeException) {
			assertThat(e).isSameInstanceAs(exception)
		}
		
		coVerify(exactly = 1) { tasksRepository.getAllTasks() }
	}
	
	private fun createTask(title: String): Task {
		return Task(
			id = UUID.randomUUID(),
			title = title,
			description = "Description for $title",
			state = State(id = UUID.randomUUID(), title = "To Do"),
			projectId = UUID.randomUUID()
		)
	}
}