package ui.features.user.admin

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
    fun editUser() {
        outputFormatter.printHeader(
            """
            ╔════════════════════════════╗
            ║ ✏️ Edit User Information      ║
            ╚════════════════════════════╝
            """.trimIndent()
        )

        val users = getAllUsersUseCase.execute().getOrNull()

        if (users.isNullOrEmpty()) {
            outputFormatter.printError("❌ No users found!")
            return
        }

        outputFormatter.printInfo("👥 Available Users:")
        users.forEachIndexed { index, user ->
            outputFormatter.printInfo("📌 ${index + 1}. ${user.name} (ID: ${user.id})")
        }

        val selectedIndex = inputHandler.promptForIntChoice("🔹 Select a user to edit: ", 1..users.size) - 1

        val selectedUser = users[selectedIndex]

        val newName = inputHandler.promptForInput("✏️ Enter new name (leave empty to keep current): ")
            .takeIf { it.isNotBlank() } ?: selectedUser.name

        val newPassword = inputHandler.promptForPassword("🔒 Enter new password (leave empty to keep current): ")
            .takeIf { it.isNotBlank() } ?: selectedUser.password

        val updatedUser = selectedUser.copy(name = newName, password = newPassword)

        editUserUseCase.editUser(updatedUser, selectedUser)

        outputFormatter.printSuccess("✅ User '${selectedUser.name}' updated successfully!")

        inputHandler.waitForEnter()
    }
}
