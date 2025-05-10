package ui.features.user.admin

import kotlinx.coroutines.runBlocking
import logic.model.User
import logic.usecase.user.GetAllUsersUseCase
import ui.utils.InputHandler
import ui.utils.OutputFormatter

class ListAllUsersView(
    private val inputHandler: InputHandler,
    private val outputFormatter: OutputFormatter,
    private val getAllUsersUseCase: GetAllUsersUseCase,
) {
    fun listAllUsers() = runBlocking {
        showHeader()

        val users = getAllUsersUseCase.getAllUsers()
        if (users.isEmpty()) {
            outputFormatter.printError("❌ No users found in the system!")
            return@runBlocking
        }

        printUserInfo(users)
        inputHandler.waitForEnter()
    }

    private fun showHeader() {
        outputFormatter.printHeader(
            """
            ╔══════════════════════════╗
            ║ 👥 List of All Users      ║
            ╚══════════════════════════╝
            """.trimIndent()
        )
    }

    private fun printUserInfo(users: List<User>) {
        outputFormatter.printInfo("📋 Available Users:")
        users.forEachIndexed { index, user ->
            outputFormatter.printInfo("📌 ${index + 1}. ${user.name} | 🆔 ID: ${user.id} | 🏷️ Type: ${user.type}")
        }
    }
}
