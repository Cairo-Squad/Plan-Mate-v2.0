package logic.usecase.project

import io.mockk.*
import util.FakeData
import kotlinx.coroutines.test.runTest
import logic.exception.ProjectNotFoundException
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
    fun `should throw exception when id is not found `() = runTest{
        //Given
        val fakeId = UUID.randomUUID()
        coEvery { projectsRepository.deleteProject(any()) } throws ProjectNotFoundException()
        //When & Then
        assertThrows<ProjectNotFoundException>
        { deleteProjectUseCase.deleteProjectById(fakeId) }
    }

    @Test
    fun `should delete successfully when id is found `() = runTest {
        //Given
        val validId = FakeData.fakeProjects[0].id
        //When
        deleteProjectUseCase.deleteProjectById(validId)
        //Then
        coVerify { projectsRepository.deleteProject(validId) }
    }
}
