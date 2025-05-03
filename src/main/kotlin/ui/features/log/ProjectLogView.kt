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
        outputFormatter.printHeader("Project Audit Logs")

        // Fetch all projects
        val projects = getAllProjectsUseCase.getAllProjects().getOrElse {
            outputFormatter.printError("Failed to retrieve projects.")
            return
        }

        // Display projects in a numbered list
        if (projects.isEmpty()) {
            outputFormatter.printError("No projects available to view logs.")
            return
        }

        outputFormatter.printHeader("Available Projects:")
        projects.forEachIndexed { index, project ->
            outputFormatter.printInfo("${index + 1}. ${project.title} (ID: ${project.id})")
        }

        // Let the user choose a project to view logs
        val projectIndex = inputHandler.promptForIntChoice("Select the project number to view logs: ", 1..projects.size)
        val selectedProject = projects[projectIndex - 1]

        val logs = getProjectLogUseCase.getProjectLog(selectedProject.id)

        if (logs.isEmpty()) {
            outputFormatter.printError("No logs found for this project.")
            return
        }

        outputFormatter.printHeader("Project Logs:")
        logs.forEach { log ->
            outputFormatter.printInfo("""
                Log ID: ${log.id}
                Entity: ${log.entityTitle} (${log.entityType})
                Action: ${log.userAction}
                User ID: ${log.userId}
                Timestamp: ${log.dateTime}
            """.trimIndent())
        }

        inputHandler.waitForEnter()
    }
}
