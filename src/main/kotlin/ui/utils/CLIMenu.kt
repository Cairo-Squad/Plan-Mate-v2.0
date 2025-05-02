package ui.utils

import UserManagementView
import ui.features.log.LogMangementView
import ui.features.project.ProjectManagementView
import ui.features.task.TaskManagementView


class
CLIMenu(

    private val outputFormatter: OutputFormatter
) {
    /**
     * Starts the application's main menu
     */
    fun start() {
        displayWelcomeMessage()
        //current user
        //userManagementView.showUserMenu()
    }

    /**
     * Displays the welcome banner
     */
    private fun displayWelcomeMessage() {
        outputFormatter.printHeader("Welcome to PlanMate Task Management System")
        println("""
            A lightweight task management application for teams
            Version 1.0.0
            
            --------------------------------------------------------
            
        """.trimIndent())
    }
}