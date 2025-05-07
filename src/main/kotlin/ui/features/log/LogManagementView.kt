package ui.features.log

import ui.utils.InputHandler
import ui.utils.OutputFormatter

class LogManagementView(
    private val projectLogView: ProjectLogView,
    private val taskLogView: TaskLogView,
    private val inputHandler: InputHandler,
    private val outputFormatter: OutputFormatter
) {
    fun showAuditMenu() {
        while (true) {
            outputFormatter.printHeader(
                """
                ╔══════════════════════════╗
                ║ 📜 Audit Logs Dashboard   ║
                ╚══════════════════════════╝
                """.trimIndent()
            )

            outputFormatter.printMenu(
                listOf(
                    "📂 1. View Project Logs",
                    "📝 2. View Task Logs",
                    "🚪 3. Exit"
                )
            )

            when (inputHandler.promptForIntChoice("🔹 Select an option:", 1..3)) {
                1 -> projectLogView.viewProjectLogs()
                2 -> taskLogView.viewTaskLogs()
                3 -> {
                    outputFormatter.printSuccess("✅ Exiting audit logs menu. Have a great day! 👋")
                    return
                }
            }
        }
    }
}

