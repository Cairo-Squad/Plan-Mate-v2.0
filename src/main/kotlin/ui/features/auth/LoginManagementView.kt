package ui.features.auth

import data.dto.UserType
import logic.usecase.user.LoginUserUseCase
import ui.features.user.UserManagementView
import ui.features.user.admin.AdminManagementView
import ui.utils.InputHandler
import ui.utils.OutputFormatter

class LoginManagementView(
    private val loginUserUseCase: LoginUserUseCase,
    private val inputHandler: InputHandler,
    private val outputFormatter: OutputFormatter,
    private val userManagementView: UserManagementView

) {
    fun showLoginScreen() {
        outputFormatter.printHeader("🔑 PlanMate - Login")

        val username = inputHandler.promptForInput("👤 Username: ")
        val password = inputHandler.promptForPassword("🔒 Password: ")

        try {
            val user = loginUserUseCase.login(username, password)
            UserSession.setUser(user)

            outputFormatter.printSuccess("🎉 Login successful! Welcome, ${user.name} 🙌")
            userManagementView.showUserMenu()
            return

        } catch (e: Exception) {
            outputFormatter.printError("❌ Authentication failed: ${e.message}")
        }
    }

}
