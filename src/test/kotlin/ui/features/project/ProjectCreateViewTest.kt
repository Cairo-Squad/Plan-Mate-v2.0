package ui.features.project

import data.dto.UserType
import io.mockk.*
import kotlinx.coroutines.test.runTest
import logic.model.Project
import logic.model.State
import logic.model.Task
import logic.model.User
import logic.usecase.project.CreateProjectUseCase
import logic.usecase.state.CreateStateUseCase
import logic.usecase.task.CreateTaskUseCase
import logic.usecase.user.GetCurrentUserUseCase
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import ui.utils.InputHandler
import ui.utils.OutputFormatter
import java.util.*

class ProjectCreateViewTest {
	
	private lateinit var createProjectUseCase: CreateProjectUseCase
	private lateinit var inputHandler: InputHandler
	private lateinit var outputFormatter: OutputFormatter
	private lateinit var createStateUseCase: CreateStateUseCase
	private lateinit var createTaskUseCase: CreateTaskUseCase
	private lateinit var getCurrentUserUseCase: GetCurrentUserUseCase
	private lateinit var projectCreateView: ProjectCreateView
	
	@BeforeEach
	fun setup() {
		createProjectUseCase = mockk(relaxed = true)
		inputHandler = mockk(relaxed = true)
		outputFormatter = mockk(relaxed = true)
		createStateUseCase = mockk(relaxed = true)
		createTaskUseCase = mockk(relaxed = true)
		getCurrentUserUseCase = mockk(relaxed = true)
		
		projectCreateView = ProjectCreateView(
			createProjectUseCase,
			inputHandler,
			outputFormatter,
			createStateUseCase,
			createTaskUseCase,
			getCurrentUserUseCase
		)
	}
	
	private fun getUser(): User {
		return User(UUID.randomUUID(), "ahmed", "123456", UserType.ADMIN)
	}
	
	private suspend fun getProject(): Project {
		val state = State(UUID.randomUUID(), "To Do")
		return Project(UUID.randomUUID(),"Test Project", "Test Description", getUser().id, emptyList(), state)
	}
	
	private fun getTask(projectId: UUID, title: String, description: String, stateTitle: String): Task {
		val state = State(UUID.randomUUID(), stateTitle)
		return Task(UUID.randomUUID(), title, description, state, projectId)
	}
	
	@Test
	fun `should create project without tasks successfully`() = runTest {
		val user = getUser()
		val project = getProject()
		val projectId = UUID.randomUUID()
		
		coEvery { getCurrentUserUseCase.getCurrentUser() } returns user
		every { inputHandler.promptForInput("📂 Enter project title: ") } returns project.title!!
		every { inputHandler.promptForInput("📝 Enter project description: ") } returns project.description!!
		every { inputHandler.promptForInput("📊 Enter initial project state: ") } returns project.state!!.title
		every { inputHandler.promptForYesNo("Do you want to add tasks to this project?") } returns false
		coEvery { createProjectUseCase.createProject(any(), user) } returns projectId
		
		projectCreateView.createProject()
		
		coVerify { createStateUseCase.createState(match { it.title == project.state!!.title }) }
		coVerify { createProjectUseCase.createProject(any(), user) }
		verify { outputFormatter.printSuccess(match { it.contains(project.title!!) }) }
	}
	
	@Test
	fun `should create project with tasks successfully`() = runTest {
		val user = getUser()
		val project = getProject()
		val projectId = UUID.randomUUID()
		val task = getTask(projectId, "Task 1", "Task 1 Description", "In Progress")
		
		coEvery { getCurrentUserUseCase.getCurrentUser() } returns user
		every { inputHandler.promptForInput("📂 Enter project title: ") } returns project.title!!
		every { inputHandler.promptForInput("📝 Enter project description: ") } returns project.description!!
		every { inputHandler.promptForInput("📊 Enter initial project state: ") } returns project.state!!.title
		every { inputHandler.promptForYesNo("Do you want to add tasks to this project?") } returns true
		every { inputHandler.promptForInput("✅ Task title: ") } returns task.title
		every { inputHandler.promptForInput("📝 Task description: ") } returns task.description
		every { inputHandler.promptForInput("📊 Task state: ") } returns task.state.title
		every { inputHandler.promptForYesNo("➕ Do you want to add another task?") } returns false
		coEvery { createProjectUseCase.createProject(any(), user) } returns projectId
		
		projectCreateView.createProject()
		
		coVerify { createTaskUseCase.createTask(match {
			it.title == task.title &&
					it.description == task.description &&
					it.state.title == task.state.title &&
					it.projectId == projectId
		}) }
	}
	
	@Test
	fun `should handle project creation failure`() = runTest {
		val user = getUser()
		val project = getProject()
		val errorMessage = "Database connection error"
		
		coEvery { getCurrentUserUseCase.getCurrentUser() } returns user
		every { inputHandler.promptForInput("📂 Enter project title: ") } returns project.title!!
		every { inputHandler.promptForInput("📝 Enter project description: ") } returns project.description!!
		every { inputHandler.promptForInput("📊 Enter initial project state: ") } returns project.state!!.title
		every { inputHandler.promptForYesNo("Do you want to add tasks to this project?") } returns false
		coEvery { createProjectUseCase.createProject(any(), user) } throws Exception(errorMessage)
		
		projectCreateView.createProject()
		
		verify { outputFormatter.printError(match { it.contains(errorMessage) }) }
	}
	
	@Test
	fun `should handle no authenticated user`() = runTest {
		coEvery { getCurrentUserUseCase.getCurrentUser() } returns null
		
		projectCreateView.createProject()
		
		verify { outputFormatter.printError(match { it.contains("No authenticated user found") }) }
		verify(exactly = 0) { inputHandler.promptForInput(any()) }
		coVerify(exactly = 0) { createProjectUseCase.createProject(any(), any()) }
	}
	
	@Test
	fun `should create project with multiple tasks successfully`() = runTest {
		val user = getUser()
		val project = getProject()
		val projectId = UUID.randomUUID()
		val task1 = getTask(projectId, "Task 1", "Task 1 Description", "In Progress")
		val task2 = getTask(projectId, "Task 2", "Task 2 Description", "Blocked")
		
		coEvery { getCurrentUserUseCase.getCurrentUser() } returns user
		every { inputHandler.promptForInput("📂 Enter project title: ") } returns project.title!!
		every { inputHandler.promptForInput("📝 Enter project description: ") } returns project.description!!
		every { inputHandler.promptForInput("📊 Enter initial project state: ") } returns project.state!!.title
		every { inputHandler.promptForYesNo("Do you want to add tasks to this project?") } returns true
		every { inputHandler.promptForInput("✅ Task title: ") } returnsMany listOf(task1.title, task2.title)
		every { inputHandler.promptForInput("📝 Task description: ") } returnsMany listOf(task1.description, task2.description)
		every { inputHandler.promptForInput("📊 Task state: ") } returnsMany listOf(task1.state.title, task2.state.title)
		every { inputHandler.promptForYesNo("➕ Do you want to add another task?") } returnsMany listOf(true, false)
		coEvery { createProjectUseCase.createProject(any(), user) } returns projectId
		
		projectCreateView.createProject()
		
		coVerify(exactly = 2) { createTaskUseCase.createTask(any()) }
		coVerify {
			createTaskUseCase.createTask(match {
				it.title == task1.title &&
						it.description == task1.description &&
						it.state.title == task1.state.title &&
						it.projectId == projectId
			})
		}
		coVerify {
			createTaskUseCase.createTask(match {
				it.title == task2.title &&
						it.description == task2.description &&
						it.state.title == task2.state.title &&
						it.projectId == projectId
			})
		}
	}
}
