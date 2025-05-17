package ui.features.log

import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import logic.model.Log
import logic.model.Project
import logic.model.Task
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
    fun viewTaskLogs() = runBlocking {
        displayHeader()

        val projects = fetchProjects() ?: return@runBlocking
        val selectedProject = selectProject(projects) ?: return@runBlocking

        val tasks = fetchTasks(selectedProject) ?: return@runBlocking
        val selectedTask = selectTask(tasks) ?: return@runBlocking

        val logs = fetchLogs(selectedTask) ?: return@runBlocking

        displayLogs(selectedTask, logs)

        inputHandler.waitForEnter()
    }

    private fun displayHeader() {
        outputFormatter.printHeader(
            """
            ╔════════════════════════════════╗
            ║ 📜 Task Audit Logs Viewer 🛠️  ║
            ╚════════════════════════════════╝
            """.trimIndent()
        )
    }


    private suspend fun fetchProjects(): List<Project>? = withContext(Dispatchers.IO) {
        val projects = getAllProjectsUseCase.getAllProjects()

        if (projects.isEmpty()) {
            outputFormatter.printError("❌ No projects available for task logs!")
            return@withContext null
        }

        outputFormatter.printInfo("📂 Available Projects:")
        projects.forEachIndexed { index, project ->
            outputFormatter.printInfo("📌 ${index + 1}. ${project.title} | 🆔 ID: ${project.id}")
        }

        return@withContext projects
    }


    private suspend fun selectProject(projects: List<Project>): Project? = withContext(Dispatchers.IO) {
        val projectIndex =
            inputHandler.promptForIntChoice("🔹 Select a project to view task logs:", 1..projects.size) - 1
        return@withContext projects.getOrNull(projectIndex)
    }


    private suspend fun fetchTasks(project: Project): List<Task>? = withContext(Dispatchers.IO) {
        val tasks = getAllTasksByProjectIdUseCase.getAllTasksByProjectId(project.id!!)

        if (tasks.isEmpty()) {
            outputFormatter.printWarning("⚠️ No tasks found for project '${project.title}'.")
            return@withContext null
        }

        outputFormatter.printInfo("📝 Available Tasks:")
        tasks.forEachIndexed { index, task ->
            outputFormatter.printInfo("✅ ${index + 1}. ${task.title} | 🏷️ Status: ${task.state?.title} | 🆔 ID: ${task.id}")
        }

        return@withContext tasks
    }


    private suspend fun selectTask(tasks: List<Task>): Task? = withContext(Dispatchers.IO) {
        val taskIndex = inputHandler.promptForIntChoice("🔹 Select a task to view logs:", 1..tasks.size) - 1
        return@withContext tasks.getOrNull(taskIndex)
    }


    private suspend fun fetchLogs(task: Task): List<Log>? = withContext(Dispatchers.IO) {
        val logs = getTaskLogsUseCase.getTaskLogs(task.id ?: UUID.randomUUID())

        if (logs.isEmpty()) {
            outputFormatter.printError("❌ No logs found for this task.")
            return@withContext null
        }

        return@withContext logs
    }

    private fun displayLogs(task: Task, logs: List<Log>) {
        outputFormatter.printHeader("📜 Task Logs for '${task.title}':")
        logs.forEach { log ->
            outputFormatter.printInfo(
                """
                🔹 Log ID: ${log.id}
                📌 Entity: ${log.entityTitle} (${log.entityType})
                🔖 Entity ID: ${log.entityId} 
                ✏️ Action: ${log.userAction}
                👤 User ID: ${log.userId}
                ⏳ Timestamp: ${log.dateTime}
            """.trimIndent()
            )
            println("--------------------------------------------------------------------------")
        }
    }
}
