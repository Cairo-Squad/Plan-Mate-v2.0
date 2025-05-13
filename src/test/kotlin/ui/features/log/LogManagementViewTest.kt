package ui.features.log

import io.mockk.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Disabled
import org.junit.jupiter.api.Test
import ui.utils.InputHandler
import ui.utils.OutputFormatter

class LogManagementViewTest {

    private lateinit var projectLogView: ProjectLogView
    private lateinit var taskLogView: TaskLogView
    private lateinit var inputHandler: InputHandler
    private lateinit var outputFormatter: OutputFormatter
    private lateinit var logManagementView: LogManagementView

    @BeforeEach
    fun setup() {
        projectLogView = mockk(relaxed = true)
        taskLogView = mockk(relaxed = true)
        inputHandler = mockk(relaxed = true)
        outputFormatter = mockk(relaxed = true)

        logManagementView = LogManagementView(
            projectLogView,
            taskLogView,
            inputHandler,
            outputFormatter
        )
    }
    @Disabled
    @Test
    fun `given user selects option 1 when showing audit menu then should call viewProjectLogs`() {
        every { inputHandler.promptForIntChoice(any(), any()) } returnsMany listOf(1, 3)

        logManagementView.showAuditMenu()

        verify { projectLogView.viewProjectLogs() }
    }
    @Disabled
    @Test
    fun `given user selects option 2 when showing audit menu then should call viewTaskLogs`() {
        every { inputHandler.promptForIntChoice(any(), any()) } returnsMany listOf(2, 3)

        logManagementView.showAuditMenu()

        verify { taskLogView.viewTaskLogs() }
    }
    @Disabled
    @Test
    fun `given user selects option 3 when showing audit menu then should exit menu`() {
        every { inputHandler.promptForIntChoice(any(), any()) } returns 3

        logManagementView.showAuditMenu()

        verify { outputFormatter.printSuccess("✅ Exiting audit logs menu. Have a great day! 👋") }
    }
}
