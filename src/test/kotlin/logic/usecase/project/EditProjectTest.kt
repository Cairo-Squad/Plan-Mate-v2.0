package logic.usecase.project

import io.mockk.every
import io.mockk.mockk
import logic.exception.CsvWriteException
import logic.exception.EmptyNameException
import logic.exception.UnknownException
import logic.model.Project
import logic.model.State
import logic.repositories.ProjectsRepository
import logic.usecase.Log.AddLogUseCase
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import java.util.*
import kotlin.test.assertEquals

class EditProjectTest {

    private val projectsRepository = mockk<ProjectsRepository>(relaxed = true)
    private lateinit var editProjectDescription: EditProjectUseCase
    private lateinit var addLogUseCase: AddLogUseCase

    @BeforeEach
    fun setup() {
        addLogUseCase=mockk(relaxed = true)
        editProjectDescription = EditProjectUseCase(projectsRepository,addLogUseCase)
    }

    @Test
    fun `Given valid newDescription,When updating database,Then returns success result`() {
        //Give
        val newProject = getNewProject()
        every { projectsRepository.editProject(any()) } returns Unit
        //When
        val result = editProjectDescription.editProject(newProject)
        //Then
        assertEquals(expected = Result.success(Unit), actual = result)
    }

    @Test
    fun `Given empty description,When updating database,Then returns success result`() {
        //Give
        val newProject = getNewProject()
        every { projectsRepository.editProject(any()) } returns Unit
        //When
        val result = editProjectDescription.editProject(newProject.copy(description = ""))
        //Then
        assertEquals(expected = Result.success(Unit), actual = result)
    }


    @Test
    fun `Given new project, When updating database but write exception is thrown, Then returns failure with cause`() {
        //Give
        val newProject = getNewProject()
        every { projectsRepository.editProject(any()) } throws CsvWriteException()
        //When
        val result = editProjectDescription.editProject(newProject)
        //Then
        val failure = Result.failure<Any>(CsvWriteException())
        assertEquals(expected = failure.exceptionOrNull()!!::class, actual = result.exceptionOrNull()!!::class)
    }

    @Test
    fun `Given new project, When updating database but unKnown exception is thrown, Then returns failure with cause`() {
        //Give
        val newProject = getNewProject()
        every { projectsRepository.editProject(any()) } throws UnknownException()
        //When
        val result = editProjectDescription.editProject(newProject)
        //Then
        val expected = Result.failure<Any>(UnknownException())
        assertEquals(expected = expected.exceptionOrNull()!!::class, actual = result.exceptionOrNull()!!::class)
    }

    @Test
    fun `Given new project with empty title, When validating, Then returns failure with cause`() {
        //Give
        val newProject = getNewProject().copy(title = "   ")
        //When
        val result = editProjectDescription.editProject(newProject)
        //Then
        val expected = Result.failure<Unit>(EmptyNameException())
        assertEquals(expected = expected.exceptionOrNull()!!::class, actual = result.exceptionOrNull()!!::class)
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