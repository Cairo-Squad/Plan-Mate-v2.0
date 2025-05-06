package logic.usecase.project

import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import logic.exception.WriteException
import logic.exception.EmptyNameException
import logic.exception.UnknownException
import logic.model.Project
import logic.model.State
import logic.repositories.ProjectsRepository
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import java.util.*

class EditProjectTest {

    private val projectsRepository = mockk<ProjectsRepository>(relaxed = true)
    private lateinit var editProjectDescription: EditProjectUseCase

    @BeforeEach
    fun setup() {
        editProjectDescription = EditProjectUseCase(projectsRepository)
    }

    @Test
    fun `should successfully edit project when valid new description is given`() {
        //Give
        val newProject = getNewProject()
        //When
        editProjectDescription.editProject(newProject)
        //Then
        verify { projectsRepository.editProject(any()) }
    }

    @Test
    fun `should successfully edit project when empty description is given`() {
        //Give
        val newProject = getNewProject()
        //When
        editProjectDescription.editProject(newProject.copy(description = ""))
        //Then
        verify { projectsRepository.editProject(any()) }
    }

    @Test
    fun `should throw exception when database throws write exception `() {
        //Give
        val newProject = getNewProject()
        every { projectsRepository.editProject(any()) } throws WriteException()
        //When && Then
        assertThrows<WriteException> { editProjectDescription.editProject(newProject) }
    }

    @Test
    fun `should throw exception when database throws unknown exception`() {
        //Give
        val newProject = getNewProject()
        every { projectsRepository.editProject(any()) } throws UnknownException()
        //When && Then
        assertThrows<UnknownException> { editProjectDescription.editProject(newProject) }
    }

    @Test
    fun `should throw empty name exception when editing project with empty title`() {
        //Give
        val newProject = getNewProject().copy(title = "   ")
        //When && Then
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