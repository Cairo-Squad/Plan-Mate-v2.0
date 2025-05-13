package ui.features.project

import data.dto.UserType
import io.mockk.*
import kotlinx.coroutines.test.runTest
import logic.model.Project
import logic.model.State
import logic.model.User
import logic.usecase.project.CreateProjectUseCase
import logic.usecase.state.CreateStateUseCase
import logic.usecase.state.GetAllStatesUseCase
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
	private lateinit var getCurrentUserUseCase: GetCurrentUserUseCase
	private lateinit var projectCreateView: ProjectCreateView
	private lateinit var getAllStatesUseCase: GetAllStatesUseCase

	@BeforeEach
	fun setup() {
		createProjectUseCase = mockk(relaxed = true)
		inputHandler = mockk(relaxed = true)
		outputFormatter = mockk(relaxed = true)
		createStateUseCase = mockk(relaxed = true)
		getCurrentUserUseCase = mockk(relaxed = true)
		

		projectCreateView = ProjectCreateView(
			createProjectUseCase,
			inputHandler,
			outputFormatter,
			createStateUseCase,
			getCurrentUserUseCase,
			getAllStatesUseCase = getAllStatesUseCase
		)
	}

	private fun getUser(): User {
		return User(UUID.randomUUID(), "ahmed", "123456", UserType.ADMIN)
	}

	private fun getProject(): Project {
		val state = State(UUID.randomUUID(), "To Do")
		return Project(UUID.randomUUID(),"Test Project", "Test Description", getUser().id, emptyList(), state)
	}

	@Test
	fun `should create project successfully`() = runTest {
		// Given
		val user = getUser()
		val project = getProject()

		coEvery { getCurrentUserUseCase.getCurrentUser() } returns user
		every { inputHandler.promptForInput("📂 Enter project title: ") } returns project.title!!
		every { inputHandler.promptForInput("📝 Enter project description: ") } returns project.description!!
		every { inputHandler.promptForInput("📊 Enter initial project state: ") } returns project.state!!.title!!
		every { inputHandler.promptForYesNo("Do you want to add tasks to this project?") } returns false
		coEvery { createProjectUseCase.createProject(any(), user) } returns true

		// When
		projectCreateView.createProject()

		// Then
		coVerify { createStateUseCase.createState(match { it.title == project.state!!.title }) }
		coVerify { createProjectUseCase.createProject(any(), user) }
		verify { outputFormatter.printSuccess(match { it.contains(project.title!!) }) }
	}

	@Test
	fun `should handle project creation failure`() = runTest {
		// Given
		val user = getUser()
		val project = getProject()
		val errorMessage = "Database connection error"

		coEvery { getCurrentUserUseCase.getCurrentUser() } returns user
		every { inputHandler.promptForInput("📂 Enter project title: ") } returns project.title!!
		every { inputHandler.promptForInput("📝 Enter project description: ") } returns project.description!!
		every { inputHandler.promptForInput("📊 Enter initial project state: ") } returns project.state!!.title!!
		every { inputHandler.promptForYesNo("Do you want to add tasks to this project?") } returns false
		coEvery { createProjectUseCase.createProject(any(), user) } throws Exception(errorMessage)

		// When
		projectCreateView.createProject()

		// Then
		verify { outputFormatter.printError(match { it.contains(errorMessage) }) }
	}

	@Test
	fun `should handle no authenticated user`() = runTest {
		// Given
		coEvery { getCurrentUserUseCase.getCurrentUser() } returns null

		// When
		projectCreateView.createProject()

		// Then
		verify { outputFormatter.printError(match { it.contains("No authenticated user found") }) }
		verify(exactly = 0) { inputHandler.promptForInput(any()) }
		coVerify(exactly = 0) { createProjectUseCase.createProject(any(), any()) }
	}


}
