package ui.features.user.admin

import data.dto.UserType
import logic.usecase.user.CreateUserUseCase
import logic.usecase.user.DeleteUserUseCase
import logic.usecase.user.EditUserUseCase
import logic.usecase.user.GetAllUsersUseCase
import ui.features.log.LogMangementView
import ui.features.project.ProjectManagementView
import ui.utils.InputHandler
import ui.utils.OutputFormatter

class AdminManagementView(
    private val createUserUseCase: CreateUserUseCase,
    private val deleteUserUseCase: DeleteUserUseCase,
    private val editUserUseCase: EditUserUseCase,
    private val getAllUsersUseCase: GetAllUsersUseCase,
    private val projectManagementView: ProjectManagementView,
    private val auditMenuView: LogMangementView,
    private val inputHandler: InputHandler,
    private val outputFormatter: OutputFormatter,
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
        getAllUsersUseCase.execute().onSuccess { users ->
            users.forEachIndexed { index, user ->
                outputFormatter.printInfo("${index + 1}. ${user.name} (ID: ${user.id})")
            }
            
            val selectedUser = inputHandler.promptForIntChoice(
                "Select the number of user you want to modify: ",
                1..users.size
            ) - 1
            
            
            val newName = inputHandler.promptForPassword("Enter new Name (leave empty to keep current): ")
                .takeIf { it.isNotBlank() } ?: users[selectedUser].name
            
            val newPassword = inputHandler.promptForPassword("Enter new password (leave empty to keep current): ")
                .takeIf { it.isNotBlank() } ?: users[selectedUser].password
            
            val updatedUser = users[selectedUser].copy(name = newName , password = newPassword)
            
            editUserUseCase.editUser(updatedUser, users[selectedUser])
            
            inputHandler.waitForEnter()
        }
    }

    private fun deleteUser() {
        
        getAllUsersUseCase.execute().onSuccess { users ->
            users.forEachIndexed { index, user ->
                outputFormatter.printInfo("${index + 1}. ${user.name} (ID: ${user.id})")
            }
            
            val selectedUser = inputHandler.promptForIntChoice(
                "Select the number of user you want to delete: ",
                1..users.size
            ) - 1
            
            
            deleteUserUseCase.deleteUser(users[selectedUser].id)
            
            inputHandler.waitForEnter()
        }
    }
}

