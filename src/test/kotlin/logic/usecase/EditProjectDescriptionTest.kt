package logic.usecase

import io.mockk.every
import io.mockk.mockk
import logic.repositories.ProjectsRepository
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import java.io.IOException
import java.util.UUID
import kotlin.test.assertEquals

class EditProjectDescriptionTest {

    private val projectsRepository = mockk<ProjectsRepository>()
    private lateinit var editProjectDescription: EditProjectUseCase

    @BeforeEach
    fun setup() {
        editProjectDescription = EditProjectUseCase(projectsRepository)
    }

    @Test
    fun `Given newDescription and project id,When updating database,Then returns success result`() {
        //Given
        val newDescription = "This is new Description"
        val prjectID = UUID.randomUUID()
        //When
        val result = editProjectDescription.editProject(newDescription, prjectID)
        //Then
        assertEquals(expected = Result.success(Unit), actual = result)
    }

    @Test
    fun `Given empty description and project id,When updating database,Then returns success result`() {
        //Given
        val newDescription = ""
        val prjectID = UUID.randomUUID()
        //When
        val result = editProjectDescription.editProject(newDescription, prjectID)
        //Then
        assertEquals(expected = Result.success(Unit), actual = result)
    }

    @Test
    fun `Given newDescription and project id,When updating the database but IO exception thrown,Then returns failure`() {
        //Given
        val newDescription = "new description"
        val projectId = UUID.randomUUID()
        every { projectsRepository.editProjectTitle(any(), any()) } throws IOException("")
        //When
        val result = editProjectDescription.editProject(newDescription, projectId)
        //Then
        assertEquals(
            expected = Result.failure(
                IOException(""),
            ),
            actual = result
        )
    }

    @Test
    fun `Given newDescription and project id, When updating database, Then returns failure with cause`() {
        //Given
        val newDescription = "This is new Description"
        val prjectID = UUID.randomUUID()
        every { projectsRepository.editProjectDescription(newDescription, prjectID) } throws Exception()
        //When
        val result = editProjectDescription.editProject(newDescription, prjectID)
        //Then
        assertEquals(expected = Result.failure(Exception()), actual = result)
    }


}