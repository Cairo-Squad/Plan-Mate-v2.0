package ui.features.log

import logic.usecase.Log.GetProjectLogUseCase
import ui.utils.InputHandler
import ui.utils.OutputFormatter
import java.util.UUID

class ProjectLogView(
    private val getProjectLogUseCase: GetProjectLogUseCase,
    private val inputHandler: InputHandler,
    private val outputFormatter: OutputFormatter
) {
    fun viewProjectLogs() {
        outputFormatter.printHeader("Project Audit Logs")
        //iterate over all projects
        val projectId = UUID.fromString(inputHandler.promptForInput("Enter Project ID to view logs: "))
        val logs = getProjectLogUseCase.getProjectLog(projectId)

        if (logs.isEmpty()) {
            outputFormatter.printError("No logs found for this project.")
            return
        }



        inputHandler.waitForEnter()
    }
}
