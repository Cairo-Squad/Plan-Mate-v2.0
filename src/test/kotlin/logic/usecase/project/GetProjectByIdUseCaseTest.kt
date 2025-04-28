package logic.usecase.project

import com.google.common.truth.Truth.assertThat
import io.mockk.every
import io.mockk.mockk
import logic.model.Project
import logic.repositories.ProjectsRepository
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import java.util.*

class GetProjectByIdUseCaseTest {

    private val projectsRepository: ProjectsRepository = mockk(relaxed = true)
    private val getProjectByIdUseCase = GetProjectByIdUseCase(projectsRepository)
    private val project = Project(id = UUID.randomUUID())

    @Test
    fun `should return project when id found`() {
        // Given
        every { projectsRepository.getProjectById(project.id) } returns project

        // When
        val result = getProjectByIdUseCase.getProjectById(project.id)

        //Then
        assertThat(result).isEqualTo(project)
    }

    @Test
    fun `should throw NoSuchElementException when projectRepository return null`() {
        // Given
        every { projectsRepository.getProjectById(project.id) } returns null

        // When & Then
        assertThrows<NoSuchElementException> {
            getProjectByIdUseCase.getProjectById(project.id)
        }
    }
}