package ui.features.task

import io.mockk.*
import kotlinx.coroutines.test.runTest
import logic.model.Project
import logic.model.State
import logic.model.Task
import logic.usecase.project.GetAllProjectsUseCase
import logic.usecase.state.EditStateUseCase
import logic.usecase.task.EditTaskUseCase
import logic.usecase.task.GetAllTasksByProjectIdUseCase
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import ui.utils.InputHandler
import ui.utils.OutputFormatter
import java.util.*

class EditTaskViewTest {
	
	private lateinit var editTaskUseCase: EditTaskUseCase
	private lateinit var getAllTasksByProjectIdUseCase: GetAllTasksByProjectIdUseCase
	private lateinit var inputHandler: InputHandler
	private lateinit var outputFormatter: OutputFormatter
	private lateinit var getAllProjectsUseCase: GetAllProjectsUseCase
	private lateinit var editStateUseCase: EditStateUseCase
	private lateinit var editTaskView: EditTaskView
	
	@BeforeEach
	fun setup() {
		editTaskUseCase = mockk(relaxed = true)
		getAllTasksByProjectIdUseCase = mockk(relaxed = true)
		inputHandler = mockk(relaxed = true)
		outputFormatter = mockk(relaxed = true)
		getAllProjectsUseCase = mockk(relaxed = true)
		editStateUseCase = mockk(relaxed = true)
		
		editTaskView = EditTaskView(
			editTaskUseCase,
			getAllTasksByProjectIdUseCase,
			inputHandler,
			outputFormatter,
			getAllProjectsUseCase,
			editStateUseCase
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
		projectId: UUID? = null,
		stateId: UUID? = null
	): Task {
		return Task(
			id = id,
			title = title,
			description = "Test Description",
			state = State(stateId ?: UUID.randomUUID(), "TODO"),
			projectId = projectId ?: UUID.randomUUID()
		)
	}
	
	@Test
	fun `should edit task successfully`() = runTest {
		// Given
		val project = createProject()
		val originalTask = createTask(projectId = project.id)
		
		coEvery { getAllProjectsUseCase.getAllProjects() } returns listOf(project)
		
		coEvery { getAllTasksByProjectIdUseCase.getAllTasksByProjectId(project.id!!) } returns listOf(originalTask)
		
		every { inputHandler.promptForIntChoice("🔹 Choose the number of project to edit its task:", any()) } returns 1
		every { inputHandler.promptForIntChoice("🔹 Select a task to edit:", any()) } returns 1
		every { inputHandler.promptForInput("✏️ Enter new title (leave empty to keep current): ") } returns "Updated Task"
		every { inputHandler.promptForInput("📝 Enter new description (leave empty to keep current): ") } returns "Updated Description"
		every { inputHandler.promptForInput("🚀 Enter new state (leave empty to keep current): ") } returns "In Progress"
		
		coEvery { editStateUseCase.editState(any()) } returns Unit
		coEvery { editTaskUseCase.editTask(any()) } returns Unit
		
		// When
		editTaskView.editTask()
		
		// Then
		coVerify { getAllProjectsUseCase.getAllProjects() }
		coVerify { getAllTasksByProjectIdUseCase.getAllTasksByProjectId(project.id!!) }
		coVerify { editStateUseCase.editState(match { it.title == "In Progress" }) }
		coVerify {
			editTaskUseCase.editTask(match {
				it.title == "Updated Task" &&
						it.description == "Updated Description" &&
						it.state?.title == "In Progress"
			})
		}
		verify { outputFormatter.printSuccess("✅ Task 'Updated Task' updated successfully!") }
	}
	
	@Test
	fun `should handle no projects available`() = runTest {
		// Given
		coEvery { getAllProjectsUseCase.getAllProjects() } returns emptyList()
		
		// When
		editTaskView.editTask()
		
		// Then
		verify { outputFormatter.printError("❌ No projects available for task editing!") }
		verify(exactly = 0) { inputHandler.promptForIntChoice(any(), any()) }
	}
	
	@Test
	fun `should handle no tasks in selected project`() = runTest {
		// Given
		val project = createProject()
		
		coEvery { getAllProjectsUseCase.getAllProjects() } returns listOf(project)
		coEvery { getAllTasksByProjectIdUseCase.getAllTasksByProjectId(project.id!!) } returns emptyList()
		every { inputHandler.promptForIntChoice("🔹 Choose the number of project to edit its task:", any()) } returns 1
		
		// When
		editTaskView.editTask()
		
		// Then
		coVerify { getAllProjectsUseCase.getAllProjects() }
		coVerify { getAllTasksByProjectIdUseCase.getAllTasksByProjectId(project.id!!) }
		verify { outputFormatter.printWarning("⚠️ No tasks found for project '${project.title}'.") }
		verify(exactly = 0) { inputHandler.promptForIntChoice("🔹 Select a task to edit:", any()) }
	}
	
	@Test
	fun `should keep original task details when no changes provided`() = runTest {
		// Given
		val project = createProject()
		val originalTask = createTask(projectId = project.id)
		
		coEvery { getAllProjectsUseCase.getAllProjects() } returns listOf(project)
		coEvery { getAllTasksByProjectIdUseCase.getAllTasksByProjectId(project.id!!) } returns listOf(originalTask)
		every { inputHandler.promptForIntChoice("🔹 Choose the number of project to edit its task:", any()) } returns 1
		every { inputHandler.promptForIntChoice("🔹 Select a task to edit:", any()) } returns 1
		every { inputHandler.promptForInput("✏️ Enter new title (leave empty to keep current): ") } returns ""
		every { inputHandler.promptForInput("📝 Enter new description (leave empty to keep current): ") } returns ""
		every { inputHandler.promptForInput("🚀 Enter new state (leave empty to keep current): ") } returns ""
		coEvery { editStateUseCase.editState(any()) } returns Unit
		coEvery { editTaskUseCase.editTask(any()) } returns Unit
		
		// When
		editTaskView.editTask()
		
		// Then
		coVerify { editStateUseCase.editState(match { it.title == originalTask.state?.title }) }
		coVerify {
			editTaskUseCase.editTask(match {
				it.title == originalTask.title &&
						it.description == originalTask.description &&
						it.state?.title == originalTask.state?.title
			})
		}
	}
	
	@Test
	fun `should handle project fetching failure`() = runTest {
		// Given
		val errorMessage = "Database connection error"
		coEvery { getAllProjectsUseCase.getAllProjects() } throws Exception(errorMessage)
		
		// When & Then
		assertThrows<Exception> {
			editTaskView.editTask()
		}
	}
}