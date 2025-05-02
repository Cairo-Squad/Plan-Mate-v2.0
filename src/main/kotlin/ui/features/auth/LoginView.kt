package ui.features.auth

import logic.usecase.user.LoginUserUseCase
import ui.utils.InputHandler
import ui.utils.OutputFormatter
import logic.model.User

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

            outputFormatter.printSuccess("Login successful! Welcome, ${user.name}")
                //store user data
            user  // Return the authenticated user
        } catch (e: Exception) {
            outputFormatter.printError("Authentication failed: ${e.message}")
            null // Return null if authentication fails
        }
    }
}
