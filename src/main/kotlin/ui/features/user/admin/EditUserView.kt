package ui.features.user.admin

import kotlinx.coroutines.runBlocking
import logic.exception.EntityNotChangedException
import logic.model.User
import logic.usecase.user.EditUserUseCase
import logic.usecase.user.GetAllUsersUseCase
import ui.utils.InputHandler
import ui.utils.OutputFormatter

class EditUserView(
    private val editUserUseCase: EditUserUseCase,
    private val getAllUsersUseCase: GetAllUsersUseCase,
    private val inputHandler: InputHandler,
    private val outputFormatter: OutputFormatter
) {
    fun editUser() = runBlocking {
        showHeader()
        val users = fetchUsers() ?: return@runBlocking

        printUserInfo(users)
        val selectedUser = promptUserSelection(users) ?: return@runBlocking

        val updatedUser = promptUserUpdates(selectedUser) ?: return@runBlocking

        performUserEdit(selectedUser, updatedUser)
    }

    private fun showHeader() {
        outputFormatter.printHeader(
            """
            ╔════════════════════════════╗
            ║ ✏️ Edit User Information      ║
            ╚════════════════════════════╝
            """.trimIndent()
        )
    }

    private suspend fun fetchUsers(): List<User>? {
        val users = getAllUsersUseCase.getAllUsers()
        if (users.isEmpty()) {
            outputFormatter.printError("❌ No users found!")
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
        val selectedIndex = inputHandler.promptForIntChoice("🔹 Select a user to edit: ", 1..users.size) - 1
        return users.getOrNull(selectedIndex)
    }

    private fun promptUserUpdates(selectedUser: User?): User? {
        if (selectedUser == null) {
            outputFormatter.printError("⚠️ Invalid selection!")
            return null
        }

        val newName = inputHandler.promptForInput("✏️ Enter new name (leave empty to keep current): ")
            .takeIf { it.isNotBlank() } ?: selectedUser.name

        val newPassword = inputHandler.promptForPassword("🔒 Enter new password (leave empty to keep current): ")
            .takeIf { it.isNotBlank() } ?: selectedUser.password

        val updatedUser = selectedUser.copy(name = newName, password = newPassword)

        return if (validateUserInputs(updatedUser, selectedUser)) updatedUser else null
    }

    private fun validateUserInputs(newUser: User, oldUser: User): Boolean {
        return if (newUser.name?.trim() == oldUser.name?.trim() &&
            newUser.password?.trim() == oldUser.password?.trim()) {
            outputFormatter.printError("⚠️ No changes detected! User information remains the same.")
            false
        } else {
            true
        }
    }

    private suspend fun performUserEdit(selectedUser: User, updatedUser: User) {
        try {
            editUserUseCase.editUser(updatedUser)
            outputFormatter.printSuccess("✅ User '${selectedUser.name}' updated successfully!")
        } catch (e: Exception) {
            outputFormatter.printError("🔄 No changes were applied: ${e.message}")
        }

        inputHandler.waitForEnter()
    }
}
