package logic.usecase

import io.mockk.every
import io.mockk.mockk
import logic.model.Project
import logic.model.State
import logic.repositories.ProjectsRepository
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import java.util.*
import kotlin.test.assertEquals

class EditProjectTest {

    private val projectsRepository = mockk<ProjectsRepository>(relaxed = true)
    private lateinit var editProjectDescription: EditProjectUseCase

    @BeforeEach
    fun setup() {
        editProjectDescription = EditProjectUseCase(projectsRepository)
    }

    @Test
    fun `Given valid newDescription,When updating database,Then returns success result`() {
        //When
        val result = editProjectDescription.editProject(getNewProject())
        //Then
        assertEquals(expected = Result.success(Unit), actual = result)
    }

    @Test
    fun `Given empty description,When updating database,Then returns success result`() {
        //When
        val result = editProjectDescription.editProject(getNewProject().copy(description = ""))
        //Then
        assertEquals(expected = Result.success(Unit), actual = result)
    }


    @Test
    fun `Given new project, When updating database but exception is thrown, Then returns failure with cause`() {
        every { projectsRepository.editProject(any()) } throws Exception()
        //When
        val result = editProjectDescription.editProject(getNewProject())
        //Then
        assertEquals(expected = Result.failure(Exception()), actual = result)
    }

    @Test
    fun `Given new project with empty title, When validating, Then returns failure with cause`() {
        //When
        val result = editProjectDescription.editProject(getNewProject())
        //Then
        assertEquals(expected = Result.failure(IllegalArgumentException()), actual = result)
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