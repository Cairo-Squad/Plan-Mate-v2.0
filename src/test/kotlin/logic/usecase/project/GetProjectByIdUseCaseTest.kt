package logic.usecase.project

import com.google.common.truth.Truth.assertThat
import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
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
    fun `should return project Successfully when repository returns project`() = runTest {
        // Given
        coEvery { projectsRepository.getProjectById(project.id!!) } returns project

        // When
        val result = getProjectByIdUseCase.getProjectById(project.id!!)

        //Then
        assertThat(result).isEqualTo(project)
    }

    @Test
    fun `should throw exception when repository returns no projects`() = runTest {
        // Given
        val exception = NoSuchElementException()
        coEvery { projectsRepository.getProjectById(project.id!!) } throws exception

        // When & Then
        assertThrows<NoSuchElementException> { getProjectByIdUseCase.getProjectById(project.id!!) }
    }
}