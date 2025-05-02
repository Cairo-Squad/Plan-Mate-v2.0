package ui.features.auth

import logic.usecase.user.LoginUserUseCase
import ui.utils.InputHandler
import ui.utils.OutputFormatter

class LoginFeatureUI(
    private val loginUserUseCase: LoginUserUseCase,
    private val inputHandler: InputHandler,
    private val outputFormatter: OutputFormatter
) {
    fun showLoginScreen() {
        outputFormatter.printHeader("PlanMate - Login")

        val username = inputHandler.promptForInput("Username: ")
        val password = inputHandler.promptForPassword("Password: ")

        try {
            val user = loginUserUseCase.login(username, password)
            UserSession.setUser(user)
            outputFormatter.printSuccess("Login successful! Welcome, ${user.name}")
        } catch (e: Exception) {
            outputFormatter.printError("Authentication failed: ${e.message}")
        }
    }
}
