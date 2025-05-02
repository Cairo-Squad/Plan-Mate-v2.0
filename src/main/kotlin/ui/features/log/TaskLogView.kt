package ui.features.log

import logic.usecase.Log.GetTaskLogsUseCase
import ui.utils.InputHandler
import ui.utils.OutputFormatter
import java.util.UUID

class TaskLogView(
    private val getTaskLogsUseCase: GetTaskLogsUseCase,
    private val inputHandler: InputHandler,
    private val outputFormatter: OutputFormatter
) {
    fun viewTaskLogs() {
        outputFormatter.printHeader("Task Audit Logs")
            //iterate over all tasks

        val taskId = UUID.fromString(inputHandler.promptForInput("Enter Task ID to view logs: "))
        val logs = getTaskLogsUseCase.execute(taskId)

        if (logs.isEmpty()) {
            outputFormatter.printError("No logs found for this task.")
            return
        }


        inputHandler.waitForEnter()
    }
}
