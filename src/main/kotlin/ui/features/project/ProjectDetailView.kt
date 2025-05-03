package ui.features.project

import logic.usecase.project.GetAllProjectsUseCase
import ui.utils.InputHandler
import ui.utils.OutputFormatter

class ProjectDetailView(
	private val getAllProjectsUseCase: GetAllProjectsUseCase,
	private val inputHandler: InputHandler,
	private val outputFormatter: OutputFormatter
) {
	fun viewProjectDetails() {
		outputFormatter.printHeader("Project Details")
  
		val projects = getAllProjectsUseCase.getAllProjects().getOrElse {
			outputFormatter.printError("Failed to retrieve projects.")
			return
		}
		
		if (projects.isEmpty()) {
			outputFormatter.printError("No projects available to view.")
			return
		}
		
		outputFormatter.printHeader("Available Projects:")
		projects.forEachIndexed { index, project ->
			outputFormatter.printInfo("${index + 1}. ${project.title} (ID: ${project.id})")
		}
		
		val userContinue = inputHandler.promptForYesNo("Do you want to see a specific project details?")
		
		if (userContinue) {
			val projectIndex =
				inputHandler.promptForIntChoice("Select the project number to view details: ", 1..projects.size)
			val selectedProject = projects[projectIndex - 1]
			
			outputFormatter.printHeader("Project Information")
			outputFormatter.printInfo("Project ID: ${selectedProject.id}")
			outputFormatter.printInfo("Title: ${selectedProject.title}")
			outputFormatter.printInfo("Description: ${selectedProject.description}")
			outputFormatter.printInfo("State: ${selectedProject.state.title}")
			
			inputHandler.waitForEnter()
		}
	}
}
