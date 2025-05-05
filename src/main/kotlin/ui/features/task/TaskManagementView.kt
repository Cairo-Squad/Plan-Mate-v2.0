package ui.features.task

import ui.features.log.TaskLogView
import ui.utils.InputHandler
import ui.utils.OutputFormatter

class TaskManagementView(
	private val creationView: CreateTaskView,
	private val editView: EditTaskView,
	private val deletionView: DeleteTaskView,
	private val swimlanesView: SwimlanesView,
	private val inputHandler: InputHandler,
	private val outputFormatter: OutputFormatter,
	private val taskLogView: TaskLogView
) {
	fun showTaskMenu() {
		while (true) {
			outputFormatter.printHeader(
				"""
                ╔═══════════════════════════╗
                ║ 📌 Task Management Menu    ║
                ╚═══════════════════════════╝
                """.trimIndent()
			)

			outputFormatter.printMenu(
				listOf(
					"📂 1. View Project Tasks (Swimlanes)",
					"🆕 2. Create New Task",
					"✏️  3. Edit Task",
					"🗑️  4. Delete Task",
					"📜 5. View Task Log",
					"⬅️  6. Back to Main Menu",
					"🚪 7. Logout"
				)
			)

			when (inputHandler.promptForIntChoice("🔹 Select an option: ", 1..7)) {
				1 -> swimlanesView.getAllTasksByProject()
				2 -> creationView.createTask()
				3 -> editView.editTask()
				4 -> deletionView.deleteTask()
				5 -> taskLogView.viewTaskLogs()
				6 -> return
				7 -> {
					outputFormatter.printSuccess("✅ Logged out successfully! Have a great day! 👋")
					return
				}
			}
		}
	}
}
