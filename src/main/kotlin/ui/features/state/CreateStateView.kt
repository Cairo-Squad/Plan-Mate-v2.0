package ui.features.state

import data.dto.UserType
import logic.model.State
import logic.usecase.CreateStateUseCase
import ui.CurrentUser
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
        CurrentUser.user?.let{
            if(it.type == UserType.ADMIN){
                val stateCreated = createStateUseCase.createState(state, it)
                outputFormatter.printSuccess("State created successfully!")
            }
        } ?: outputFormatter.printError("Non authorized access")
    }
}