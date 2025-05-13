package ui.features.project

import io.mockk.*
import kotlinx.coroutines.test.runTest
import logic.model.Project
import logic.model.State
import logic.model.Task
import logic.usecase.project.GetAllProjectsUseCase
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Disabled
import org.junit.jupiter.api.Test
import ui.utils.InputHandler
import ui.utils.OutputFormatter
import java.util.*

class ProjectDetailViewTest {
	
	private lateinit var getAllProjectsUseCase: GetAllProjectsUseCase
	private lateinit var inputHandler: InputHandler
	private lateinit var outputFormatter: OutputFormatter
	private lateinit var projectDetailView: ProjectDetailView
	
	@BeforeEach
	fun setup() {
		getAllProjectsUseCase = mockk(relaxed = true)
		inputHandler = mockk(relaxed = true)
		outputFormatter = mockk(relaxed = true)
		
		projectDetailView = ProjectDetailView(
			getAllProjectsUseCase,
			inputHandler,
			outputFormatter
		)
	}
	
	private fun getTask(id: UUID = UUID.randomUUID(), title: String = "Test Task"): Task {
		val state = State(UUID.randomUUID(), "To Do")
		return Task(
			id = id,
			title = title,
			description = "Task Description",
			projectId = UUID.randomUUID(),
			state = state,
		)
	}
	
	private fun getProject(
		id: UUID = UUID.randomUUID(),
		title: String = "Test Project",
		taskCount: Int = 0
	): Project {
		val state = State(UUID.randomUUID(), "Active")
		val tasks = (1..taskCount).map {
			getTask(title = "Task $it")
		}
		return Project(
			id = id,
			title = title,
			description = "Test Description",
			createdBy = UUID.randomUUID(),
			tasks = tasks,
			state = state
		)
	}
	
	private fun getProjects(count: Int, tasksPerProject: Int = 0): List<Project> {
		return (1..count).map {
			getProject(title = "Test Project $it", taskCount = tasksPerProject)
		}
	}
	@Disabled
	@Test
	fun `should show error when no projects available`() = runTest {
		// Given
		coEvery { getAllProjectsUseCase.getAllProjects() } returns emptyList()
		
		// When
		projectDetailView.viewProjectDetails()
		
		// Then
		verify { outputFormatter.printHeader(any()) }
		verify { outputFormatter.printError("❌ No projects available to view!") }
		verify(exactly = 0) { inputHandler.promptForYesNo(any()) }
	}
	@Disabled
	@Test
	fun `should display project list but not details when user declines to view details`() = runTest {
		// Given
		val projects = getProjects(3)
		
		coEvery { getAllProjectsUseCase.getAllProjects() } returns projects
		every { inputHandler.promptForYesNo("🔍 Would you like to view details for a specific project?") } returns false
		
		// When
		projectDetailView.viewProjectDetails()
		
		// Then
		verify { outputFormatter.printHeader(any()) }
		verify { outputFormatter.printInfo("📂 Available Projects:") }
		projects.forEachIndexed { index, project ->
			verify { outputFormatter.printInfo(match { it.contains("${index + 1}") && it.contains(project.title!!) }) }
		}
		verify(exactly = 0) { inputHandler.promptForIntChoice(any(), any()) }
		verify(exactly = 0) { outputFormatter.printInfo("📝 Description") }
	}
	@Disabled
	@Test
	fun `should display project details when user chooses to view a project`() = runTest {
		// Given
		val projects = getProjects(3, tasksPerProject = 2)
		val selectedIndex = 1
		val selectedProject = projects[selectedIndex]
		
		coEvery { getAllProjectsUseCase.getAllProjects() } returns projects
		every { inputHandler.promptForYesNo("🔍 Would you like to view details for a specific project?") } returns true
		every {
			inputHandler.promptForIntChoice("🔹 Select a project number:", 1..3)
		} returns selectedIndex + 1
		every { inputHandler.waitForEnter() } just Runs
		
		// When
		projectDetailView.viewProjectDetails()
		
		// Then
		verify { outputFormatter.printHeader(any()) }
		verify { outputFormatter.printInfo("📂 Available Projects:") }
		verify { outputFormatter.printHeader("📜 Project Information") }
		verify { outputFormatter.printInfo("🆔 Project ID: ${selectedProject.id}") }
		verify { outputFormatter.printInfo("📂 Title: ${selectedProject.title}") }
		verify { outputFormatter.printInfo("📝 Description: ${selectedProject.description}") }
		verify { outputFormatter.printInfo("📊 State: ${selectedProject.state!!.title}") }
		verify { outputFormatter.printInfo(match { it.startsWith("✅ Tasks:") }) }
		verify { inputHandler.waitForEnter() }
	}
	@Disabled
	@Test
	fun `should display project with empty task list correctly`() = runTest {
		// Given
		val project = getProject(taskCount = 0)
		val projects = listOf(project)
		
		coEvery { getAllProjectsUseCase.getAllProjects() } returns projects
		every { inputHandler.promptForYesNo("🔍 Would you like to view details for a specific project?") } returns true
		every { inputHandler.promptForIntChoice("🔹 Select a project number:", 1..1) } returns 1
		every { inputHandler.waitForEnter() } just Runs
		
		// When
		projectDetailView.viewProjectDetails()
		
		// Then
		verify { outputFormatter.printInfo(match { it.contains("✅ Tasks:") && it.contains("⚠️ No tasks available.") }) }
		verify { inputHandler.waitForEnter() }
	}
	@Disabled
	@Test
	fun `should display project with multiple tasks correctly`() = runTest {
		// Given
		val taskCount = 3
		val project = getProject(taskCount = taskCount)
		val projects = listOf(project)
		
		coEvery { getAllProjectsUseCase.getAllProjects() } returns projects
		every { inputHandler.promptForYesNo("🔍 Would you like to view details for a specific project?") } returns true
		every { inputHandler.promptForIntChoice("🔹 Select a project number:", 1..1) } returns 1
		every { inputHandler.waitForEnter() } just Runs
		
		// When
		projectDetailView.viewProjectDetails()
		
		// Then
		for (i in 1..taskCount) {
			verify { outputFormatter.printInfo(match { it.contains("Task $i") }) }
		}
		verify { inputHandler.waitForEnter() }
	}
}