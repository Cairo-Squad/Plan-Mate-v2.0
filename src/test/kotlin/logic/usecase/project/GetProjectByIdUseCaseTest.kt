package logic.usecase.project

import com.google.common.truth.Truth.assertThat
import io.mockk.every
import io.mockk.mockk
import logic.model.Project
import logic.model.State
import logic.repositories.ProjectsRepository
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import java.util.UUID

class GetProjectByIdUseCaseTest {

    private lateinit var projectsRepository: ProjectsRepository
    private lateinit var getProjectByIdUseCase: GetProjectByIdUseCase
    private val project = Project(
        id = UUID.randomUUID(),
        title = "Project",
        description = "Project Description",
        createdBy = UUID.randomUUID(),
        tasks = emptyList(),
        state = State(id = UUID.randomUUID(), title = "TODO")
    )

    @BeforeEach
    fun setup() {
        projectsRepository = mockk(relaxed = true)
        getProjectByIdUseCase = GetProjectByIdUseCase(projectsRepository)
    }

    @Test
    fun `should return Success with Project when repository returns project`() {
        // Given
        val projectId = project.id
        every { projectsRepository.getProjectById(project.id) } returns Result.success(project)

        // When
        val result = getProjectByIdUseCase.getProjectById(project.id)

        //Then
        assertThat(result).isEqualTo(project)
    }

    @Test
    fun `should return Failure when repository returns not found`() {
        // Given
        val projectId = project.id
        val exception = NoSuchElementException()
        every { projectsRepository.getProjectById(project.id) } returns Result.failure(exception)

        // When
        val result = getProjectByIdUseCase.getProjectById(project.id)

        //Then
        assertThat(result.isFailure).isTrue()
    }
}