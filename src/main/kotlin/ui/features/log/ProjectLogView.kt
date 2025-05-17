package ui.features.log

import kotlinx.coroutines.runBlocking
import logic.model.Log
import logic.model.Project
import logic.usecase.log.GetProjectLogsUseCase
import logic.usecase.project.GetAllProjectsUseCase
import ui.utils.InputHandler
import ui.utils.OutputFormatter

class ProjectLogView(
    private val getAllProjectsUseCase: GetAllProjectsUseCase,
    private val getProjectLogsUseCase: GetProjectLogsUseCase,
    private val inputHandler: InputHandler,
    private val outputFormatter: OutputFormatter
) {
    fun viewProjectLogs() = runBlocking {
        displayHeader()

        val projects = fetchProjects() ?: return@runBlocking

        val selectedProject = selectProject(projects) ?: return@runBlocking

        val logs = fetchLogs(selectedProject) ?: return@runBlocking

        displayLogs(selectedProject, logs)

        inputHandler.waitForEnter()
    }

    private fun displayHeader() {
        outputFormatter.printHeader(
            """
            ╔════════════════════════════════╗
            ║ 📜 Project Audit Logs Viewer 🏗️  ║
            ╚════════════════════════════════╝
            """.trimIndent()
        )
    }

    suspend private fun fetchProjects(): List<Project>? {
        val projects = getAllProjectsUseCase.getAllProjects()

        if (projects.isEmpty()) {
            outputFormatter.printError("❌ No projects available for log viewing!")
            return null
        }

        outputFormatter.printInfo("📂 Available Projects:")
        projects.forEachIndexed { index, project ->
            outputFormatter.printInfo("📌 ${index + 1}. ${project.title} | 🆔 ID: ${project.id}")
        }

        return projects
    }


    private fun selectProject(projects: List<Project>): Project? {
        val projectIndex = inputHandler.promptForIntChoice("🔹 Select a project to view logs:", 1..projects.size) - 1
        return projects.getOrNull(projectIndex)
    }

    private suspend fun fetchLogs(project: Project): List<Log>? {
        val logs = getProjectLogsUseCase.getProjectLogs(project.id!!)

        if (logs.isEmpty()) {
            outputFormatter.printWarning("⚠️ No logs found for project '${project.title}'.")
            return null
        }

        return logs
    }

    private fun displayLogs(project: Project, logs: List<Log>) {
        outputFormatter.printHeader("📜 Logs for Project: '${project.title}'")
        logs.forEach { log ->
            outputFormatter.printInfo(
                """
                🔹 Log ID: ${log.id}
                📌 Entity: ${log.entityTitle} (${log.entityType})
                ✏️ Action: ${log.userAction}
                👤 User ID: ${log.userId}
                ⏳ Timestamp: ${log.dateTime}
            """.trimIndent()
            )
        }
        println("-------------------------------------------------------------------------------")
    }
}
