import io.mockk.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import ui.features.task.TaskManagementView
import ui.features.user.admin.AdminManagementView
import ui.utils.InputHandler
import ui.utils.OutputFormatter
import ui.features.log.LogManagementView
import ui.features.project.ProjectManagementView
import ui.features.user.admin.CreateNewUserView
import ui.features.user.admin.DeleteUserView
import ui.features.user.admin.EditUserView
import ui.features.user.admin.GetAllUsersView

class AdminManagementViewTest {

    private lateinit var adminManagementView: AdminManagementView
    private lateinit var projectManagementView: ProjectManagementView
    private lateinit var auditMenuView: LogManagementView
    private lateinit var inputHandler: InputHandler
    private lateinit var outputFormatter: OutputFormatter
    private lateinit var createNewUserView: CreateNewUserView
    private lateinit var deleteUserView: DeleteUserView
    private lateinit var editUserView: EditUserView
    private lateinit var listAllUsersView: GetAllUsersView
    private lateinit var taskManagementView: TaskManagementView

    @BeforeEach
    fun setUp() {
        projectManagementView = mockk()
        auditMenuView = mockk()
        inputHandler = mockk()
        outputFormatter = mockk()
        createNewUserView = mockk()
        deleteUserView = mockk()
        editUserView = mockk()
        listAllUsersView = mockk()
        taskManagementView = mockk()

        every { outputFormatter.printHeader(any()) } returns Unit
        every { outputFormatter.printMenu(any()) } returns Unit

        every { inputHandler.promptForIntChoice(any(), any()) } returns 1

        every { projectManagementView.showProjectMenu() } returns Unit

        adminManagementView = AdminManagementView(
            projectManagementView, auditMenuView, inputHandler, outputFormatter,
            createNewUserView, deleteUserView, editUserView, listAllUsersView, taskManagementView
        )
    }

    @Test
    fun `should show admin menu and handle user input`() {
        // When
        adminManagementView.showAdminMenu()

        // Then
        verify { projectManagementView.showProjectMenu() }
        verify(exactly = 0) { taskManagementView.showTaskMenu() }
    }
}
