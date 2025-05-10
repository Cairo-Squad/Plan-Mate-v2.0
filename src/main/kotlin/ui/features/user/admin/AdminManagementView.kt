package ui.features.user.admin
import ui.features.log.LogManagementView
import ui.features.project.ProjectManagementView
import ui.features.task.TaskManagementView
import ui.utils.InputHandler
import ui.utils.OutputFormatter
class AdminManagementView(
    private val projectManagementView: ProjectManagementView,
    private val auditMenuView: LogManagementView,
    private val inputHandler: InputHandler,
    private val outputFormatter: OutputFormatter,
    private val createNewUserView: CreateNewUserView,
    private val deleteUserView: DeleteUserView,
    private val editUserView: EditUserView,
    private val listAllUsersView: ListAllUsersView,
    private val taskManagementView: TaskManagementView
) {
    fun showAdminMenu() {
        while (true) {
            outputFormatter.printHeader(
                """
                ╔════════════════════════╗
                ║ 👑 Admin Control Center ║
                ╚════════════════════════╝
                """.trimIndent()
            )

            outputFormatter.printMenu(
                listOf(
                    "📂 1. Manage Projects",
                    "👥 2. Manage Users",
                    "✅ 3. Manage Tasks",
                    "📜 4. View Audit Logs",
                    "🚪 5. Logout"
                )
            )

            when (inputHandler.promptForIntChoice("🛠️ Select an option:", 1..4)) {
                1 -> projectManagementView.showProjectMenu()
                2 -> showUserManagementMenu()
                3 -> taskManagementView.showTaskMenu()
                4 -> auditMenuView.showAuditMenu()
                5 -> {
                    outputFormatter.printSuccess("✅ You have logged out successfully! Have a great day! 👋")
                    break

                }
            }
        }
    }

    private fun showUserManagementMenu() {
        while (true) {
            outputFormatter.printHeader(
                """
                ─────────────────────────────
                👥 User Management Dashboard 
                ─────────────────────────────
                """.trimIndent()
            )

            outputFormatter.printMenu(
                listOf(
                    "📜 1. List All Users",
                    "➕ 2. Create New User",
                    "✏️ 3. Edit User",
                    "🗑️ 4. Delete User",
                    "⬅️ 5. Back to Main Menu"
                )
            )

            when (inputHandler.promptForIntChoice("🔹 Choose an option:", 1..5)) {
                1 -> listAllUsersView.listAllUsers()
                2 -> createNewUserView.createNewUser()
                3 -> editUserView.editUser()
                4 -> deleteUserView.deleteUser()
                5 -> return
            }
        }
    }
}
