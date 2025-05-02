package ui.features.task

import logic.model.State
import logic.model.Task
import logic.usecase.task.CreateTaskUseCase
import ui.utils.InputHandler
import ui.utils.OutputFormatter
import java.util.UUID

class CreateTaskView(
    private val createTaskUseCase: CreateTaskUseCase,
    private val inputHandler: InputHandler,
    private val outputFormatter: OutputFormatter
) {
    fun createTask() {
        outputFormatter.printHeader("Create a New Task")

        val title = inputHandler.promptForInput("Enter task title: ")
        val description = inputHandler.promptForInput("Enter task description: ")
        val projectId = UUID.fromString(inputHandler.promptForInput("Enter project ID: "))

        val task = Task(UUID.randomUUID(), title, description, State(id = UUID.randomUUID() , "TODO" ) , projectId)

        val result = createTaskUseCase.createTask(task)
        result.fold(
            { outputFormatter.printSuccess("Task created successfully!") },
            { error -> outputFormatter.printError("Failed to create task: ${error.message}") }
        )
    }
}
