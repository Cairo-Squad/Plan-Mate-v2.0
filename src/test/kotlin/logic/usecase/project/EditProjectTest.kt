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
    fun `Given valid newDescription,When updating database,Then returns success result`() {
        //Give
        val newProject = getNewProject()
        //When
        editProjectDescription.editProject(newProject)
        //Then
        verify { projectsRepository.editProject(any()) }
    }

    @Test
    fun `Given empty description,When updating database,Then returns success result`() {
        //Give
        val newProject = getNewProject()
        //When
        editProjectDescription.editProject(newProject.copy(description = ""))
        //Then
        verify { projectsRepository.editProject(any()) }
    }

    @Test
    fun `Given new project, When updating database but write exception is thrown, Then returns failure with cause`() {
        //Give
        val newProject = getNewProject()
        every { projectsRepository.editProject(any()) } throws WriteException()
        //When && Then
        assertThrows<WriteException> { editProjectDescription.editProject(newProject) }
    }

    @Test
    fun `Given new project, When updating database but unKnown exception is thrown, Then returns failure with cause`() {
        //Give
        val newProject = getNewProject()
        every { projectsRepository.editProject(any()) } throws UnknownException()
        //When && Then
        assertThrows<UnknownException> { editProjectDescription.editProject(newProject) }
    }

    @Test
    fun `Given new project with empty title, When validating, Then returns failure with cause`() {
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