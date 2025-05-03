package ui.features.task

import logic.model.State
import logic.usecase.project.GetAllProjectsUseCase
import logic.usecase.state.EditStateUseCase
import logic.usecase.task.EditTaskUseCase
import logic.usecase.task.GetAllTasksByProjectIdUseCase
import ui.utils.InputHandler
import ui.utils.OutputFormatter

class EditTaskView(
	private val editTaskUseCase: EditTaskUseCase,
	private val getAllTasksByProjectIdUseCase: GetAllTasksByProjectIdUseCase,
	private val inputHandler: InputHandler,
	private val outputFormatter: OutputFormatter,
	private val getAllProjectsUseCase: GetAllProjectsUseCase,
	private val editStateUseCase: EditStateUseCase,
) {
	fun editTask() {
		outputFormatter.printHeader("Edit Task")
		
		getAllProjectsUseCase.getAllProjects().onSuccess { projects ->
			projects.forEachIndexed { index, project ->
				outputFormatter.printInfo("${index + 1}. ${project.title} (ID: ${project.id})")
			}
			val projectIndex = inputHandler.promptForIntChoice(
				"please choose the project you want to edit its task",
				1..projects.size
			) - 1
			
			val tasks = getAllTasksByProjectIdUseCase.execute(projects[projectIndex].id).getOrElse {
				outputFormatter.printError("Failed to retrieve tasks for project.")
				return
			}
			
			tasks.forEachIndexed { index2, task ->
				outputFormatter.printInfo("${index2 + 1}. ${task.title} (ID: ${task.id})")
			}
			
			val taskIndex = inputHandler.promptForIntChoice(
				"please choose the task you want to edit: ",
				1..tasks.size
			) - 1
			val newTitle = inputHandler.promptForInput("Enter new task title (leave empty to keep current): ")
				.takeIf { it.isNotBlank() } ?: tasks[taskIndex].title
			val newDescription =
				inputHandler.promptForInput("Enter new task description (leave empty to keep current): ")
					.takeIf { it.isNotBlank() } ?: tasks[taskIndex].description
			
			val newStateTitle = inputHandler.promptForInput("Enter new task state (leave empty to keep current): ")
				.takeIf { it.isNotBlank() } ?: tasks[taskIndex].state.title
			
			val newState = State(tasks[taskIndex].state.id, newStateTitle)
			
			editStateUseCase.editState(newState, tasks[taskIndex].state)
			
			val updatedTask = tasks[taskIndex].copy(title = newTitle, description = newDescription)
			
			try {
				editTaskUseCase.invoke(updatedTask, tasks[taskIndex])
				outputFormatter.printSuccess("Task edited successfully!")
			} catch (exception: Exception) {
				outputFormatter.printError("Failed to edit task: ${exception.message}")
			}
		}
	}
}
