package ui.features.log

import logic.model.Log
import logic.usecase.Log.GetProjectLogUseCase
import logic.usecase.project.GetAllProjectsUseCase
import ui.utils.InputHandler
import ui.utils.OutputFormatter

class ProjectLogView(
    private val getAllProjectsUseCase: GetAllProjectsUseCase,
    private val getProjectLogUseCase: GetProjectLogUseCase,
    private val inputHandler: InputHandler,
    private val outputFormatter: OutputFormatter
) {
    fun viewProjectLogs() {
        outputFormatter.printHeader(
            """
            ╔════════════════════════════════╗
            ║ 📜 Project Audit Logs Viewer 🏗️  ║
            ╚════════════════════════════════╝
            """.trimIndent()
        )

        val projects = getAllProjectsUseCase.getAllProjects().getOrNull()

        if (projects.isNullOrEmpty()) {
            outputFormatter.printError("❌ No projects available for log viewing!")
            return
        }

        outputFormatter.printInfo("📂 Available Projects:")
        projects.forEachIndexed { index, project ->
            outputFormatter.printInfo("📌 ${index + 1}. ${project.title} | 🆔 ID: ${project.id}")
        }

        val projectIndex = inputHandler.promptForIntChoice("🔹 Select a project to view logs:", 1..projects.size) - 1
        val selectedProject = projects[projectIndex]

        val logs = getProjectLogUseCase.getProjectLog(selectedProject.id)

        if (logs.isEmpty()) {
            outputFormatter.printWarning("⚠️ No logs found for project '${selectedProject.title}'.")
            return
        }

        outputFormatter.printHeader("📜 Logs for Project: '${selectedProject.title}'")
        logs.forEach { log ->
            outputFormatter.printInfo("""
                🔹 Log ID: ${log.id}
                📌 Entity: ${log.entityTitle} (${log.entityType})
                ✏️ Action: ${log.userAction}
                👤 User ID: ${log.userId}
                ⏳ Timestamp: ${log.dateTime}
            """.trimIndent())
        }
        println("-------------------------------------------------------------------------------")

        inputHandler.waitForEnter()
    }
}
