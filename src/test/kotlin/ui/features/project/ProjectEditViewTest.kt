package ui.features.project

import io.mockk.*
import kotlinx.coroutines.test.runTest
import logic.model.Project
import logic.model.State
import logic.usecase.project.EditProjectUseCase
import logic.usecase.project.GetAllProjectsUseCase
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Disabled
import org.junit.jupiter.api.Test
import ui.features.task.EditTaskView
import ui.utils.InputHandler
import ui.utils.OutputFormatter
import java.util.*

class ProjectEditViewTest {
	
	private lateinit var editProjectUseCase: EditProjectUseCase
	private lateinit var getAllProjectsUseCase: GetAllProjectsUseCase
	private lateinit var inputHandler: InputHandler
	private lateinit var outputFormatter: OutputFormatter
	private lateinit var editTaskView: EditTaskView
	private lateinit var projectEditView: ProjectEditView
	
	@BeforeEach
	fun setup() {
		editProjectUseCase = mockk(relaxed = true)
		getAllProjectsUseCase = mockk(relaxed = true)
		inputHandler = mockk(relaxed = true)
		outputFormatter = mockk(relaxed = true)
		editTaskView = mockk(relaxed = true)
		
		projectEditView = ProjectEditView(
			editProjectUseCase,
			getAllProjectsUseCase,
			inputHandler,
			outputFormatter,
			editTaskView
		)
	}
	
	private fun getProject(id: UUID = UUID.randomUUID(), title: String = "Test Project"): Project {
		val state = State(UUID.randomUUID(), "Active")
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
	@Disabled
	@Test
	fun `should show error when unable to retrieve projects`() = runTest {
		// Given
		val errorMessage = "Database connection error"
		coEvery { getAllProjectsUseCase.getAllProjects() } throws Exception(errorMessage)
		
		// When
		projectEditView.editProject()
		
		// Then
		verify { outputFormatter.printHeader("Edit Project") }
		verify { outputFormatter.printError(errorMessage) }
		verify(exactly = 0) { inputHandler.promptForIntChoice(any(), any()) }
	}
	@Disabled
	@Test
	fun `should show error when no projects available`() = runTest {
		// Given
		coEvery { getAllProjectsUseCase.getAllProjects() } returns emptyList()
		
		// When
		projectEditView.editProject()
		
		// Then
		verify { outputFormatter.printHeader("Edit Project") }
		verify { outputFormatter.printError("No projects available to edit.") }
		verify(exactly = 0) { inputHandler.promptForIntChoice(any(), any()) }
	}
	@Disabled
	@Test
	fun `should update project basic info without editing tasks`() = runTest {
		// Given
		val projects = getProjects(3)
		val selectedIndex = 1
		val selectedProject = projects[selectedIndex]
		val newTitle = "Updated Project Title"
		val newDescription = "Updated Project Description"
		
		coEvery { getAllProjectsUseCase.getAllProjects() } returns projects
		every {
			inputHandler.promptForIntChoice("Select the project number to edit: ", 1..3)
		} returns selectedIndex + 1
		every { inputHandler.promptForYesNo("Do you want to edit basic project information?") } returns true
		every { inputHandler.promptForInput("Enter new project title (leave empty to keep current): ") } returns newTitle
		every {
			inputHandler.promptForInput("Enter new project description (leave empty to keep current): ")
		} returns newDescription
		every { inputHandler.promptForYesNo("Do you want to edit tasks within this project?") } returns false
		coEvery {
			editProjectUseCase.editProject(match {
				it.id == selectedProject.id &&
						it.title == newTitle &&
						it.description == newDescription
			})
		} just Runs
		
		// When
		projectEditView.editProject()
		
		// Then
		verify { outputFormatter.printHeader("Edit Project") }
		verify { outputFormatter.printHeader("Available Projects:") }
		projects.forEachIndexed { index, project ->
			verify { outputFormatter.printInfo("${index + 1}. ${project.title} (ID: ${project.id})") }
		}
		verify(exactly = 0) { editTaskView.editTask() }
		coVerify { editProjectUseCase.editProject(any()) }
		verify { outputFormatter.printSuccess("Project updated successfully!") }
	}
	@Disabled
	@Test
	fun `should keep original values when input is empty`() = runTest {
		// Given
		val project = getProject()
		val projects = listOf(project)
		
		coEvery { getAllProjectsUseCase.getAllProjects() } returns projects
		every { inputHandler.promptForIntChoice("Select the project number to edit: ", 1..1) } returns 1
		every { inputHandler.promptForYesNo("Do you want to edit basic project information?") } returns true
		every { inputHandler.promptForInput("Enter new project title (leave empty to keep current): ") } returns ""
		every {
			inputHandler.promptForInput("Enter new project description (leave empty to keep current): ")
		} returns ""
		every { inputHandler.promptForYesNo("Do you want to edit tasks within this project?") } returns false
		coEvery {
			editProjectUseCase.editProject(match {
				it.id == project.id &&
						it.title == project.title &&
						it.description == project.description
			})
		} just Runs
		
		// When
		projectEditView.editProject()
		
		// Then
		coVerify {
			editProjectUseCase.editProject(match {
				it.title == project.title &&
						it.description == project.description
			})
		}
		verify { outputFormatter.printSuccess("Project updated successfully!") }
	}
	@Disabled
	@Test
	fun `should update project and navigate to edit tasks when requested`() = runTest {
		// Given
		val projects = getProjects(2)
		val selectedProject = projects[0]
		val newTitle = "Updated Title"
		
		coEvery { getAllProjectsUseCase.getAllProjects() } returns projects
		every { inputHandler.promptForIntChoice("Select the project number to edit: ", 1..2) } returns 1
		every { inputHandler.promptForYesNo("Do you want to edit basic project information?") } returns true
		every { inputHandler.promptForInput("Enter new project title (leave empty to keep current): ") } returns newTitle
		every {
			inputHandler.promptForInput("Enter new project description (leave empty to keep current): ")
		} returns ""
		every { inputHandler.promptForYesNo("Do you want to edit tasks within this project?") } returns true
		every { editTaskView.editTask() } just Runs
		coEvery { editProjectUseCase.editProject(any()) } just Runs
		
		// When
		projectEditView.editProject()
		
		// Then
		verify { editTaskView.editTask() }
		coVerify {
			editProjectUseCase.editProject(match {
				it.id == selectedProject.id &&
						it.title == newTitle &&
						it.description == selectedProject.description
			})
		}
	}
	@Disabled
	@Test
	fun `should not edit basic info when user declines`() = runTest {
		// Given
		val projects = getProjects(1)
		val project = projects[0]
		
		coEvery { getAllProjectsUseCase.getAllProjects() } returns projects
		every { inputHandler.promptForIntChoice("Select the project number to edit: ", 1..1) } returns 1
		every { inputHandler.promptForYesNo("Do you want to edit basic project information?") } returns false
		coEvery { editProjectUseCase.editProject(any()) } just Runs
		
		// When
		projectEditView.editProject()
		
		// Then
		verify(exactly = 0) { inputHandler.promptForInput(any()) }
		verify(exactly = 0) { inputHandler.promptForYesNo("Do you want to edit tasks within this project?") }
		coVerify {
			editProjectUseCase.editProject(match {
				it.id == project.id &&
						it.title == project.title &&
						it.description == project.description
			})
		}
	}
	@Disabled
	@Test
	fun `should handle exception when updating project`() = runTest {
		// Given
		val errorMessage = "Update failed due to database constraint"
		val projects = getProjects(1)
		
		coEvery { getAllProjectsUseCase.getAllProjects() } returns projects
		every { inputHandler.promptForIntChoice("Select the project number to edit: ", 1..1) } returns 1
		every { inputHandler.promptForYesNo("Do you want to edit basic project information?") } returns false
		coEvery { editProjectUseCase.editProject(any()) } throws Exception(errorMessage)
		
		// When
		projectEditView.editProject()
		
		// Then
		verify { outputFormatter.printError("Failed to update project: $errorMessage") }
	}
}