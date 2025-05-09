package ui.features.user.admin

import data.dto.UserType
import kotlinx.coroutines.runBlocking
import logic.usecase.user.CreateUserUseCase
import ui.utils.InputHandler
import ui.utils.OutputFormatter
import java.util.UUID

class CreateNewUserView(
    private val inputHandler : InputHandler,
    private val outputFormatter : OutputFormatter,
    private val createUserUseCase : CreateUserUseCase
) {
    fun createNewUser() = runBlocking {
        outputFormatter.printHeader(
            """
            ╔════════════════════════════╗
            ║ 👤 Create a New User 🆕     ║
            ╚════════════════════════════╝
            """.trimIndent()
        )

        val username = inputHandler.promptForInput("📛 Enter username: ")
        if (username.isEmpty()) {
            outputFormatter.printError("❌ Username cannot be empty.")
            inputHandler.waitForEnter()
	        return@runBlocking
        }

        val password = inputHandler.promptForPassword("🔒 Enter password: ")
        if (password.isEmpty()) {
            outputFormatter.printError("❌ Password cannot be empty.")
            inputHandler.waitForEnter()
	        return@runBlocking
        }
        val userType = UserType.MATE

        try {
            val isCreated = createUserUseCase.createUser(UUID.randomUUID(), username, password, userType)
            if (isCreated) {
                outputFormatter.printSuccess("✅ User '$username' created successfully!")
            }
        } catch (ex : Exception) {
            outputFormatter.printError("❌ Failed to create user: ${ex.message}")
        }

        inputHandler.waitForEnter()
    }
}
