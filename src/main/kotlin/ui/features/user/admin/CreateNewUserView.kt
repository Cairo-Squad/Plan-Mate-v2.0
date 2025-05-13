package ui.features.user.admin

import logic.model.UserType
import kotlinx.coroutines.runBlocking
import logic.model.User
import logic.usecase.user.CreateUserUseCase
import ui.utils.InputHandler
import ui.utils.OutputFormatter

class CreateNewUserView(
    private val inputHandler: InputHandler,
    private val outputFormatter: OutputFormatter,
    private val createUserUseCase: CreateUserUseCase
) {
    fun createNewUser() = runBlocking {
        showHeader()
        val user = collectUserInput() ?: return@runBlocking
        performUserCreation(user)
    }

    private fun showHeader() {
        outputFormatter.printHeader(
            """
            ╔════════════════════════════╗
            ║ 👤 Create a New User 🆕     ║
            ╚════════════════════════════╝
            """.trimIndent()
        )
    }

    private fun collectUserInput(): User? {
        val username = inputHandler.promptForInput("📛 Enter username: ")
        if (username.isBlank()) {
            outputFormatter.printError("❌ Username cannot be empty.")
            inputHandler.waitForEnter()
            return null
        }

        val password = inputHandler.promptForPassword("🔒 Enter password: ")
        if (password.isBlank()) {
            outputFormatter.printError("❌ Password cannot be empty.")
            inputHandler.waitForEnter()
            return null
        }

        return User( name = username, password = password, type = UserType.MATE)
    }

    private suspend fun performUserCreation(user: User) {
        try {
            val isCreated = createUserUseCase.createUser(user)
            if (isCreated) {
                outputFormatter.printSuccess("✅ User '${user.name}' created successfully!")
            } else {
                outputFormatter.printError("❌ Failed to create user. Please try again.")
            }
        } catch (ex: Exception) {
            outputFormatter.printError("❌ Error during user creation: ${ex.message}")
        }

        inputHandler.waitForEnter()
    }
}
