package ui.features.task

import logic.model.Task
import logic.usecase.task.DeleteTaskUseCase
import logic.usecase.task.GetAllTasksByProjectIdUseCase
import ui.utils.InputHandler
import ui.utils.OutputFormatter
import java.util.UUID

class DeleteTaskView(
    private val getAllTasksByProjectIdUseCase: GetAllTasksByProjectIdUseCase,
    private val deleteTaskUseCase: DeleteTaskUseCase,
    private val inputHandler: InputHandler,
    private val outputFormatter: OutputFormatter
) {
    fun deleteTask() {
        outputFormatter.printHeader("Delete Task from a Specific Project")

        val projectId = UUID.fromString(inputHandler.promptForInput("Enter Project ID: "))

        val tasks = getAllTasksByProjectIdUseCase.execute(projectId).getOrElse {
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
