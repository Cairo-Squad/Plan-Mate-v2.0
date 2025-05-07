package ui.utils

import ui.features.auth.LoginManagementView
import ui.features.auth.UserSession
import ui.features.user.UserManagementView

class CLIMenu(
    private val loginView: LoginManagementView,
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
        outputFormatter.printHeader(
            """
        ██████╗ ██╗      █████╗ ███╗   ███╗ ███╗   ██╗ █████╗ ████████╗███████╗
        ██╔══██╗██║     ██╔══██╗████╗ ████║ ████╗  ██║██╔══██╗╚══██╔══╝██╔════╝
        ██████╔╝██║     ███████║██╔████╔██║ ██╔██╗ ██║███████║   ██║   █████╗  
        ██╔══██╗██║     ██╔══██║██║╚██╔╝██║ ██║╚██╗██║██╔══██║   ██║   ██╔══╝  
        ██║  ██║███████╗██║  ██║██║ ╚═╝ ██║ ██║ ╚████║██║  ██║   ██║   ███████╗
        ╚═╝  ╚═╝╚══════╝╚═╝  ╚═╝╚═╝     ╚═╝ ╚═╝  ╚═══╝╚═╝  ╚═╝   ╚═╝   ╚══════╝
        🌟 🚀 Welcome to PlanMate Task Management System 🌟
        """.trimIndent()
        )

        println(
            """
        📝 A lightweight task management application for teams
        🛠️ Version 1.0.0
        
        🔹 Organize tasks efficiently
        🔹 Collaborate with your team seamlessly
        🔹 Track progress with real-time updates
        
        ✅ Ready to boost productivity? Log in below! ✨
        --------------------------------------------------------
    """.trimIndent()
        )
    }
}
