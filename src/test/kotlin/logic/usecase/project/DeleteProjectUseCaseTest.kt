package logic.usecase.project

import io.mockk.*
import util.FakeData
import kotlinx.coroutines.test.runTest
import data.customException.PlanMateException
import logic.repositories.ProjectsRepository
import logic.usecase.log.AddProjectLogUseCase
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import java.util.*

class DeleteProjectUseCaseTest {
    private lateinit var projectsRepository: ProjectsRepository
    private lateinit var addProjectLogUseCase: AddProjectLogUseCase
    private lateinit var deleteProjectUseCase: DeleteProjectUseCase
    private lateinit var getProjectByIdUseCase: GetProjectByIdUseCase

    @BeforeEach
    fun setUp() {
        projectsRepository = mockk(relaxed = true)
        addProjectLogUseCase = mockk()
        getProjectByIdUseCase = mockk()
        deleteProjectUseCase = DeleteProjectUseCase(projectsRepository, addProjectLogUseCase, getProjectByIdUseCase)
    }

    @Test
    fun `should throw exception when id is not found `() = runTest{
        //Given
        val fakeId = UUID.randomUUID()
        coEvery { projectsRepository.deleteProject(any()) } throws PlanMateException.NetworkException.DataNotFoundException()
        
        //When & Then
        coVerify(exactly = 0) { addProjectLogUseCase.addProjectLog(any()) }
        assertThrows<PlanMateException.NetworkException.DataNotFoundException>
        { deleteProjectUseCase.deleteProjectById(fakeId) }
    }

    @Test
    fun `should delete successfully when id is found `() = runTest {
        //Given
        val validId = FakeData.fakeProjects[0].id
        coEvery { getProjectByIdUseCase.getProjectById(any()) } returns FakeData.fakeProjects[0]
        coEvery { addProjectLogUseCase.addProjectLog(any()) } returns Unit

        //When
        deleteProjectUseCase.deleteProjectById(validId!!)
        //Then
        coVerify(exactly = 1) { addProjectLogUseCase.addProjectLog(any()) }
        coVerify { projectsRepository.deleteProject(validId) }
    }
}
