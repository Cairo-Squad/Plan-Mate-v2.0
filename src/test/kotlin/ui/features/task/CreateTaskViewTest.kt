package ui.features.task

import io.mockk.*
import kotlinx.coroutines.test.runTest
import logic.model.Project
import logic.model.State
import logic.model.Task
import logic.usecase.project.EditProjectUseCase
import logic.usecase.project.GetAllProjectsUseCase
import logic.usecase.state.CreateStateUseCase
import logic.usecase.state.GetAllStatesUseCase
import logic.usecase.task.CreateTaskUseCase
import logic.usecase.task.GetAllTasksByProjectIdUseCase
import logic.usecase.task.GetAllTasksUseCase
import logic.usecase.task.GetTaskByIdUseCaseTest
import logic.usecase.task.GetTaskBytIdUseCase
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Disabled
import org.junit.jupiter.api.Test
import ui.utils.InputHandler
import ui.utils.OutputFormatter
import java.util.*

class CreateTaskViewTest {

	private lateinit var createTaskUseCase: CreateTaskUseCase
	private lateinit var inputHandler: InputHandler
	private lateinit var outputFormatter: OutputFormatter
	private lateinit var editProjectUseCase: EditProjectUseCase
	private lateinit var getAllProjectsUseCase: GetAllProjectsUseCase
	private lateinit var createStateUseCase: CreateStateUseCase
	private lateinit var createTaskView: CreateTaskView
	private lateinit var getAllStatesUseCase: GetAllStatesUseCase
	private lateinit var getAllTasksUseCase: GetAllTasksUseCase
	private lateinit var getTaskByIdUseCase: GetTaskBytIdUseCase

	@BeforeEach
	fun setup() {
		createTaskUseCase = mockk(relaxed = true)
		inputHandler = mockk(relaxed = true)
		outputFormatter = mockk(relaxed = true)
		editProjectUseCase = mockk(relaxed = true)
		getAllProjectsUseCase = mockk(relaxed = true)
		createStateUseCase = mockk(relaxed = true)
		getTaskByIdUseCase = mockk(relaxed = true)

		createTaskView = CreateTaskView(
			createTaskUseCase,
			inputHandler,
			outputFormatter,
			editProjectUseCase,
			getAllProjectsUseCase,
			createStateUseCase,
			getAllStatesUseCase,
			getAllTasksUseCase,
            getTaskByIdUseCase
		)
	}

	private fun getProject(): Project {
		val projectId = UUID.randomUUID()
		val state = State(UUID.randomUUID(), "To Do")
		return Project(
			id = projectId,
			title = "Test Project",
			description = "Test Project Description",
			createdBy = UUID.randomUUID(),
			tasks = emptyList(),
			state = state
		)
	}
	private fun getTask(project: Project):Task{
		return Task(
			title = "Test Task",
			description = "Test Task Description",
			state = State(UUID.randomUUID(), "TODO"),
			projectId = project.id!!
		)
	}
	@Disabled
	@Test
	fun `should create task successfully`() = runTest {
		// Given
		val project = getProject()
		val task = getTask(project)
		val state = State(UUID.randomUUID(), "Test State")

		coEvery { getAllProjectsUseCase.getAllProjects() } returns listOf(project)

		every { inputHandler.promptForInput("Enter task title: ") } returns task.title!!
		every { inputHandler.promptForInput("Enter task description: ") } returns task.description!!
		every { inputHandler.promptForIntChoice(any(), any()) } returns 1

		coEvery { createStateUseCase.createState(any()) } returns true
		coEvery { createTaskUseCase.createTask(any()) } returns task.id!!
		coEvery { editProjectUseCase.editProject(any()) } just Runs

		// When
		createTaskView.createTask()

		// Then
		coVerify { getAllProjectsUseCase.getAllProjects() }
		coVerify { createStateUseCase.createState(any()) }
		coVerify { createTaskUseCase.createTask(match { it.title == task.title }) }
		coVerify { editProjectUseCase.editProject(any()) }
		verify { outputFormatter.printSuccess("Task created successfully!") }
	}
	@Disabled
	@Test
	fun `should handle no projects available`() = runTest {
		// Given
		coEvery { getAllProjectsUseCase.getAllProjects() } returns emptyList()

		every { inputHandler.promptForInput("Enter task title: ") } returns "Test Task"
		every { inputHandler.promptForInput("Enter task description: ") } returns "Test Description"

		// When
		createTaskView.createTask()

		// Then
		coVerify { getAllProjectsUseCase.getAllProjects() }
		verify { outputFormatter.printError("No projects available. Please create a project first.") }
		coVerify (exactly = 0) { createTaskUseCase.createTask(any()) }
	}
	@Disabled
	@Test
	fun `should handle project fetching failure`() = runTest {
		// Given
		val errorMessage = "Network error"
		coEvery { getAllProjectsUseCase.getAllProjects() } throws Exception(errorMessage)

		every { inputHandler.promptForInput("Enter task title: ") } returns "Test Task"
		every { inputHandler.promptForInput("Enter task description: ") } returns "Test Description"

		// When
		createTaskView.createTask()

		// Then
		verify { outputFormatter.printError("Failed to get all projects: $errorMessage") }
	}
	@Disabled
	@Test
	fun `should handle task creation failure`() = runTest {
		// Given
		val project = getProject()
		val task = getTask(project)
		val state = State(UUID.randomUUID(), "Test State")
		coEvery { getAllProjectsUseCase.getAllProjects() } returns listOf(project)

		every { inputHandler.promptForInput("Enter task title: ") } returns task.title!!
		every { inputHandler.promptForInput("Enter task description: ") } returns task.description!!
		every { inputHandler.promptForIntChoice(any(), any()) } returns 1

		coEvery { createStateUseCase.createState(any()) } returns true

		val errorMessage = "Database error"
		coEvery { createTaskUseCase.createTask(any()) } throws Exception(errorMessage)

		// When
		createTaskView.createTask()

		// Then
		coVerify { getAllProjectsUseCase.getAllProjects() }
		coVerify { createStateUseCase.createState(any()) }
		coVerify { createTaskUseCase.createTask(any()) }
		verify { outputFormatter.printError(errorMessage) }
	}
}