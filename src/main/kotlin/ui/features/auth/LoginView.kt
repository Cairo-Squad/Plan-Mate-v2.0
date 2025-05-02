package ui.features.auth

import logic.usecase.user.LoginUserUseCase
import ui.utils.InputHandler
import ui.utils.OutputFormatter
import logic.model.User
import ui.features.auth.UserSession

class LoginView(
    private val loginUserUseCase: LoginUserUseCase,
    private val inputHandler: InputHandler,
    private val outputFormatter: OutputFormatter
) {
    fun showLoginScreen(): User? {
        outputFormatter.printHeader("PlanMate - Login")

        val username = inputHandler.promptForInput("Username: ")
        val password = inputHandler.promptForPassword("Password: ")

        return try {
            val user = loginUserUseCase.login(username, password)
            UserSession.setUser(user)

            outputFormatter.printSuccess("Login successful! Welcome, ${user.name}")
            user
        } catch (e: Exception) {
            outputFormatter.printError("Authentication failed: ${e.message}")
            null
        }
    }
}
