package ui.features.auth

import kotlinx.coroutines.runBlocking
import logic.usecase.user.GetCurrentUserUseCase
import logic.usecase.user.LoginUserUseCase
import ui.features.user.UserManagementView
import ui.utils.InputHandler
import ui.utils.OutputFormatter

class LoginManagementView(
    private val loginUserUseCase : LoginUserUseCase,
    private val inputHandler : InputHandler,
    private val outputFormatter : OutputFormatter,
    private val userManagementView : UserManagementView,
    private val getCurrentUser : GetCurrentUserUseCase

) {
    fun showLoginScreen() = runBlocking {
        outputFormatter.printHeader("🔑 PlanMate - Login")

        val username = inputHandler.promptForInput("👤 Username: ")
        if (username.isEmpty()) {
            outputFormatter.printError("❌ Username cannot be empty.")
            inputHandler.waitForEnter()
            return@runBlocking
        }

        val password = inputHandler.promptForPassword("🔒 Password: ")
        if (password.isEmpty()) {
            outputFormatter.printError("❌ Password cannot be empty.")
            inputHandler.waitForEnter()
            return@runBlocking
        }

        try {
            val isLogin = loginUserUseCase.login(username, password)
            val getCurrentUser = getCurrentUser.getCurrentUser()
            if (isLogin && getCurrentUser != null) {
                outputFormatter.printSuccess("🎉 Login successful! Welcome, ${getCurrentUser.name} 🙌")
                userManagementView.showUserMenu()
            } else {
                outputFormatter.printError("❌ Invalid username or password.")
            }
        } catch (e : Exception) {
            outputFormatter.printError("❌ Authentication failed: ${e.message}")
        }
    }
}
