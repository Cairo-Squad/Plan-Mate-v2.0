package ui.features.log

import io.mockk.*
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import logic.usecase.log.GetProjectLogsUseCase
import logic.usecase.project.GetAllProjectsUseCase
import ui.utils.InputHandler
import ui.utils.OutputFormatter
import logic.model.Project
import org.junit.jupiter.api.Disabled
import java.util.UUID

class ProjectLogViewTest {

    private lateinit var getAllProjectsUseCase: GetAllProjectsUseCase
    private lateinit var getProjectLogsUseCase: GetProjectLogsUseCase
    private lateinit var inputHandler: InputHandler
    private lateinit var outputFormatter: OutputFormatter
    private lateinit var projectLogView: ProjectLogView

    @BeforeEach
    fun setup() {
        getAllProjectsUseCase = mockk(relaxed = true)
        getProjectLogsUseCase = mockk(relaxed = true)
        inputHandler = mockk(relaxed = true)
        outputFormatter = mockk(relaxed = true)

        projectLogView = ProjectLogView(
            getAllProjectsUseCase,
            getProjectLogsUseCase,
            inputHandler,
            outputFormatter
        )
    }
    @Disabled
    @Test
    fun `given no projects exist when viewing logs then should show error`() = runBlocking {
        // GIVEN
        coEvery { getAllProjectsUseCase.getAllProjects() } returns emptyList()

        // WHEN
        projectLogView.viewProjectLogs()

        // THEN
        verify { outputFormatter.printError("❌ No projects available for log viewing!") }
    }



    @Test
    fun `given a project with no logs when viewing logs then should display warning`() = runBlocking {
        // GIVEN
        val mockProject = Project(id = UUID.randomUUID(), title = "Test Project", description = "Sample Description")
        coEvery { getAllProjectsUseCase.getAllProjects() } returns listOf(mockProject)
        every { inputHandler.promptForIntChoice(any(), any()) } returns 1
        coEvery { getProjectLogsUseCase.getProjectLogs(mockProject.id!!) } returns emptyList()

        // WHEN
        projectLogView.viewProjectLogs()

        // THEN
        verify { outputFormatter.printWarning("⚠️ No logs found for project 'Test Project'.") }
    }
}
