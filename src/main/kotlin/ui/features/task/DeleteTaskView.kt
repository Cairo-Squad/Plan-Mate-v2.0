package ui.features.task

import logic.usecase.project.GetAllProjectsUseCase
import logic.usecase.task.DeleteTaskUseCase
import logic.usecase.task.GetAllTasksByProjectIdUseCase
import ui.utils.InputHandler
import ui.utils.OutputFormatter

class DeleteTaskView(
    private val getAllTasksByProjectIdUseCase: GetAllTasksByProjectIdUseCase,
    private val deleteTaskUseCase: DeleteTaskUseCase,
    private val inputHandler: InputHandler,
    private val outputFormatter: OutputFormatter,
    private val getAllProjectsUseCase: GetAllProjectsUseCase
) {
    fun deleteTask() {
        
        var projectIndex = 0
        outputFormatter.printHeader("Delete Task from a Specific Project")


        getAllProjectsUseCase.getAllProjects().onSuccess { projects ->
            projects.forEachIndexed { index, project ->
                outputFormatter.printInfo("${index + 1}. ${project.title} (ID: ${project.id})")
            }
            
            projectIndex = inputHandler.promptForIntChoice(
                "please choose the project you want to edit its task",
                1..projects.size
            ) - 1
            
            val tasks = getAllTasksByProjectIdUseCase.execute(projects[projectIndex].id).getOrElse {
                outputFormatter.printError("Failed to retrieve tasks for project.")
                return
            }
            
            
            if (tasks.isEmpty()) {
                outputFormatter.printError("No tasks available to delete for this project.")
                return
            }
            
            outputFormatter.printHeader("Available Tasks in Project:")
            tasks.forEachIndexed { index, task ->
                outputFormatter.printInfo("${index + 1}. ${task.title} (ID: ${task.id})")
            }
            
            val taskIndex = inputHandler.promptForIntChoice("Select the task number to delete: ", 1..tasks.size)
            val selectedTask = tasks[taskIndex - 1]
            
            try {
                deleteTaskUseCase.execute(selectedTask)
                outputFormatter.printSuccess("Task deleted successfully!")
            } catch (exception: Exception) {
                outputFormatter.printError("Failed to delete task: ${exception.message}")
            }
        }
    }
}
