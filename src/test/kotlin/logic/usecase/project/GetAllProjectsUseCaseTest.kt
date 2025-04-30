package logic.usecase.project

import com.google.common.truth.Truth.assertThat
import io.mockk.every
import io.mockk.mockk
import logic.model.Project
import logic.model.State
import logic.repositories.ProjectsRepository
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import java.util.*

class GetAllProjectsUseCaseTest {

    private lateinit var projectRepository: ProjectsRepository
    private lateinit var getAllProjectsUseCase: GetAllProjectsUseCase

    @BeforeEach
    fun setup() {
        projectRepository = mockk(relaxed = true)
        getAllProjectsUseCase = GetAllProjectsUseCase(projectRepository)
    }

    @Test
    fun `should return Success when repository returns Success`() {
        // Given
        every { projectRepository.getAllProjects() } returns Result.success(projectsList())

        // When
        val result = getAllProjectsUseCase.getAllProjects()

        // Then
        assertThat(result.isSuccess).isTrue()
    }

    @Test
    fun `should return Failure when repository returns Failure`() {
        // Given
        every { projectRepository.getAllProjects() } returns Result.failure(NoSuchElementException())

        // When
        val result = getAllProjectsUseCase.getAllProjects()

        // Then
        assertThat(result.isFailure).isTrue()
    }

    private fun projectsList(): List<Project> {
        val project = Project(
            id = UUID.randomUUID(),
            title = "Project",
            description = "Description",
            createdBy = UUID.randomUUID(),
            tasks = emptyList(),
            state = State(id = UUID.randomUUID(), title = "TODO")
        )
        return listOf(
            project.copy(id = UUID.randomUUID()),
            project.copy(id = UUID.randomUUID()),
            project.copy(id = UUID.randomUUID())
        )
    }
}