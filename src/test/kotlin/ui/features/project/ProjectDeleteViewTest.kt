package ui.features.project

import io.mockk.*
import kotlinx.coroutines.test.runTest
import logic.model.Project
import logic.model.State
import logic.usecase.project.DeleteProjectUseCase
import logic.usecase.project.GetAllProjectsUseCase
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import ui.utils.InputHandler
import ui.utils.OutputFormatter
import java.util.*

class ProjectDeleteViewTest {
	
	private lateinit var getAllProjectsUseCase: GetAllProjectsUseCase
	private lateinit var deleteProjectUseCase: DeleteProjectUseCase
	private lateinit var inputHandler: InputHandler
	private lateinit var outputFormatter: OutputFormatter
	private lateinit var projectDeleteView: ProjectDeleteView
	
	@BeforeEach
	fun setup() {
		getAllProjectsUseCase = mockk(relaxed = true)
		deleteProjectUseCase = mockk(relaxed = true)
		inputHandler = mockk(relaxed = true)
		outputFormatter = mockk(relaxed = true)
		
		projectDeleteView = ProjectDeleteView(
			getAllProjectsUseCase,
			deleteProjectUseCase,
			inputHandler,
			outputFormatter
		)
	}
	
	private fun getProject(id: UUID = UUID.randomUUID(), title: String = "Test Project"): Project {
		val state = State(UUID.randomUUID(), "To Do")
		return Project(
			id = id,
			title = title,
			description = "Test Description",
			createdBy = UUID.randomUUID(),
			tasks = emptyList(),
			state = state
		)
	}
	
	private fun getProjects(count: Int): List<Project> {
		return (1..count).map {
			getProject(title = "Test Project $it")
		}
	}
	
	@Test
	fun `should show error when no projects available`() = runTest {
		// Given
		coEvery { getAllProjectsUseCase.getAllProjects() } returns emptyList()
		
		// When
		projectDeleteView.deleteProject()
		
		// Then
		verify { outputFormatter.printHeader(any()) }
		verify { outputFormatter.printError("❌ No projects available for deletion!") }
		verify(exactly = 0) { inputHandler.promptForIntChoice(any(), any()) }
	}
	
	@Test
	fun `should delete project successfully when user confirms`() = runTest {
		// Given
		val projects = getProjects(3)
		val selectedIndex = 1
		val selectedProject = projects[selectedIndex]
		
		coEvery { getAllProjectsUseCase.getAllProjects() } returns projects
		every {
			inputHandler.promptForIntChoice(
				"🔹 Select a project to delete:",
				1..3
			)
		} returns 2
		every { inputHandler.promptForInput("Type 'YES' to confirm deletion: ") } returns "YES"
		coEvery { deleteProjectUseCase.deleteProjectById(selectedProject.id) } just Runs
		every { inputHandler.waitForEnter() } just Runs
		
		// When
		projectDeleteView.deleteProject()
		
		// Then
		verify { outputFormatter.printHeader(any()) }
		verify { outputFormatter.printInfo("📂 Available Projects:") }
		projects.forEachIndexed { index, project ->
			verify { outputFormatter.printInfo(match { it.contains("${index + 1}") && it.contains(project.title) }) }
		}
		verify { outputFormatter.printWarning(match { it.contains(selectedProject.title) && it.contains("cannot be undone") }) }
		coVerify { deleteProjectUseCase.deleteProjectById(selectedProject.id) }
		verify { outputFormatter.printSuccess(match { it.contains(selectedProject.title) && it.contains("deleted successfully") }) }
		verify { inputHandler.waitForEnter() }
	}
	
	@Test
	fun `should not delete project when user does not confirm`() = runTest {
		// Given
		val projects = getProjects(3)
		val selectedIndex = 1
		val selectedProject = projects[selectedIndex]
		
		coEvery { getAllProjectsUseCase.getAllProjects() } returns projects
		every {
			inputHandler.promptForIntChoice(
				"🔹 Select a project to delete:",
				1..3
			)
		} returns 2
		every { inputHandler.promptForInput("Type 'YES' to confirm deletion: ") } returns "NO"
		every { inputHandler.waitForEnter() } just Runs
		
		// When
		projectDeleteView.deleteProject()
		
		// Then
		verify { outputFormatter.printHeader(any()) }
		verify { outputFormatter.printWarning(match { it.contains(selectedProject.title) }) }
		coVerify(exactly = 0) { deleteProjectUseCase.deleteProjectById(any()) }
		verify(exactly = 0) { outputFormatter.printSuccess(any()) }
		verify { inputHandler.waitForEnter() }
	}
	
	@Test
	fun `should handle case insensitive confirmation`() = runTest {
		// Given
		val projects = getProjects(3)
		val selectedIndex = 0
		val selectedProject = projects[selectedIndex]
		
		coEvery { getAllProjectsUseCase.getAllProjects() } returns projects
		every {
			inputHandler.promptForIntChoice(
				"🔹 Select a project to delete:",
				1..3
			)
		} returns 1
		every { inputHandler.promptForInput("Type 'YES' to confirm deletion: ") } returns "yes"
		coEvery { deleteProjectUseCase.deleteProjectById(selectedProject.id) } just Runs
		every { inputHandler.waitForEnter() } just Runs
		
		// When
		projectDeleteView.deleteProject()
		
		// Then
		coVerify { deleteProjectUseCase.deleteProjectById(selectedProject.id) }
		verify { outputFormatter.printSuccess(match { it.contains(selectedProject.title) }) }
	}
	
	@Test
	fun `should handle deletion error`() = runTest {
		// Given
		val projects = getProjects(2)
		val selectedProject = projects[0]
		val errorMessage = "Database connection error"
		
		coEvery { getAllProjectsUseCase.getAllProjects() } returns projects
		every { inputHandler.promptForIntChoice("🔹 Select a project to delete:", 1..2) } returns 1
		every { inputHandler.promptForInput("Type 'YES' to confirm deletion: ") } returns "YES"
		coEvery { deleteProjectUseCase.deleteProjectById(selectedProject.id) } throws Exception(errorMessage)
		every { inputHandler.waitForEnter() } just Runs
		
		// When
		projectDeleteView.deleteProject()
		
		// Then
		verify { outputFormatter.printError(match { it.contains(errorMessage) }) }
		verify { inputHandler.waitForEnter() }
	}
}