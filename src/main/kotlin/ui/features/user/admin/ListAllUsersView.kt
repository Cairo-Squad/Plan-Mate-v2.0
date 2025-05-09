package ui.features.user.admin

import kotlinx.coroutines.runBlocking
import logic.usecase.user.GetAllUsersUseCase
import ui.utils.InputHandler
import ui.utils.OutputFormatter

class ListAllUsersView(
    private val inputHandler: InputHandler,
    private val outputFormatter: OutputFormatter,
    private val getAllUsersUseCase: GetAllUsersUseCase,
) {
    fun listAllUsers() = runBlocking {
        outputFormatter.printHeader(
            """
            ╔══════════════════════════╗
            ║ 👥 List of All Users      ║
            ╚══════════════════════════╝
            """.trimIndent()
        )

        val users = getAllUsersUseCase.getAllUsers()

        if (users.isEmpty()) {
            outputFormatter.printError("❌ No users found in the system!")
	        return@runBlocking
        }

        outputFormatter.printInfo("📋 Available Users:")
        users.forEachIndexed { index, user ->
            outputFormatter.printInfo("📌 ${index + 1}. ${user.name} | 🆔 ID: ${user.id} | 🏷️ Type: ${user.type}")
        }

        inputHandler.waitForEnter()
    }
}
