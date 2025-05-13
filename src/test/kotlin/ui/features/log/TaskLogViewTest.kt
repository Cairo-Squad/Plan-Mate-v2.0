package ui.features.log

import io.mockk.*
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import logic.usecase.task.GetAllTasksByProjectIdUseCase
import logic.usecase.project.GetAllProjectsUseCase
import logic.usecase.log.GetTaskLogsUseCase
import ui.utils.InputHandler
import ui.utils.OutputFormatter
import logic.model.Project
import logic.model.Task
import logic.model.Log
import java.util.UUID

class TaskLogViewTest {

    private lateinit var getAllProjectsUseCase: GetAllProjectsUseCase
    private lateinit var getAllTasksByProjectIdUseCase: GetAllTasksByProjectIdUseCase
    private lateinit var getTaskLogsUseCase: GetTaskLogsUseCase
    private lateinit var inputHandler: InputHandler
    private lateinit var outputFormatter: OutputFormatter
    private lateinit var taskLogView: TaskLogView

    @BeforeEach
    fun setup() {
        getAllProjectsUseCase = mockk(relaxed = true)
        getAllTasksByProjectIdUseCase = mockk(relaxed = true)
        getTaskLogsUseCase = mockk(relaxed = true)
        inputHandler = mockk(relaxed = true)
        outputFormatter = mockk(relaxed = true)

        taskLogView = TaskLogView(
            getAllProjectsUseCase,
            getAllTasksByProjectIdUseCase,
            getTaskLogsUseCase,
            inputHandler,
            outputFormatter
        )
    }

    @Test
    fun `given no projects exist when viewing task logs then should show error`() = runBlocking {
        // GIVEN
        coEvery { getAllProjectsUseCase.getAllProjects() } returns emptyList()

        // WHEN
        taskLogView.viewTaskLogs()

        // THEN
        verify { outputFormatter.printError("❌ No projects available for task logs!") }
    }

    @Test
    fun `given a project with no tasks when viewing task logs then should display warning`() = runBlocking {
        // GIVEN
        val mockProject = Project(id = UUID.randomUUID(), title = "Test Project")
        coEvery { getAllProjectsUseCase.getAllProjects() } returns listOf(mockProject)
        every { inputHandler.promptForIntChoice(any(), any()) } returns 1
        coEvery { getAllTasksByProjectIdUseCase.getAllTasksByProjectId(mockProject.id!!) } returns emptyList()

        // WHEN
        taskLogView.viewTaskLogs()

        // THEN
        verify { outputFormatter.printWarning("⚠️ No tasks found for project 'Test Project'.") }
    }

    @Test
    fun `given a task with no logs when viewing task logs then should display error`() = runBlocking {
        // GIVEN
        val mockProject = Project(id = UUID.randomUUID(), title = "Test Project")
        val mockTask = Task(id = UUID.randomUUID(), title = "Test Task")

        coEvery { getAllProjectsUseCase.getAllProjects() } returns listOf(mockProject)
        every { inputHandler.promptForIntChoice(any(), any()) } returns 1
        coEvery { getAllTasksByProjectIdUseCase.getAllTasksByProjectId(mockProject.id!!) } returns listOf(mockTask)
        every { inputHandler.promptForIntChoice(any(), any()) } returns 1
        coEvery { getTaskLogsUseCase.execute(mockTask.id!!) } returns emptyList()

        // WHEN
        taskLogView.viewTaskLogs()

        // THEN
        verify { outputFormatter.printError("❌ No logs found for this task.") }
    }


}
