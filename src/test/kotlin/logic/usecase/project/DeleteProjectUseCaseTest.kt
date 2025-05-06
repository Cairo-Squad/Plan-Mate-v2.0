package logic.usecase.project

import util.FakeData
import com.google.common.truth.Truth.assertThat
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import logic.exception.projectNotFoundException
import logic.repositories.ProjectsRepository
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import java.util.*

class DeleteProjectUseCaseTest {
    private lateinit var projectsRepository: ProjectsRepository
    private lateinit var deleteProjectUseCase: DeleteProjectUseCase

    @BeforeEach
    fun setUp() {
        projectsRepository = mockk(relaxed = true)
        deleteProjectUseCase = DeleteProjectUseCase(projectsRepository)
    }

    @Test
    fun `should throw exception when id is not found `() {
        //Given
        val fakeId = UUID.randomUUID()
        every { projectsRepository.deleteProject(any()) } throws projectNotFoundException()
        //When & Then
        assertThrows<projectNotFoundException>
        { deleteProjectUseCase.deleteProjectById(fakeId) }
    }

    @Test
    fun `should delete successfully when id is found `() {
        //Given
        val validId = FakeData.fakeProjects[0].id
        //When
        deleteProjectUseCase.deleteProjectById(validId)
        //Then
        verify { projectsRepository.deleteProject(validId) }
    }
}
