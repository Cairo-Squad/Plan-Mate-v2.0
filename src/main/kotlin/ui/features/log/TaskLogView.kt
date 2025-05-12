package ui.features.log


import kotlinx.coroutines.runBlocking
import logic.usecase.log.GetTaskLogsUseCase
import logic.usecase.project.GetAllProjectsUseCase
import logic.usecase.task.GetAllTasksByProjectIdUseCase
import ui.utils.InputHandler
import ui.utils.OutputFormatter
import java.util.*

class TaskLogView(
    private val getAllProjectsUseCase: GetAllProjectsUseCase,
    private val getAllTasksByProjectIdUseCase: GetAllTasksByProjectIdUseCase,
    private val getTaskLogsUseCase: GetTaskLogsUseCase,
    private val inputHandler: InputHandler,
    private val outputFormatter: OutputFormatter
) {
    fun viewTaskLogs() = runBlocking{
        outputFormatter.printHeader(
            """
            ╔════════════════════════════════╗
            ║ 📜 Task Audit Logs Viewer 🛠️  ║
            ╚════════════════════════════════╝
            """.trimIndent()
        )

        val projects = getAllProjectsUseCase.getAllProjects()

        if (projects.isEmpty()) {
            outputFormatter.printError("❌ No projects available for task logs!")
	        return@runBlocking
        }

        outputFormatter.printInfo("📂 Available Projects:")
        projects.forEachIndexed { index, project ->
            outputFormatter.printInfo("📌 ${index + 1}. ${project.title} | 🆔 ID: ${project.id}")
        }

        val projectIndex = inputHandler.promptForIntChoice("🔹 Select a project to view task logs:", 1..projects.size) - 1
        val selectedProject = projects[projectIndex]

        val tasks = getAllTasksByProjectIdUseCase.getAllTasksByProjectId(selectedProject.id!!)

        if (tasks.isEmpty()) {
            outputFormatter.printWarning("⚠️ No tasks found for project '${selectedProject.title}'.")
	        return@runBlocking
        }

        outputFormatter.printInfo("📝 Available Tasks:")
        tasks.forEachIndexed { index, task ->
            outputFormatter.printInfo("✅ ${index + 1}. ${task.title} | 🏷️ Status: ${task.state?.title} | 🆔 ID: ${task.id}")
        }

        val taskIndex = inputHandler.promptForIntChoice("🔹 Select a task to view logs:", 1..tasks.size) - 1
        val selectedTask = tasks[taskIndex]

        val logs = getTaskLogsUseCase.execute(selectedTask.id?: UUID.randomUUID())

        if (logs.isEmpty()) {
            outputFormatter.printError("❌ No logs found for this task.")
	        return@runBlocking
        }

        outputFormatter.printHeader("📜 Task Logs for '${selectedTask.title}':")
        logs.forEach { log ->
            outputFormatter.printInfo("""
                🔹 Log ID: ${log.id}
                📌 Entity: ${log.entityTitle} (${log.entityType})
                ✏️ Action: ${log.userAction}
                👤 User ID: ${log.userId}
                ⏳ Timestamp: ${log.dateTime}
            """.trimIndent())
            println("--------------------------------------------------------------------------")
        }

        inputHandler.waitForEnter()
    }
}
