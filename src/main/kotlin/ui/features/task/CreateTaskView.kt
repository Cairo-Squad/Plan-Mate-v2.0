package ui.features.task

import logic.model.State
import logic.model.Task
import logic.usecase.project.EditProjectUseCase
import logic.usecase.project.GetAllProjectsUseCase
import logic.usecase.state.CreateStateUseCase
import logic.usecase.task.CreateTaskUseCase
import ui.features.auth.UserSession
import ui.utils.InputHandler
import ui.utils.OutputFormatter
import java.util.*

class CreateTaskView(
	private val createTaskUseCase: CreateTaskUseCase,
	private val inputHandler: InputHandler,
	private val outputFormatter: OutputFormatter,
	private val editProjectUseCase: EditProjectUseCase,
	private val getAllProjectsUseCase: GetAllProjectsUseCase,
	private val createStateUseCase: CreateStateUseCase
) {
	fun createTask() {
		outputFormatter.printHeader("Create a New Task")
		
		val title = inputHandler.promptForInput("Enter task title: ")
		val description = inputHandler.promptForInput("Enter task description: ")
		
		getAllProjectsUseCase.getAllProjects().onSuccess { projects ->
			if (projects.isEmpty()) {
				outputFormatter.printError("No projects available. Please create a project first.")
				return@onSuccess
			}
			
			projects.forEachIndexed { index, project ->
				outputFormatter.printInfo("${index + 1}. ${project.title} (ID: ${project.id})")
			}
			
			val projectIndex = inputHandler.promptForIntChoice("Select the number of project: ", 1..projects.size) - 1
			val selectedProject = projects[projectIndex]
			
			val taskState = State(UUID.randomUUID(), "TODO")
			createStateUseCase.createState(taskState, UserSession.getUser()!!)
			
			val task = Task(
				id = UUID.randomUUID(),
				title = title,
				description = description,
				state = taskState,
				projectId = selectedProject.id
			)
			
			val result = createTaskUseCase.createTask(task)
			result.fold(
				onSuccess = {
					val updatedProject = selectedProject.copy(
						tasks = selectedProject.tasks + task
					)
					
					editProjectUseCase.editProject(updatedProject)
					outputFormatter.printSuccess("Task created successfully!")
					
					
				},
				onFailure = { error -> outputFormatter.printError("Failed to create task: ${error.message}") }
			)
		}
	}
}
