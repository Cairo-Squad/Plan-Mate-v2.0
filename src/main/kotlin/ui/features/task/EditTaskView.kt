package ui.features.task

import kotlinx.coroutines.runBlocking
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
    fun editTask() = runBlocking {
        outputFormatter.printHeader(
            """
            ╔══════════════════════════╗
            ║ ✏️ Edit Task Information  ║
            ╚══════════════════════════╝
            """.trimIndent()
        )

        val projects = getAllProjectsUseCase.getAllProjects()

        if (projects.isEmpty()) {
            outputFormatter.printError("❌ No projects available for task editing!")
	        return@runBlocking
        }

        outputFormatter.printInfo("📂 Available Projects:")
        projects.forEachIndexed { index, project ->
            outputFormatter.printInfo("📌 ${index + 1}. ${project.title} | 🆔 ID: ${project.id}")
        }

        val projectIndex = inputHandler.promptForIntChoice(
            "🔹 Choose a project to edit its task:", 1..projects.size
        ) - 1

        val selectedProject = projects[projectIndex]

        val tasks = getAllTasksByProjectIdUseCase.getAllTasksByProjectId(selectedProject.id)

        if (tasks.isEmpty()) {
            outputFormatter.printWarning("⚠️ No tasks found for project '${selectedProject.title}'.")
	        return@runBlocking
        }

        outputFormatter.printInfo("📝 Available Tasks:")
        tasks.forEachIndexed { index, task ->
            outputFormatter.printInfo("✅ ${index + 1}. ${task.title} | 🏷️ Status: ${task.state.title}")
        }

        val taskIndex = inputHandler.promptForIntChoice("🔹 Select a task to edit:", 1..tasks.size) - 1

        val selectedTask = tasks[taskIndex]

        val newTitle = inputHandler.promptForInput("✏️ Enter new title (leave empty to keep current): ")
            .takeIf { it.isNotBlank() } ?: selectedTask.title

        val newDescription = inputHandler.promptForInput("📝 Enter new description (leave empty to keep current): ")
            .takeIf { it.isNotBlank() } ?: selectedTask.description

        val newStateTitle = inputHandler.promptForInput("🚀 Enter new state (leave empty to keep current): ")
            .takeIf { it.isNotBlank() } ?: selectedTask.state.title

        val newState = State(selectedTask.state.id, newStateTitle)

        editStateUseCase.editState(newState, selectedTask.state)

        val updatedTask = selectedTask.copy(title = newTitle, description = newDescription, state = newState)

        editTaskUseCase.editTask(updatedTask, selectedTask)
        outputFormatter.printSuccess("✅ Task '${updatedTask.title}' updated successfully!")

        inputHandler.waitForEnter()
    }
}
