package ui.features.task

import logic.model.Task
import logic.model.User

import ui.utils.InputHandler
import ui.utils.OutputFormatter

class TaskManagementView(
    private val creationView:CreateTaskView,
    private val editView:EditTaskView,
    private val deletionView: DeleteTaskView,
    private val swimlanesView: SwimlanesView,
    private val inputHandler: InputHandler,
    private val outputFormatter: OutputFormatter
) {
    fun showTaskMenu(user: User) {
        while (true) {
            outputFormatter.printHeader("Task Management")
            outputFormatter.printMenu(listOf(
                "1. View Project Tasks (Swimlanes)",
                "2. Create New Task",
                "3. Edit Task",
                "4. Delete Task",
                "5. Back to Main Menu"
            ))

            when (inputHandler.promptForIntChoice("Select an option: ", 1..6)) {
                1 -> swimlanesView.display(user)
                2 -> creationView.createTask()
                3 -> editView.editTask()
                4 -> deletionView.deleteTask()
                5 -> return
            }
        }
    }

}