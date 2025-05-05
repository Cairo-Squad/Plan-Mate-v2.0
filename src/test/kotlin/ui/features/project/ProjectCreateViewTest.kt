import data.dto.UserType
import io.mockk.every
import io.mockk.mockk
import io.mockk.mockkObject
import io.mockk.verify
import logic.model.User
import logic.usecase.project.CreateProjectUseCase
import logic.usecase.state.CreateStateUseCase
import logic.usecase.task.CreateTaskUseCase
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import ui.features.auth.UserSession
import ui.features.project.ProjectCreateView
import ui.utils.InputHandler
import ui.utils.OutputFormatter
import java.util.*

class ProjectCreateViewTest {

    private lateinit var createProjectUseCase: CreateProjectUseCase
    private lateinit var createStateUseCase: CreateStateUseCase
    private lateinit var createTaskUseCase: CreateTaskUseCase
    private lateinit var inputHandler: InputHandler
    private lateinit var outputFormatter: OutputFormatter

    private lateinit var projectCreateView: ProjectCreateView

    private val user = User(UUID.randomUUID(), "Mahmoud khairy", "123ogiods", UserType.ADMIN)

    @BeforeEach
    fun setup() {
        createProjectUseCase = mockk()
        createStateUseCase = mockk()
        createTaskUseCase = mockk()
        inputHandler = mockk()
        outputFormatter = mockk(relaxed = true)

        projectCreateView = ProjectCreateView(
            createProjectUseCase,
            inputHandler,
            outputFormatter,
            createStateUseCase,
            createTaskUseCase
        )
    }

    @Test
    fun `createProject should print error if no authenticated user`() {
        mockkObject(UserSession)
        every { UserSession.getUser() } returns null

        projectCreateView.createProject()

        verify { outputFormatter.printError("No authenticated user found! Please log in first.") }
        verify(exactly = 0) { createProjectUseCase.createProject(any(), any()) }
    }

    @Test
    fun `createProject should create project without tasks`() {
        mockkObject(UserSession)
        every { UserSession.getUser() } returns user

        every { inputHandler.promptForInput("Enter project title: ") } returns "Project X"
        every { inputHandler.promptForInput("Enter project description: ") } returns "Description X"
        every { inputHandler.promptForInput("Enter project state title: ") } returns "State X"
        every { inputHandler.promptForYesNo("Do you want to add tasks to this project?") } returns false

        every { createStateUseCase.createState(any(), user) } returns true
        every { createProjectUseCase.createProject(any(), any()) } returns Result.success(Unit)

        projectCreateView.createProject()

        verify { createStateUseCase.createState(match { it.title == "State X" }, user) }
        verify {
            createProjectUseCase.createProject(match {
                it.title == "Project X" && it.tasks.isEmpty() && it.state.title == "State X"
            }, user)
        }
        verify { outputFormatter.printSuccess("Project created successfully!") }
    }

    @Test
    fun `createProject should create project with tasks`() {
        mockkObject(UserSession)
        every { UserSession.getUser() } returns user

        every { inputHandler.promptForInput("Enter project title: ") } returns "Project Y"
        every { inputHandler.promptForInput("Enter project description: ") } returns "Description Y"
        every { inputHandler.promptForInput("Enter project state title: ") } returns "State Y"

        every { inputHandler.promptForYesNo("Do you want to add tasks to this project?") } returns true
        every { inputHandler.promptForInput("Task title: ") } returns "Task 1"
        every { inputHandler.promptForInput("Task description: ") } returns "Task 1 Desc"
        every { inputHandler.promptForInput("Task state: ") } returns "TaskState 1"
        every { inputHandler.promptForYesNo("Do you want to add another task?") } returns false

        every { createStateUseCase.createState(any(), any()) } returns true
        every { createTaskUseCase.createTask(any()) } returns Result.success(Unit)
        every { createProjectUseCase.createProject(any(), any()) } returns Result.success(Unit)

        projectCreateView.createProject()

        verify { createStateUseCase.createState(match { it.title == "State Y" }, user) }
        verify { createTaskUseCase.createTask(match { it.title == "Task 1" && it.state.title == "TaskState 1" }) }
        verify {
            createProjectUseCase.createProject(match {
                it.title == "Project Y" && it.tasks.size == 1 && it.tasks[0].title == "Task 1"
            }, user)
        }
        verify { outputFormatter.printSuccess("Project created successfully!") }
    }

    @Test
    fun `createProject should print error if project creation fails`() {
        mockkObject(UserSession)
        every { UserSession.getUser() } returns user

        every { inputHandler.promptForInput(any()) } returnsMany listOf("Project Z", "Desc Z", "State Z")
        every { inputHandler.promptForYesNo("Do you want to add tasks to this project?") } returns false

        every { createStateUseCase.createState(any(), any()) } returns true
        every { createProjectUseCase.createProject(any(), any()) } returns Result.failure(RuntimeException("DB error"))

        projectCreateView.createProject()

        verify { outputFormatter.printError(match { it.contains("Failed to create project: DB error") }) }
    }
}
