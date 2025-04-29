package logic.usecase

import io.mockk.every
import io.mockk.mockk
import logic.repositories.ProjectsRepository
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import java.io.IOException
import java.util.*
import kotlin.test.assertEquals

class EditProjectTitleUseCaseTest() {

    private val projectRepository = mockk<ProjectsRepository>()
    private lateinit var editProjectTitleUseCase: EditProjectTitleUseCase

    @BeforeEach
    fun setup() {
        editProjectTitleUseCase = EditProjectTitleUseCase(projectRepository)
    }

    @Test
    fun `Given project id and a newTitle,When updating the database,Then return success`() {
        //Given
        val newTitle = "New Title"
        val projectId = UUID.randomUUID()
        //When
        val result = editProjectTitleUseCase.editProjectTitle(projectId, newTitle)
        //Then
        assertEquals(expected = Result.success(Unit), actual = result)
    }

    @Test
    fun `Given project id and new title,When updating the database but IO exception thrown,Then returns failure`() {
        //Given
        val newTitle = "new title"
        val projectId = UUID.randomUUID()
        every { projectRepository.editProjectTitle(any(), any()) } throws IOException("")
        //When
        val result = editProjectTitleUseCase.editProjectTitle(projectId, newTitle)
        //Then
        assertEquals(
            expected = Result.failure(
                IOException(""),
            ), actual = result
        )
    }

    @Test
    fun `Given project id and new title,When updating the database but unKnown exception thrown,Then returns failure`() {
        //Given
        val newTitle = "new title"
        val projectId = UUID.randomUUID()
        every { projectRepository.editProjectTitle(any(), any()) } throws Exception("")
        //When
        val result = editProjectTitleUseCase.editProjectTitle(projectId, newTitle)
        //Then
        assertEquals(
            expected = Result.failure(
                Exception(""),
            ), actual = result
        )
    }

    @Test
    fun `Given project id and empty project title,When validating,Then returns failure`() {
        //Given
        val newTitle = ""
        val projectId = UUID.randomUUID()
        //When
        val result = editProjectTitleUseCase.editProjectTitle(projectId, newTitle)
        //Then
        assertEquals(
            expected = Result.failure(
                IllegalArgumentException("project title can't be empty"),
            ), actual = result
        )
    }


}