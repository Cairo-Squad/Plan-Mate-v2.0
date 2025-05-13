package ui.features.user.admin

import kotlinx.coroutines.runBlocking
import logic.model.User
import logic.usecase.user.GetAllUsersUseCase
import ui.utils.InputHandler
import ui.utils.OutputFormatter

class GetAllUsersView(
    private val inputHandler: InputHandler,
    private val outputFormatter: OutputFormatter,
    private val getAllUsersUseCase: GetAllUsersUseCase,
) {
    fun listAllUsers() = runBlocking {
        showHeader()
        val users = fetchUsers() ?: return@runBlocking

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

    private suspend fun fetchUsers(): List<User>? {
        val users = getAllUsersUseCase.getAllUsers()
        if (users.isEmpty()) {
            outputFormatter.printError("❌ No users found in the system!")
            return null
        }
        return users
    }

    private fun printUserInfo(users: List<User>) {
        outputFormatter.printInfo("📋 Available Users:")
        users.forEachIndexed { index, user ->
            outputFormatter.printInfo("📌 ${index + 1}. ${user.name ?: "Unknown"} | 🆔 ID: ${user.id ?: "N/A"} | 🏷️ Type: ${user.type ?: "Not Specified"}")
        }
    }
}
