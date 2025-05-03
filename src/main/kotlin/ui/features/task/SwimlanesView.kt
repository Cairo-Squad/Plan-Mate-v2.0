package ui.features.task

import logic.usecase.project.GetAllProjectsUseCase
import logic.usecase.task.GetAllTasksByProjectIdUseCase
import ui.utils.InputHandler
import ui.utils.OutputFormatter


class SwimlanesView(
	private val inputHandler: InputHandler,
	private val outputFormatter: OutputFormatter,
	private val getAllTasksByProjectIdUseCase: GetAllTasksByProjectIdUseCase,
	private val getAllProjectUseCase: GetAllProjectsUseCase
){
	fun getAllTasksByProject() {
		getAllProjectUseCase.getAllProjects().onSuccess { projects ->
			if (projects.isEmpty()) {
				outputFormatter.printError("No projects available.")
				return@onSuccess
			}
			
			projects.forEachIndexed { index, project ->
				outputFormatter.printInfo("${index + 1}. ${project.title} (ID: ${project.id})")
			}
			
			val projectIndex = inputHandler.promptForIntChoice(
				"Choose the number of project you want to view its tasks:",
				1..projects.size
			) - 1
			
			val selectedProject = projects[projectIndex]
			
			getAllTasksByProjectIdUseCase.execute(selectedProject.id).onSuccess { tasks ->
				if (tasks.isEmpty()) {
					outputFormatter.printInfo("No tasks found for project '${selectedProject.title}'.")
				} else {
					outputFormatter.printHeader("Tasks in project: ${selectedProject.title}")
					tasks.forEach { task ->
						outputFormatter.printInfo("- ${task.title} (${task.state.title})")
					}
				}
			}.onFailure { error ->
				outputFormatter.printError("Failed to load tasks: ${error.message}")
			}
		}.onFailure {
			outputFormatter.printError("Failed to load projects: ${it.message}")
		}
	}
	
}