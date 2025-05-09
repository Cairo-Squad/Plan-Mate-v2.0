package ui.features.auth

import logic.usecase.user.LoginUserUseCase
import ui.features.user.UserManagementView
import ui.utils.InputHandler
import ui.utils.OutputFormatter

class LoginManagementView(
    private val loginUserUseCase : LoginUserUseCase,
    private val inputHandler : InputHandler,
    private val outputFormatter : OutputFormatter,
    private val userManagementView : UserManagementView

) {
    fun showLoginScreen() {
        outputFormatter.printHeader("🔑 PlanMate - Login")

        val username = inputHandler.promptForInput("👤 Username: ")
        if (username.isEmpty()) {
            outputFormatter.printError("❌ Username cannot be empty.")
            inputHandler.waitForEnter()
            return
        }

        val password = inputHandler.promptForPassword("🔒 Password: ")
        if (password.isEmpty()) {
            outputFormatter.printError("❌ Password cannot be empty.")
            inputHandler.waitForEnter()
            return
        }

        try {
            val user = loginUserUseCase.login(username, password)
                ?: throw Exception("Invalid username or password.")
            UserSession.setUser(user)

            outputFormatter.printSuccess("🎉 Login successful! Welcome, ${user.name} 🙌")
            userManagementView.showUserMenu()
            return

        } catch (e : Exception) {
            outputFormatter.printError("❌ Authentication failed: ${e.message}")
        }
    }

}
