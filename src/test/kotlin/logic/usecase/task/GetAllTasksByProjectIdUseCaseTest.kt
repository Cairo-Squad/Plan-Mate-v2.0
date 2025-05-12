package logic.usecase.task

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
import kotlin.test.assertTrue

class GetAllTasksByProjectIdUseCaseTest {
	private lateinit var tasksRepository: TasksRepository
	private lateinit var getAllTasksByProjectIdUseCase: GetAllTasksByProjectIdUseCase
	
	@BeforeEach
	fun setup() {
		tasksRepository = mockk()
		getAllTasksByProjectIdUseCase = GetAllTasksByProjectIdUseCase(tasksRepository)
	}
	
	@Test
	fun `should return all tasks for a given project id`() = runTest {
		// Given
		val projectId = UUID.randomUUID()
		val expectedTasks = listOf(
			createValidTask(projectId),
			createValidTask(projectId)
		)
		coEvery { tasksRepository.getAllTasksByProjectId(projectId) } returns expectedTasks
		
		// When
		val result = getAllTasksByProjectIdUseCase.getAllTasksByProjectId(projectId)
		
		// Then
		coVerify(exactly = 1) { tasksRepository.getAllTasksByProjectId(projectId) }
		assertEquals(expectedTasks, result)
		assertEquals(2, result.size)
	}
	
	@Test
	fun `should return empty list when no tasks exist for project`() = runTest {
		// Given
		val projectId = UUID.randomUUID()
		coEvery { tasksRepository.getAllTasksByProjectId(projectId) } returns emptyList()
		
		// When
		val result = getAllTasksByProjectIdUseCase.getAllTasksByProjectId(projectId)
		
		// Then
		coVerify(exactly = 1) { tasksRepository.getAllTasksByProjectId(projectId) }
		assertTrue(result.isEmpty())
	}
	
	@Test
	fun `should handle large number of tasks for a project`() = runTest {
		// Given
		val projectId = UUID.randomUUID()
		val largeNumberOfTasks = List(100) { createValidTask(projectId) }
		coEvery { tasksRepository.getAllTasksByProjectId(projectId) } returns largeNumberOfTasks
		
		// When
		val result = getAllTasksByProjectIdUseCase.getAllTasksByProjectId(projectId)
		
		// Then
		coVerify(exactly = 1) { tasksRepository.getAllTasksByProjectId(projectId) }
		assertEquals(largeNumberOfTasks.size, result.size)
	}
	
	
	private fun createValidTask(projectId: UUID = UUID.randomUUID()): Task {
		return Task(
			id = UUID.randomUUID(),
			title = "Test Task",
			description = "Test Description",
			state = State(id = UUID.randomUUID(), title = "To Do"),
			projectId = projectId
		)
	}
}