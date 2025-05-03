package ui.utils

import ui.features.auth.LoginFeatureUI
import ui.features.auth.UserSession
import ui.features.user.UserManagementView

class CLIMenu(
    private val loginView: LoginFeatureUI,
    private val userManagementView: UserManagementView,
    private val outputFormatter: OutputFormatter
) {
    fun start() {
        displayWelcomeMessage()
        while (UserSession.getUser() == null) {
            loginView.showLoginScreen()
        }
        userManagementView.showUserMenu()
    }

    private fun displayWelcomeMessage() {
        outputFormatter.printHeader("Welcome to PlanMate Task Management System")
        println(
            """
            A lightweight task management application for teams
            Version 1.0.0
            
            --------------------------------------------------------
            
        """.trimIndent()
        )
    }
}