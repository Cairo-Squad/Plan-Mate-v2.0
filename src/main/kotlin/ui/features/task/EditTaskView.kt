package ui.features.task

import logic.model.Task
import logic.usecase.task.EditTaskUseCase
import logic.usecase.task.GetAllTasksByProjectIdUseCase
import ui.utils.InputHandler
import ui.utils.OutputFormatter
import java.util.UUID

class EditTaskView(
    private val editTaskUseCase: EditTaskUseCase,
    private val getAllTasksByProjectIdUseCase: GetAllTasksByProjectIdUseCase,
    private val inputHandler: InputHandler,
    private val outputFormatter: OutputFormatter
) {
    fun editTask() {
        outputFormatter.printHeader("Edit Task")

        val projectId = UUID.fromString(inputHandler.promptForInput("Enter Project ID: "))
        val tasks = getAllTasksByProjectIdUseCase.execute(projectId).getOrElse {
            outputFormatter.printError("Failed to retrieve tasks for project.")
            return
        }

        if (tasks.isEmpty()) {
            outputFormatter.printError("No tasks available to edit for this project.")
            return
        }


        outputFormatter.printHeader("Available Tasks in Project:")
        tasks.forEachIndexed { index, task ->
            outputFormatter.printInfo("${index + 1}. ${task.title} (ID: ${task.id})")
        }

        val taskIndex = inputHandler.promptForIntChoice("Select the task number to edit: ", 1..tasks.size)
        val selectedTask = tasks[taskIndex - 1]


        val newTitle = inputHandler.promptForInput("Enter new task title (leave empty to keep current): ")
            .takeIf { it.isNotBlank() } ?: selectedTask.title
        val newDescription = inputHandler.promptForInput("Enter new task description (leave empty to keep current): ")
            .takeIf { it.isNotBlank() } ?: selectedTask.description

        val updatedTask = selectedTask.copy(title = newTitle, description = newDescription)

        // Execute task update
        try {
            editTaskUseCase.invoke(updatedTask, selectedTask)
            outputFormatter.printSuccess("Task edited successfully!")
        } catch (exception: Exception) {
            outputFormatter.printError("Failed to edit task: ${exception.message}")
        }

    }
}
