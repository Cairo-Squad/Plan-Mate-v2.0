package ui.features.state

import logic.model.Project
import ui.utils.InputHandler
import ui.utils.OutputFormatter

class StateManagementView(
    private val createStateView: CreateStateView,
    private val editStateView: EditStateView,
    private val inputHandler: InputHandler,
    private val outputFormatter: OutputFormatter
) {
    fun showStateMenu(project: Project) = runBlocking {
        while (true) {
            outputFormatter.printHeader("State Management")
            outputFormatter.printMenu(
                listOf(
                    "1. Create State",
                    "2. Edit State",
                    "3. Exit"
                )
            )

            when (inputHandler.promptForIntChoice("Select an option: ", 1..3)) {
                1 -> createStateView.createState()
                2 -> editStateView.editState(project.state!!)
                else -> return@runBlocking
            }
        }
    }
}
