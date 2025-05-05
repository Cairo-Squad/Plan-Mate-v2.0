package ui.features.user.admin

import data.dto.UserType
import logic.usecase.user.CreateUserUseCase
import ui.utils.InputHandler
import ui.utils.OutputFormatter

class CreateNewUserView (
    private val inputHandler: InputHandler,
    private val outputFormatter: OutputFormatter,
    private val createUserUseCase: CreateUserUseCase
) {
    fun createNewUser() {
        outputFormatter.printHeader(
            """
            ╔════════════════════════════╗
            ║ 👤 Create a New User 🆕     ║
            ╚════════════════════════════╝
            """.trimIndent()
        )

        val username = inputHandler.promptForInput("📛 Enter username: ")
        val password = inputHandler.promptForPassword("🔒 Enter password: ")
        val userType = UserType.MATE

        val result = createUserUseCase.createUser(username, password, userType)

        if (result.isSuccess) {
            outputFormatter.printSuccess("✅ User '$username' created successfully!")
        } else {
            outputFormatter.printError("❌ Failed to create user: ${result.exceptionOrNull()?.message}")
        }

        inputHandler.waitForEnter()
    }
}
