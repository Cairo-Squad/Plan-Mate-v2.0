package logic.usecase.project

import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import logic.exception.EmptyTitleException
import logic.exception.InvalidUserException
import logic.repositories.ProjectsRepository
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import util.FakeData
import util.FakeData.invalidUser
import util.FakeData.validUser

class CreateProjectUseCaseTest {
    private lateinit var projectRepository: ProjectsRepository
    private lateinit var createProjectUseCase: CreateProjectUseCase

    @BeforeEach
    fun setUp() {
        projectRepository = mockk(relaxed = true)
        createProjectUseCase = CreateProjectUseCase(projectRepository)
    }

    @Test
    fun `should successfully create a project when user type is admin`() {
        //Given
        val project = FakeData.validProject

        //When
        createProjectUseCase.createProject(project, validUser)

        //Then
        verify(exactly = 1) {
            createProjectUseCase.createProject(project, validUser)
        }
    }

    @Test
    fun `should not create a project when user type is mate`() {
        //Given
        val project = FakeData.validProject
        every { (projectRepository).createProject(project, invalidUser) } throws InvalidUserException()

        //When & Then
        assertThrows<InvalidUserException> {
            createProjectUseCase.createProject(project, invalidUser)
        }
    }

    @Test
    fun `should create a project successfully when description is empty`() {
        //Given
        val project = FakeData.projectWithNoDescription

        //When
        createProjectUseCase.createProject(project, validUser)

        //Then
        verify(exactly = 1) {
            createProjectUseCase.createProject(project, validUser)
        }
    }

    @Test
    fun `should throw exception when project title is blank`() {
        //Given
        val project = FakeData.projectWithNoTitle

        //When & Then
        assertThrows<EmptyTitleException> {
            createProjectUseCase.createProject(project, validUser)
        }
    }
}