package ui.features.state

import data.dto.UserType
import logic.model.State
import logic.usecase.state.CreateStateUseCase
import ui.features.auth.UserSession
import ui.utils.InputHandler
import ui.utils.OutputFormatter
import java.util.*

class CreateStateView(
    private val createStateUseCase: CreateStateUseCase,
    private val inputHandler: InputHandler,
    private val outputFormatter: OutputFormatter
) {
    fun createState() {
        outputFormatter.printHeader("Create a New State")
        val title = inputHandler.promptForInput("Enter state title: ")
        val state = State(id = UUID.randomUUID(), title = title)
        UserSession.getUser()?.let {
            if (it.type == UserType.ADMIN) {
                outputFormatter.printSuccess("State created successfully!")
            }
        } ?: outputFormatter.printError("Non authorized access")
    }
}