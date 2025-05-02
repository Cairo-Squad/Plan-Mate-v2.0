package ui.features.user.admin

import logic.model.User
import data.dto.UserType
import ui.utils.InputHandler
import ui.utils.OutputFormatter
import logic.usecase.user.*
import ui.features.project.ProjectManagementView
import ui.features.log.LogMangementView

class AdminManagementView(
    private val createUserUseCase: CreateUserUseCase,
    private val deleteUserUseCase: DeleteUserUseCase,
    private val editUserUseCase: EditUserUseCase,
    private val getAllUsersUseCase: GetAllUsersUseCase,
    private val projectManagementView: ProjectManagementView,
    private val auditMenuView: LogMangementView,
    private val inputHandler: InputHandler,
    private val outputFormatter: OutputFormatter
) {
    fun showAdminMenu() {
        while (true) {
            outputFormatter.printHeader("Admin Menu")
            outputFormatter.printMenu(
                listOf(
                    "1. Manage Projects",
                    "2. Manage Users",
                    "3. View Audit Logs",
                    "4. Logout"
                )
            )

            when (inputHandler.promptForIntChoice("Select an option: ", 1..4)) {
                1 -> projectManagementView.showProjectMenu()
                2 -> showUserManagementMenu()
                3 -> auditMenuView.showAuditMenu()
                4 -> {
                    outputFormatter.printSuccess("Logged out successfully!")
                    return
                }
            }
        }
    }

    private fun showUserManagementMenu() {
        while (true) {
            outputFormatter.printHeader("User Management")
            outputFormatter.printMenu(
                listOf(
                    "1. List All Users",
                    "2. Create New User",
                    "3. Edit User",
                    "4. Delete User",
                    "5. Back to Main Menu"
                )
            )

            when (inputHandler.promptForIntChoice("Select an option: ", 1..5)) {
                1 -> listAllUsers()
                2 -> createNewUser()
                3 -> editUser()
                4 -> deleteUser()
                5 -> return
            }
        }
    }

    private fun listAllUsers() {
        val users = getAllUsersUseCase.execute().getOrElse {
            outputFormatter.printError("Failed to retrieve users.")
            return
        }

        outputFormatter.printHeader("All Users")
        users.forEach { user ->
            outputFormatter.printInfo("- ${user.name} (${user.type}) | ID: ${user.id}")
        }

        inputHandler.waitForEnter()
    }

    private fun createNewUser() {
        val username = inputHandler.promptForInput("Enter username: ")
        val password = inputHandler.promptForPassword("Enter password: ")
        val userType = UserType.MATE

        val result = createUserUseCase.createUser(username, password, userType)
        if (result.isSuccess) {
            outputFormatter.printSuccess("User created successfully!")
        } else {
            outputFormatter.printError("Failed to create user: ${result.exceptionOrNull()?.message}")
        }

        inputHandler.waitForEnter()
    }

    private fun editUser() {
        val username = inputHandler.promptForInput("Enter username to edit: ")

        val users = getAllUsersUseCase.execute().getOrElse {
            outputFormatter.printError("Failed to retrieve users.")
            return
        }

        val currentUser = users.find { it.name == username } ?: run {
            outputFormatter.printError("User not found!")
            return
        }

        val newPassword = inputHandler.promptForPassword("Enter new password (leave empty to keep current): ")
            .takeIf { it.isNotBlank() } ?: currentUser.password

        val updatedUser = currentUser.copy(password = newPassword)

        val result = editUserUseCase.editUser(updatedUser, currentUser)

        inputHandler.waitForEnter()
    }

    private fun deleteUser() {
        val username = inputHandler.promptForInput("Enter username to delete: ")

        val users = getAllUsersUseCase.execute().getOrElse {
            outputFormatter.printError("Failed to retrieve users.")
            return
        }
    }
}

