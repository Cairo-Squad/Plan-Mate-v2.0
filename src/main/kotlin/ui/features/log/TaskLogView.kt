package ui.features.log


import logic.usecase.Log.GetTaskLogsUseCase
import logic.usecase.project.GetAllProjectsUseCase
import logic.usecase.task.GetAllTasksByProjectIdUseCase
import ui.utils.InputHandler
import ui.utils.OutputFormatter

class TaskLogView(
    private val getAllProjectsUseCase: GetAllProjectsUseCase,
    private val getAllTasksByProjectIdUseCase: GetAllTasksByProjectIdUseCase,
    private val getTaskLogsUseCase: GetTaskLogsUseCase,
    private val inputHandler: InputHandler,
    private val outputFormatter: OutputFormatter
) {
    fun viewTaskLogs() {
        outputFormatter.printHeader("Task Audit Logs")

        // Fetch all projects
        val projects = getAllProjectsUseCase.getAllProjects().getOrElse {
            outputFormatter.printError("Failed to retrieve projects.")
            return
        }


        if (projects.isEmpty()) {
            outputFormatter.printError("No projects available to view task logs.")
            return
        }

        outputFormatter.printHeader("Available Projects:")
        projects.forEachIndexed { index, project ->
            outputFormatter.printInfo("${index + 1}. ${project.title} (ID: ${project.id})")
        }

        // Let the user choose a project
        val projectIndex = inputHandler.promptForIntChoice("Select the project number to view its task logs: ", 1..projects.size)
        val selectedProject = projects[projectIndex - 1]

        // Fetch tasks for the selected project
        val tasks = getAllTasksByProjectIdUseCase.execute(selectedProject.id).getOrElse {
            outputFormatter.printError("Failed to retrieve tasks for the selected project.")
            return
        }

        // Display tasks in a numbered list
        if (tasks.isEmpty()) {
            outputFormatter.printError("No tasks available in this project.")
            return
        }

        outputFormatter.printHeader("Available Tasks in Project '${selectedProject.title}':")
        tasks.forEachIndexed { index, task ->
            outputFormatter.printInfo("${index + 1}. ${task.title} (ID: ${task.id})")
        }


        val taskIndex = inputHandler.promptForIntChoice("Select the task number to view logs: ", 1..tasks.size)
        val selectedTask = tasks[taskIndex - 1]


        val logs = getTaskLogsUseCase.execute(selectedTask.id)

        if (logs.isEmpty()) {
            outputFormatter.printError("No logs found for this task.")
            return
        }

        outputFormatter.printHeader("Task Logs for '${selectedTask.title}':")
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
