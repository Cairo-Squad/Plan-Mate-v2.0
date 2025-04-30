package logic.usecase

import com.google.common.truth.Truth.assertThat
import io.mockk.every
import io.mockk.mockk
import logic.repositories.ProjectsRepository
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import utils.FakeData
import utils.FakeData.invalidUser
import utils.FakeData.validUser
import java.lang.Exception

class CreateProjectUseCaseTest() {
    private lateinit var projectRepository: ProjectsRepository
    private lateinit var createProject: CreateProjectUseCase

    @BeforeEach
    fun setUp() {
        projectRepository = mockk(relaxed = true)
        createProject = CreateProjectUseCase(projectRepository)
    }

    @Test
    fun `should successfully create a project when user type is admin`() {
        //Given
        val project = FakeData.validProject
        every { projectRepository.createProject(project, validUser) } returns Result.success(Unit)

        //When
        val result = createProject.createProject(project, validUser)

        //Then
        assertThat(result.isSuccess).isTrue()
    }

    @Test
    fun `should not create a project when user type is mate`() {
        //Given
        val project = FakeData.validProject
        every { projectRepository.createProject(project, invalidUser) } returns Result.failure(Exception())

        //When
        val result = createProject.createProject(project, invalidUser)

        //Then
        assertThat(result.isFailure).isTrue()
    }

    @Test
    fun `should create a project when description is empty`() {
        //Given
        val project = FakeData.projectWithNoDescription
        every { projectRepository.createProject(project, validUser) } returns Result.success(Unit)

        //When
        val result = createProject.createProject(project, validUser)

        //Then
        assertThat(result.isSuccess).isTrue()
    }

    @Test
    fun `should not create a project when project title is blank`() {
        //Given
        val project = FakeData.projectWithNoTitle
        every { projectRepository.createProject(project, validUser) } returns Result.failure(Exception())

        //When
        val result = createProject.createProject(project, validUser)

        //Then
        assertThat(result.isFailure).isTrue()
    }

}