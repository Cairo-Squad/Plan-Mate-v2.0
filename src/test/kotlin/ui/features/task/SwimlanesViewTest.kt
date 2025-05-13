package ui.features.task

import io.mockk.*
import kotlinx.coroutines.test.runTest
import logic.model.Project
import logic.model.State
import logic.model.Task
import logic.usecase.project.GetAllProjectsUseCase
import logic.usecase.task.GetAllTasksByProjectIdUseCase
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Disabled
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import ui.utils.InputHandler
import ui.utils.OutputFormatter
import java.util.*

class SwimlanesViewTest {
	private lateinit var inputHandler: InputHandler
	private lateinit var outputFormatter: OutputFormatter
	private lateinit var getAllTasksByProjectIdUseCase: GetAllTasksByProjectIdUseCase
	private lateinit var getAllProjectUseCase: GetAllProjectsUseCase
	private lateinit var swimlanesView: SwimlanesView
	
	@BeforeEach
	fun setup() {
		inputHandler = mockk(relaxed = true)
		outputFormatter = mockk(relaxed = true)
		getAllTasksByProjectIdUseCase = mockk(relaxed = true)
		getAllProjectUseCase = mockk(relaxed = true)
		
		swimlanesView = SwimlanesView(
			inputHandler,
			outputFormatter,
			getAllTasksByProjectIdUseCase,
			getAllProjectUseCase
		)
	}
	
	private fun createProject(id: UUID = UUID.randomUUID(), title: String = "Test Project"): Project {
		return Project(
			id = id,
			title = title,
			description = "Test Description",
			createdBy = UUID.randomUUID(),
			tasks = mutableListOf(),
			state = State(UUID.randomUUID(), "To Do")
		)
	}
	
	private fun createTask(
		id: UUID = UUID.randomUUID(),
		title: String = "Test Task",
		projectId: UUID? = null
	): Task {
		return Task(
			id = id,
			title = title,
			description = "Test Description",
			state = State(UUID.randomUUID(), "TODO"),
			projectId = projectId ?: UUID.randomUUID()
		)
	}
	@Disabled
	@Test
	fun `should display tasks for selected project`() = runTest {
		// Given
		val projects = listOf(createProject())
		val tasks = listOf(
			createTask(projectId = projects[0].id),
			createTask(projectId = projects[0].id, title = "Another Task")
		)
		coEvery { getAllProjectUseCase.getAllProjects() } returns projects
		coEvery { getAllTasksByProjectIdUseCase.getAllTasksByProjectId(projects[0].id!!) } returns tasks
		every { inputHandler.promptForIntChoice("🔹 Choose a project to view tasks:", any()) } returns 1
		
		// When
		swimlanesView.getAllTasksByProject()
		
		// Then
		coVerify { getAllProjectUseCase.getAllProjects() }
		coVerify { getAllTasksByProjectIdUseCase.getAllTasksByProjectId(projects[0].id!!) }
		verify { outputFormatter.printHeader("📌 Tasks in Project: ${projects[0].title}") }
		tasks.forEach { task ->
			verify { outputFormatter.printInfo("✅ ${task.title} | 🏷️ Status: ${task.state?.title}") }
		}
	}
	@Disabled
	@Test
	fun `should handle no projects available`() = runTest {
		// Given
		coEvery { getAllProjectUseCase.getAllProjects() } returns emptyList()
		
		// When
		swimlanesView.getAllTasksByProject()
		
		// Then
		verify { outputFormatter.printError("❌ No projects available!") }
		verify(exactly = 0) { inputHandler.promptForIntChoice(any(), any()) }
	}
	@Disabled
	@Test
	fun `should handle no tasks in selected project`() = runTest {
		// Given
		val projects = listOf(createProject())
		coEvery { getAllProjectUseCase.getAllProjects() } returns projects
		coEvery { getAllTasksByProjectIdUseCase.getAllTasksByProjectId(projects[0].id!!) } returns emptyList()
		every { inputHandler.promptForIntChoice("🔹 Choose a project to view tasks:", any()) } returns 1
		
		// When
		swimlanesView.getAllTasksByProject()
		
		// Then
		coVerify { getAllProjectUseCase.getAllProjects() }
		coVerify { getAllTasksByProjectIdUseCase.getAllTasksByProjectId(projects[0].id!!) }
		verify { outputFormatter.printWarning("⚠️ No tasks found for project '${projects[0].title}'!") }
	}
	@Disabled
	@Test
	fun `should handle project fetching failure`() = runTest {
		// Given
		val errorMessage = "Database connection error"
		coEvery { getAllProjectUseCase.getAllProjects() } throws Exception(errorMessage)
		
		// When & Then
		assertThrows<Exception> {
			swimlanesView.getAllTasksByProject()
		}
	}
	@Disabled
	@Test
	fun `should handle task fetching failure`() = runTest {
		// Given
		val projects = listOf(createProject())
		val errorMessage = "Unable to fetch tasks"
		
		coEvery { getAllProjectUseCase.getAllProjects() } returns projects
		coEvery { getAllTasksByProjectIdUseCase.getAllTasksByProjectId(projects[0].id!!) } throws Exception(errorMessage)
		every { inputHandler.promptForIntChoice("🔹 Choose a project to view tasks:", any()) } returns 1
		
		// When & Then
		assertThrows<Exception> {
			swimlanesView.getAllTasksByProject()
		}
	}
}