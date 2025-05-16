package ui.features.user.admin

import kotlinx.coroutines.runBlocking
import logic.model.UserType
import logic.usecase.user.SignUpUseCase
import ui.utils.InputHandler
import ui.utils.OutputFormatter

class CreateNewUserView(
    private val inputHandler: InputHandler,
    private val outputFormatter: OutputFormatter,
    private val signUpUseCase: SignUpUseCase
) {
    fun createNewUser() = runBlocking {
        showHeader()
        val userNameAndPassword = collectUserInput() ?: return@runBlocking
        performUserCreation(userNameAndPassword)
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

    private fun collectUserInput(): Pair<String, String>? {
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

        return username to password
    }

    private suspend fun performUserCreation(userNameAndPassword: Pair<String, String>) {
        try {
            signUpUseCase.signUp(userNameAndPassword.first, userNameAndPassword.second, UserType.MATE)
            outputFormatter.printSuccess("✅ User '${userNameAndPassword.first}' created successfully!")
        } catch (ex: Exception) {
            outputFormatter.printError("❌ Error during user creation: ${ex.message}")
        }

        inputHandler.waitForEnter()
    }
}
