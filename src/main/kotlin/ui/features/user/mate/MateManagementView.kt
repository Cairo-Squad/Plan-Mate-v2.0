package ui.features.user.mate

import logic.model.User

import ui.utils.OutputFormatter
import ui.features.task.TaskManagementView

class MateManagementView(
    private val taskManagementView: TaskManagementView,
    private val outputFormatter: OutputFormatter
) {
    fun showMateMenu(user: User) {
        while (true) {
            outputFormatter.printHeader("Mate Menu")
            taskManagementView.showTaskMenu(user)

        }
    }
}
