package logic.usecase.project

import io.mockk.*
import kotlinx.coroutines.test.runTest
import logic.exception.WriteException
import logic.exception.EmptyNameException
import logic.exception.UnknownException
import logic.model.Project
import logic.model.State
import logic.repositories.ProjectsRepository
import logic.usecase.log.AddLogUseCase
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import java.util.*

class EditProjectTest {

    private val projectsRepository = mockk<ProjectsRepository>(relaxed = true)
    private lateinit var editProjectDescription: EditProjectUseCase
    private lateinit var addLogUseCase: AddLogUseCase
    private lateinit var validationProject: ValidationProject

    @BeforeEach
    fun setup() {
        validationProject = mockk(relaxed = true)
        addLogUseCase=mockk(relaxed = true)
        editProjectDescription = EditProjectUseCase(projectsRepository,addLogUseCase, validationProject)
    }

    @Test
    fun `should successfully edit project when valid new description is given`() = runTest {
        //Given
        val newProject = getNewProject()
        every { validationProject.validateEditProject(newProject) } returns Unit
        //When
        editProjectDescription.editProject(newProject)

        //Then
        verify { validationProject.validateEditProject(newProject) }
        coVerify { projectsRepository.editProject(any()) }
    }

    @Test
    fun `should successfully edit project when empty description is given`() = runTest {
        //Given
        val newProject = getNewProject()

        //When
        editProjectDescription.editProject(newProject.copy(description = ""))

        //Then
        coVerify { projectsRepository.editProject(any()) }
    }

    @Test
    fun `should throw exception when database throws write exception `() = runTest {
        //Given
        val newProject = getNewProject()
        coEvery { projectsRepository.editProject(any()) } throws WriteException()

        //When & Then
        assertThrows<WriteException> { editProjectDescription.editProject(newProject) }
    }

    @Test
    fun `should throw exception when database throws unknown exception`() = runTest {
        //Given
        val newProject = getNewProject()
        coEvery { projectsRepository.editProject(any()) } throws UnknownException()

        //When & Then
        assertThrows<UnknownException> { editProjectDescription.editProject(newProject) }
    }

    @Test
    fun `should throw empty name exception when editing project with empty title`() = runTest {
        //Given
        val newProject = getNewProject().copy(title = "   ")
        every { validationProject.validateEditProject(newProject) } throws EmptyNameException()

        //When & Then
        assertThrows<EmptyNameException> { editProjectDescription.editProject(newProject) }
    }

    private fun getNewProject() = Project(
        id = UUID.randomUUID(),
        title = "Project",
        description = "Project Description",
        createdBy = UUID.randomUUID(),
        tasks = emptyList(),
        state = State(id = UUID.randomUUID(), title = "TODO")
    )
}