package ui.features.project

import io.mockk.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import ui.features.log.ProjectLogView
import ui.utils.InputHandler
import ui.utils.OutputFormatter

class ProjectManagementViewTest {
	
	private lateinit var projectCreateView: ProjectCreateView
	private lateinit var projectEditView: ProjectEditView
	private lateinit var projectDeleteView: ProjectDeleteView
	private lateinit var projectDetailView: ProjectDetailView
	private lateinit var projectLogView: ProjectLogView
	private lateinit var inputHandler: InputHandler
	private lateinit var outputFormatter: OutputFormatter
	private lateinit var projectManagementView: ProjectManagementView
	
	@BeforeEach
	fun setup() {
		projectCreateView = mockk(relaxed = true)
		projectEditView = mockk(relaxed = true)
		projectDeleteView = mockk(relaxed = true)
		projectDetailView = mockk(relaxed = true)
		projectLogView = mockk(relaxed = true)
		inputHandler = mockk(relaxed = true)
		outputFormatter = mockk(relaxed = true)
		
		projectManagementView = ProjectManagementView(
			projectCreateView,
			projectEditView,
			projectDeleteView,
			projectDetailView,
			projectLogView,
			inputHandler,
			outputFormatter
		)
	}
	
	@Test
	fun `should display project menu properly`() {
		// Given
		every { inputHandler.promptForIntChoice("🔹 Select an option:", 1..6) } returns 6
		
		// When
		projectManagementView.showProjectMenu()
		
		// Then
		verify { outputFormatter.printHeader(any()) }
		verify {
			outputFormatter.printMenu(
				listOf(
					"🆕  1. Create Project",
					"✏️  2. Edit Project",
					"🗑️  3. Delete Project",
					"📜  4. View Project Logs",
					"📂  5. View All Projects",
					"🚪  6. Exit"
				)
			)
		}
		verify { outputFormatter.printSuccess("✅ Exiting project management. Have a great day! 👋") }
	}
	
	@Test
	fun `should call create project view when user selects option 1`() {
		// Given
		every { inputHandler.promptForIntChoice("🔹 Select an option:", 1..6) } returnsMany listOf(1, 6)
		
		// When
		projectManagementView.showProjectMenu()
		
		// Then
		verify { projectCreateView.createProject() }
		verify(exactly = 0) { projectEditView.editProject() }
		verify(exactly = 0) { projectDeleteView.deleteProject() }
		verify(exactly = 0) { projectLogView.viewProjectLogs() }
		verify(exactly = 0) { projectDetailView.viewProjectDetails() }
	}
	
	@Test
	fun `should call edit project view when user selects option 2`() {
		// Given
		every { inputHandler.promptForIntChoice("🔹 Select an option:", 1..6) } returnsMany listOf(2, 6)
		
		// When
		projectManagementView.showProjectMenu()
		
		// Then
		verify(exactly = 0) { projectCreateView.createProject() }
		verify { projectEditView.editProject() }
		verify(exactly = 0) { projectDeleteView.deleteProject() }
		verify(exactly = 0) { projectLogView.viewProjectLogs() }
		verify(exactly = 0) { projectDetailView.viewProjectDetails() }
	}
	
	@Test
	fun `should call delete project view when user selects option 3`() {
		// Given
		every { inputHandler.promptForIntChoice("🔹 Select an option:", 1..6) } returnsMany listOf(3, 6)
		
		// When
		projectManagementView.showProjectMenu()
		
		// Then
		verify(exactly = 0) { projectCreateView.createProject() }
		verify(exactly = 0) { projectEditView.editProject() }
		verify { projectDeleteView.deleteProject() }
		verify(exactly = 0) { projectLogView.viewProjectLogs() }
		verify(exactly = 0) { projectDetailView.viewProjectDetails() }
	}
	
	@Test
	fun `should call view project logs when user selects option 4`() {
		// Given
		every { inputHandler.promptForIntChoice("🔹 Select an option:", 1..6) } returnsMany listOf(4, 6)
		
		// When
		projectManagementView.showProjectMenu()
		
		// Then
		verify(exactly = 0) { projectCreateView.createProject() }
		verify(exactly = 0) { projectEditView.editProject() }
		verify(exactly = 0) { projectDeleteView.deleteProject() }
		verify { projectLogView.viewProjectLogs() }
		verify(exactly = 0) { projectDetailView.viewProjectDetails() }
	}
	
	@Test
	fun `should call view project details when user selects option 5`() {
		// Given
		every { inputHandler.promptForIntChoice("🔹 Select an option:", 1..6) } returnsMany listOf(5, 6)
		
		// When
		projectManagementView.showProjectMenu()
		
		// Then
		verify(exactly = 0) { projectCreateView.createProject() }
		verify(exactly = 0) { projectEditView.editProject() }
		verify(exactly = 0) { projectDeleteView.deleteProject() }
		verify(exactly = 0) { projectLogView.viewProjectLogs() }
		verify { projectDetailView.viewProjectDetails() }
	}
	
	@Test
	fun `should exit menu when user selects option 6`() {
		// Given
		every { inputHandler.promptForIntChoice("🔹 Select an option:", 1..6) } returns 6
		
		// When
		projectManagementView.showProjectMenu()
		
		// Then
		verify(exactly = 0) { projectCreateView.createProject() }
		verify(exactly = 0) { projectEditView.editProject() }
		verify(exactly = 0) { projectDeleteView.deleteProject() }
		verify(exactly = 0) { projectLogView.viewProjectLogs() }
		verify(exactly = 0) { projectDetailView.viewProjectDetails() }
		verify { outputFormatter.printSuccess("✅ Exiting project management. Have a great day! 👋") }
	}
}