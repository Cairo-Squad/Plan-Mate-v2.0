package ui.features.user.admin

import kotlinx.coroutines.runBlocking
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
        outputFormatter.printHeader(
            """
            ╔════════════════════════════╗
            ║ 🗑️  Delete User Management  ║
            ╚════════════════════════════╝
            """.trimIndent()
        )

        val users = getAllUsersUseCase.getAllUsers()

        if (users.isEmpty()) {
            outputFormatter.printError("❌ No users available to delete!")
	        return@runBlocking
        }

        outputFormatter.printInfo("👥 Available Users:")
        users.forEachIndexed { index, user ->
            outputFormatter.printInfo("📌 ${index + 1}. ${user.name} (ID: ${user.id})")
        }

        val selectedIndex = inputHandler.promptForIntChoice("🔹 Select a user to delete: ", 1..users.size) - 1

        val selectedUser = users[selectedIndex]

        outputFormatter.printWarning("⚠️ Are you sure you want to delete '${selectedUser.name}'? This action cannot be undone.")

        val confirmation = inputHandler.promptForInput("Type 'YES' to confirm: ")

        if (confirmation.equals("YES", ignoreCase = true)) {
            try {
                deleteUserUseCase.deleteUser(selectedUser.id)
                outputFormatter.printSuccess("✅ User '${selectedUser.name}' deleted successfully!")
            } catch (e: Exception) {
                outputFormatter.printInfo("🔄 Action canceled. No user was deleted: ${e.message}")
            }

            inputHandler.waitForEnter()
        }
    }
}