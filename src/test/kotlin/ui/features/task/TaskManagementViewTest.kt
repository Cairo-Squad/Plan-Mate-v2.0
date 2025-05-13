package ui.features.task

import io.mockk.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Disabled
import org.junit.jupiter.api.Test
import ui.features.log.TaskLogView
import ui.utils.InputHandler
import ui.utils.OutputFormatter

class TaskManagementViewTest {
	private lateinit var creationView: CreateTaskView
	private lateinit var editView: EditTaskView
	private lateinit var deletionView: DeleteTaskView
	private lateinit var swimlanesView: SwimlanesView
	private lateinit var inputHandler: InputHandler
	private lateinit var outputFormatter: OutputFormatter
	private lateinit var taskLogView: TaskLogView
	private lateinit var taskManagementView: TaskManagementView
	
	@BeforeEach
	fun setup() {
		creationView = mockk(relaxed = true)
		editView = mockk(relaxed = true)
		deletionView = mockk(relaxed = true)
		swimlanesView = mockk(relaxed = true)
		inputHandler = mockk(relaxed = true)
		outputFormatter = mockk(relaxed = true)
		taskLogView = mockk(relaxed = true)
		
		taskManagementView = TaskManagementView(
			creationView,
			editView,
			deletionView,
			swimlanesView,
			inputHandler,
			outputFormatter,
			taskLogView
		)
	}
	@Disabled
	@Test
	fun `should display task management menu header`() {
		// Given
		every { inputHandler.promptForIntChoice("🔹 Select an option: ", 1..7) } returns 6
		
		// When
		taskManagementView.showTaskMenu()
		
		// Then
		verify {
			outputFormatter.printHeader(
				"""
            ╔═══════════════════════════╗
            ║ 📌 Task Management Menu    ║
            ╚═══════════════════════════╝
            """.trimIndent()
			)
		}
		verify {
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
		}
	}
	@Disabled
	@Test
	fun `should call swimlanes view when option 1 is selected`() {
		// Given
		every { inputHandler.promptForIntChoice("🔹 Select an option: ", 1..7) } returnsMany listOf(1, 6)
		
		// When
		taskManagementView.showTaskMenu()
		
		// Then
		verify { swimlanesView.getAllTasksByProject() }
	}
	@Disabled
	@Test
	fun `should call create task view when option 2 is selected`() {
		// Given
		every { inputHandler.promptForIntChoice("🔹 Select an option: ", 1..7) } returnsMany listOf(2, 6)
		
		// When
		taskManagementView.showTaskMenu()
		
		// Then
		verify { creationView.createTask() }
	}
	@Disabled
	@Test
	fun `should call edit task view when option 3 is selected`() {
		// Given
		every { inputHandler.promptForIntChoice("🔹 Select an option: ", 1..7) } returnsMany listOf(3, 6)
		
		// When
		taskManagementView.showTaskMenu()
		
		// Then
		verify { editView.editTask() }
	}
	@Disabled
	@Test
	fun `should call delete task view when option 4 is selected`() {
		// Given
		every { inputHandler.promptForIntChoice("🔹 Select an option: ", 1..7) } returnsMany listOf(4, 6)
		
		// When
		taskManagementView.showTaskMenu()
		
		// Then
		verify { deletionView.deleteTask() }
	}
	@Disabled
	@Test
	fun `should call task log view when option 5 is selected`() {
		// Given
		every { inputHandler.promptForIntChoice("🔹 Select an option: ", 1..7) } returnsMany listOf(5, 6)
		
		// When
		taskManagementView.showTaskMenu()
		
		// Then
		verify { taskLogView.viewTaskLogs() }
	}
	@Disabled
	@Test
	fun `should exit menu when option 6 is selected`() {
		// Given
		every { inputHandler.promptForIntChoice("🔹 Select an option: ", 1..7) } returns 6
		
		// When
		taskManagementView.showTaskMenu()
		
		// Then
		verify(exactly = 0) {
			creationView.createTask()
			editView.editTask()
			deletionView.deleteTask()
			swimlanesView.getAllTasksByProject()
			taskLogView.viewTaskLogs()
		}
	}
	@Disabled
	@Test
	fun `should logout and display success message when option 7 is selected`() {
		// Given
		every { inputHandler.promptForIntChoice("🔹 Select an option: ", 1..7) } returns 7
		
		// When
		taskManagementView.showTaskMenu()
		
		// Then
		verify { outputFormatter.printSuccess("✅ Logged out successfully! Have a great day! 👋") }
	}
	@Disabled
	@Test
	fun `should continue looping until exit or logout is selected`() {
		// Given
		every { inputHandler.promptForIntChoice("🔹 Select an option: ", 1..7) } returnsMany listOf(1, 2, 3, 4, 5, 6)
		
		// When
		taskManagementView.showTaskMenu()
		
		// Then
		verify(exactly = 1) { swimlanesView.getAllTasksByProject() }
		verify(exactly = 1) { creationView.createTask() }
		verify(exactly = 1) { editView.editTask() }
		verify(exactly = 1) { deletionView.deleteTask() }
		verify(exactly = 1) { taskLogView.viewTaskLogs() }
	}
}