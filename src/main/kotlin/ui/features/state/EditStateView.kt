package ui.features.state

import kotlinx.coroutines.runBlocking
import logic.model.State
import logic.usecase.state.EditStateUseCase
import ui.utils.InputHandler
import ui.utils.OutputFormatter

class EditStateView(
    private val editStateUseCase: EditStateUseCase,
    private val inputHandler: InputHandler,
    private val outputFormatter: OutputFormatter
) {
    fun editState(oldState: State) = runBlocking {
        outputFormatter.printHeader("Edit a State ID")
        val title = inputHandler.promptForInput("Enter new state title: ")
        editStateUseCase.editState(oldState)
        outputFormatter.printSuccess("State updated successfully!")
    }
}