package ui.features.task

import io.mockk.*
import kotlinx.coroutines.test.runTest
import logic.model.Project
import logic.model.State
import logic.model.Task
import logic.usecase.project.EditProjectUseCase
import logic.usecase.project.GetAllProjectsUseCase
import logic.usecase.task.DeleteTaskUseCase
import logic.usecase.task.GetAllTasksByProjectIdUseCase
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Disabled
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import ui.utils.InputHandler
import ui.utils.OutputFormatter
import java.util.*

class DeleteTaskViewTest {
	
	private lateinit var getAllTasksByProjectIdUseCase: GetAllTasksByProjectIdUseCase
	private lateinit var deleteTaskUseCase: DeleteTaskUseCase
	private lateinit var inputHandler: InputHandler
	private lateinit var outputFormatter: OutputFormatter
	private lateinit var getAllProjectsUseCase: GetAllProjectsUseCase
	private lateinit var editProjectUseCase: EditProjectUseCase
	private lateinit var deleteTaskView: DeleteTaskView
	
	@BeforeEach
	fun setup() {
		getAllTasksByProjectIdUseCase = mockk(relaxed = true)
		deleteTaskUseCase = mockk(relaxed = true)
		inputHandler = mockk(relaxed = true)
		outputFormatter = mockk(relaxed = true)
		getAllProjectsUseCase = mockk(relaxed = true)
		editProjectUseCase = mockk(relaxed = true)
		
		deleteTaskView = DeleteTaskView(
			getAllTasksByProjectIdUseCase,
			deleteTaskUseCase,
			inputHandler,
			outputFormatter,
			getAllProjectsUseCase,
			editProjectUseCase
		)
	}
	
	private fun createProject(id: UUID = UUID.randomUUID(), title: String = "Test Project"): Project {
		return Project(
			id = id,
			title = title,
			description = "Test Description",
			createdBy = UUID.randomUUID(),
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
	fun `should handle task deletion successfully`() = runTest {
		// Given
		val project = createProject()
		val task = createTask(projectId = project.id)
		
		coEvery { getAllProjectsUseCase.getAllProjects() } returns listOf(project)
		
		coEvery { getAllTasksByProjectIdUseCase.getAllTasksByProjectId(project.id!!) } returns listOf(task)
		
		every { inputHandler.promptForIntChoice("🔹 Choose a project to delete its task:", any()) } returns 1
		every { inputHandler.promptForIntChoice("🔹 Select a task to delete:", any()) } returns 1
		every { inputHandler.promptForInput("Type 'YES' to confirm: ") } returns "YES"
		
		coEvery { deleteTaskUseCase.deleteTask(task) } returns Unit
		coEvery { editProjectUseCase.editProject(any()) } just Runs
		
		// When
		deleteTaskView.deleteTask()
		
		// Then
		coVerify { getAllProjectsUseCase.getAllProjects() }
		coVerify { getAllTasksByProjectIdUseCase.getAllTasksByProjectId(project.id!!) }
		coVerify { deleteTaskUseCase.deleteTask(task) }
		coVerify { editProjectUseCase.editProject(any()) }
		verify { outputFormatter.printSuccess("✅ Task '${task.title}' deleted successfully!") }
	}
	@Disabled
	@Test
	fun `should handle task deletion cancellation`() = runTest {
		// Given
		val project = createProject()
		val task = createTask(projectId = project.id)
		
		coEvery { getAllProjectsUseCase.getAllProjects() } returns listOf(project)
		
		coEvery { getAllTasksByProjectIdUseCase.getAllTasksByProjectId(project.id!!) } returns listOf(task)
		
		every { inputHandler.promptForIntChoice("🔹 Choose a project to delete its task:", any()) } returns 1
		every { inputHandler.promptForIntChoice("🔹 Select a task to delete:", any()) } returns 1
		every { inputHandler.promptForInput("Type 'YES' to confirm: ") } returns "NO"
		
		// When
		deleteTaskView.deleteTask()
		
		// Then
		coVerify { getAllProjectsUseCase.getAllProjects() }
		coVerify { getAllTasksByProjectIdUseCase.getAllTasksByProjectId(project.id!!) }
		verify { outputFormatter.printInfo("🔄 Action canceled. No task was deleted.") }
		coVerify(exactly = 0) { deleteTaskUseCase.deleteTask(any()) }
		coVerify(exactly = 0) { editProjectUseCase.editProject(any()) }
	}
	@Disabled
	@Test
	fun `should handle no projects available`() = runTest {
		// Given
		coEvery { getAllProjectsUseCase.getAllProjects() } returns emptyList()
		
		// When
		deleteTaskView.deleteTask()
		
		// Then
		verify { outputFormatter.printError("❌ No projects available for task deletion!") }
		verify(exactly = 0) { inputHandler.promptForIntChoice(any(), any()) }
	}
	@Disabled
	@Test
	fun `should handle no tasks in selected project`() = runTest {
		// Given
		val project = createProject()
		
		coEvery { getAllProjectsUseCase.getAllProjects() } returns listOf(project)
		
		coEvery { getAllTasksByProjectIdUseCase.getAllTasksByProjectId(project.id!!) } returns emptyList()
		
		every { inputHandler.promptForIntChoice("🔹 Choose a project to delete its task:", any()) } returns 1
		
		// When
		deleteTaskView.deleteTask()
		
		// Then
		coVerify { getAllProjectsUseCase.getAllProjects() }
		coVerify { getAllTasksByProjectIdUseCase.getAllTasksByProjectId(project.id!!) }
		verify { outputFormatter.printWarning("⚠️ No tasks found for project '${project.title}'.") }
		verify(exactly = 0) { inputHandler.promptForIntChoice("🔹 Select a task to delete:", any()) }
	}
	@Disabled
	@Test
	fun `should handle project fetching failure`() = runTest {
		// Given
		val errorMessage = "Database connection error"
		coEvery { getAllProjectsUseCase.getAllProjects() } throws Exception(errorMessage)
		
		// When & Then
		assertThrows<Exception> {
			deleteTaskView.deleteTask()
		}
	}
	@Disabled
	@Test
	fun `should handle task deletion failure`() = runTest {
		// Given
		val project = createProject()
		val task = createTask(projectId = project.id)
		val errorMessage = "Task deletion failed"
		
		coEvery { getAllProjectsUseCase.getAllProjects() } returns listOf(project)
		
		coEvery { getAllTasksByProjectIdUseCase.getAllTasksByProjectId(project.id!!) } returns listOf(task)
		
		every { inputHandler.promptForIntChoice("🔹 Choose a project to delete its task:", any()) } returns 1
		every { inputHandler.promptForIntChoice("🔹 Select a task to delete:", any()) } returns 1
		every { inputHandler.promptForInput("Type 'YES' to confirm: ") } returns "YES"
		
		coEvery { deleteTaskUseCase.deleteTask(task) } throws Exception(errorMessage)
		
		// When & Then
		assertThrows<Exception> {
			deleteTaskView.deleteTask()
		}
	}
}