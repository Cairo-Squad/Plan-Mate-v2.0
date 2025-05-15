package logic.usecase.project

import io.mockk.*
import kotlinx.coroutines.test.runTest
import data.customException.PlanMateException
import logic.model.Project
import logic.model.State
import logic.repositories.ProjectsRepository
import logic.usecase.log.AddProjectLogUseCase
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import java.io.IOException
import java.util.*

class EditProjectTest {

    private val projectsRepository = mockk<ProjectsRepository>(relaxed = true)
    private lateinit var editProjectDescription: EditProjectUseCase
    private lateinit var addProjectLogUseCase: AddProjectLogUseCase
    private lateinit var validationProject: ValidationProject

    @BeforeEach
    fun setup() {
        validationProject = mockk(relaxed = true)
        addProjectLogUseCase = mockk(relaxed = true)
        editProjectDescription = EditProjectUseCase(projectsRepository, addProjectLogUseCase, validationProject)
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
        coEvery { projectsRepository.editProject(any()) } throws IOException()

        //When & Then
        assertThrows<IOException> { editProjectDescription.editProject(newProject) }
    }

    @Test
    fun `should throw exception when database throws unknown exception`() = runTest {
        //Given
        val newProject = getNewProject()
        coEvery { projectsRepository.editProject(any()) } throws PlanMateException.NetworkException.UnKnownException()

        //When & Then
        assertThrows<PlanMateException.NetworkException.UnKnownException> { editProjectDescription.editProject(newProject) }
    }

    @Test
    fun `should throw empty name exception when editing project with empty title`() = runTest {
        //Given
        val newProject = getNewProject().copy(title = "   ")
        every { validationProject.validateEditProject(newProject) } throws PlanMateException.ValidationException.NameException()

        //When & Then
        assertThrows<PlanMateException.ValidationException.NameException> { editProjectDescription.editProject(newProject) }
    }

    private fun getNewProject() = Project(
        id = UUID.randomUUID(),
        title = "Project",
        description = "Project Description",
        createdBy = UUID.randomUUID(),
        state = State(id = UUID.randomUUID(), title = "TODO")
    )
}