package logic.usecase.project

import io.mockk.coVerify
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.test.runTest
import logic.exception.TitleException
import logic.exception.InvalidUserTypeException
import logic.repositories.ProjectsRepository
import logic.usecase.FakeData.adminUser
import logic.usecase.FakeData.mateUser
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import util.FakeData

class CreateProjectUseCaseTest() {
    private lateinit var projectRepository: ProjectsRepository
    private lateinit var createProject: CreateProjectUseCase
    private lateinit var validationCreationProjectCreation: ValidationProject

    @BeforeEach
    fun setUp() {
        validationCreationProjectCreation = mockk()
        projectRepository = mockk(relaxed = true)
        createProject = CreateProjectUseCase(projectRepository, validationCreationProjectCreation)
    }

    @Test
    fun `should successfully create a project when user type is admin`() = runTest {
        //Given
        val project = FakeData.validProject
        every { validationCreationProjectCreation.validateCreateProject(project, adminUser) } returns Unit

        //When
        createProject.createProject(project, adminUser)

        //Then
        verify(exactly = 1) { validationCreationProjectCreation.validateCreateProject(project, adminUser) }
        coVerify(exactly = 1) { projectRepository.createProject(project, adminUser) }
    }

    @Test
    fun `should not create a project when user type is mate`() = runTest {
        // Given
        val project = FakeData.validProject
        every { validationCreationProjectCreation.validateCreateProject(project, mateUser) } throws InvalidUserTypeException()
        
        // When & Then
        assertThrows<InvalidUserTypeException> {
            createProject.createProject(project, mateUser)
        }
    }

    @Test
    fun `should create a project successfully when description is empty`() = runTest {
        //Given
        val project = FakeData.projectWithNoDescription
        every { validationCreationProjectCreation.validateCreateProject(project, adminUser) } returns Unit
        
        //When
        createProject.createProject(project, adminUser)

        //Then
        verify(exactly = 1) { validationCreationProjectCreation.validateCreateProject(project, adminUser) }
        coVerify(exactly = 1) { projectRepository.createProject(project, adminUser) }
    }

    @Test
    fun `should throw exception when project title is blank`() = runTest {
        //Given
        val project = FakeData.projectWithNoTitle
        every { validationCreationProjectCreation.validateCreateProject(project, adminUser) } throws TitleException()

        //When & Then
        assertThrows<TitleException> {
            createProject.createProject(project, adminUser)
        }
    }
}