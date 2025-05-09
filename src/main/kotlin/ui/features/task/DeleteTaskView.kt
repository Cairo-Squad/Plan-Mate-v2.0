package ui.features.task

import kotlinx.coroutines.runBlocking
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
    fun deleteTask() = runBlocking {
        outputFormatter.printHeader(
            """
            ╔══════════════════════════════╗
            ║ 🗑️  Delete Task Management      ║
            ╚══════════════════════════════╝
            """.trimIndent()
        )

        val projects = getAllProjectsUseCase.getAllProjects()

        if (projects.isEmpty()) {
            outputFormatter.printError("❌ No projects available for task deletion!")
	        return@runBlocking
        }

        outputFormatter.printInfo("📂 Available Projects:")
        projects.forEachIndexed { index, project ->
            outputFormatter.printInfo("📌 ${index + 1}. ${project.title} | 🆔 ID: ${project.id}")
        }

        val projectIndex = inputHandler.promptForIntChoice(
            "🔹 Choose a project to delete its task:", 1..projects.size
        ) - 1

        val selectedProject = projects[projectIndex]

        val tasks = getAllTasksByProjectIdUseCase.getAllTasksByProjectId(selectedProject.id)

        if (tasks.isEmpty()) {
            outputFormatter.printWarning("⚠️ No tasks found for project '${selectedProject.title}'.")
	        return@runBlocking
        }

        outputFormatter.printInfo("📜 Available Tasks:")
        tasks.forEachIndexed { index, task ->
            outputFormatter.printInfo("✅ ${index + 1}. ${task.title} | 🆔 ID: ${task.id} | 🏷️ Status: ${task.state.title}")
        }

        val taskIndex = inputHandler.promptForIntChoice("🔹 Select a task to delete:", 1..tasks.size) - 1
        val selectedTask = tasks[taskIndex]

        outputFormatter.printWarning("⚠️ Are you sure you want to delete '${selectedTask.title}'? This action cannot be undone.")
        val confirmation = inputHandler.promptForInput("Type 'YES' to confirm: ")

        if (confirmation.equals("YES", ignoreCase = true)) {
            deleteTaskUseCase.deleteTask(selectedTask)
            outputFormatter.printSuccess("✅ Task '${selectedTask.title}' deleted successfully!")
        } else {
            outputFormatter.printInfo("🔄 Action canceled. No task was deleted.")
        }

        inputHandler.waitForEnter()
    }
}
