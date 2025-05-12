package ui.features.user.admin

import kotlinx.coroutines.runBlocking
import logic.model.User
import logic.usecase.user.DeleteUserUseCase
import logic.usecase.user.GetAllUsersUseCase
import ui.utils.InputHandler
import ui.utils.OutputFormatter

class DeleteUserView(
    private val inputHandler: InputHandler,
    private val outputFormatter: OutputFormatter,
    private val getAllUsersUseCase: GetAllUsersUseCase,
    private val deleteUserUseCase: DeleteUserUseCase
) {
    fun deleteUser() = runBlocking {
        showHeader()
        val users = fetchUsers() ?: return@runBlocking

        printUserInfo(users)
        val selectedUser = promptUserSelection(users) ?: return@runBlocking
        if (confirmDeletion(selectedUser)) performDeletion(selectedUser)
    }


    private fun showHeader() {
        outputFormatter.printHeader(
            """
            ╔════════════════════════════╗
            ║ 🗑️  Delete User Management  ║
            ╚════════════════════════════╝
            """.trimIndent()
        )
    }


    private suspend fun fetchUsers(): List<User>? {
        val users = getAllUsersUseCase.getAllUsers()
        if (users.isEmpty()) {
            outputFormatter.printError("❌ No users available to delete!")
            return null
        }
        return users
    }


    private fun printUserInfo(users: List<User>) {
        outputFormatter.printInfo("👥 Available Users:")
        users.forEachIndexed { index, user ->
            outputFormatter.printInfo("📌 ${index + 1}. ${user.name} (ID: ${user.id})")
        }
    }


    private fun promptUserSelection(users: List<User>): User? {
        val selectedIndex = inputHandler.promptForIntChoice("🔹 Select a user to delete: ", 1..users.size) - 1
        return users.getOrNull(selectedIndex)
    }


    private fun confirmDeletion(selectedUser: User): Boolean {
        outputFormatter.printWarning("⚠️ Are you sure you want to delete '${selectedUser.name}'? This action cannot be undone.")
        return inputHandler.promptForInput("Type 'YES' to confirm: ").equals("YES", ignoreCase = true)
    }


    private suspend fun performDeletion(selectedUser: User) {
        try {
            deleteUserUseCase.deleteUser(selectedUser.id!!)
            outputFormatter.printSuccess("✅ User '${selectedUser.name}' deleted successfully!")
        } catch (e: Exception) {
            outputFormatter.printError("🔄 Action canceled. No user was deleted: ${e.message}")
        }
        inputHandler.waitForEnter()
    }
}
