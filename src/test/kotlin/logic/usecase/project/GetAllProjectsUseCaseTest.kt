package logic.usecase.project

import io.mockk.every
import io.mockk.mockk
import logic.model.Project
import logic.model.State
import logic.repositories.ProjectsRepository
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import java.util.*
import kotlin.NoSuchElementException
import kotlin.test.assertEquals

class GetAllProjectsUseCaseTest {

    private lateinit var projectRepository: ProjectsRepository
    private lateinit var getAllProjectsUseCase: GetAllProjectsUseCase

    @BeforeEach
    fun setup() {
        projectRepository = mockk(relaxed = true)
        getAllProjectsUseCase = GetAllProjectsUseCase(projectRepository)
    }

    @Test
    fun `should return all projects when repository returns all projects`() {
        // Given
        val list = projectsList()
        every { projectRepository.getAllProjects() } returns list

        // When
        val result = getAllProjectsUseCase.getAllProjects()

        // Then
        assertEquals(list, result)
    }

    @Test
    fun `should throw exception when repository returns Failure`() {
        // Given
        every { projectRepository.getAllProjects() } throws NoSuchElementException()

        // When & Then
        assertThrows<NoSuchElementException> { getAllProjectsUseCase.getAllProjects() }
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