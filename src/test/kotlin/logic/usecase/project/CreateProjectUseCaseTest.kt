package logic.usecase.project

import data.dto.EntityType
import data.dto.UserAction
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import logic.exception.EmptyTitleException
import logic.exception.InvalidUserException
import logic.repositories.ProjectsRepository
import logic.usecase.Log.AddLogUseCase
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import util.FakeData
import util.FakeData.mateUser
import util.FakeData.adminUser

class CreateProjectUseCaseTest() {
    private lateinit var projectRepository: ProjectsRepository
    private lateinit var createProject: CreateProjectUseCase
    private lateinit var addLogUseCase: AddLogUseCase
    private lateinit var validationCreationProjectCreation: ValidationProject

    @BeforeEach
    fun setUp() {
        validationCreationProjectCreation = mockk()
        projectRepository = mockk(relaxed = true)
        addLogUseCase = mockk(relaxed = true)
        createProject = CreateProjectUseCase(projectRepository, addLogUseCase, validationCreationProjectCreation)
    }

    @Test
    fun `should successfully create a project when user type is admin`() {
        //Given
        val project = FakeData.validProject
        every { validationCreationProjectCreation.validateCreateProject(project, adminUser) } returns Unit

        //When
        createProject.createProject(project, adminUser)

        //Then
        verify(exactly = 1) { validationCreationProjectCreation.validateCreateProject(project, adminUser) }
        verify(exactly = 1) { projectRepository.createProject(project, adminUser) }
        verify(exactly = 1) {
          addLogUseCase.addLog(match {
                it.entityId == project.id &&
                        it.entityTitle == project.title &&
                        it.userId == project.createdBy &&
                        it.entityType == EntityType.PROJECT &&
                        it.userAction is UserAction.CreateProject
            })
        }
    }

    @Test
    fun `should not create a project when user type is mate`() {
        // Given
        val project = FakeData.validProject
        every { validationCreationProjectCreation.validateCreateProject(project, mateUser) } throws InvalidUserException()
        
        // When & Then
        assertThrows<InvalidUserException> {
            createProject.createProject(project, mateUser)
        }
    }

    @Test
    fun `should create a project successfully when description is empty`() {
        //Given
        val project = FakeData.projectWithNoDescription
        every { validationCreationProjectCreation.validateCreateProject(project, adminUser) } returns Unit
        
        //When
        createProject.createProject(project, adminUser)

        //Then
        verify(exactly = 1) { validationCreationProjectCreation.validateCreateProject(project, adminUser) }
        verify(exactly = 1) { projectRepository.createProject(project, adminUser) }
        verify(exactly = 1) {
            addLogUseCase.addLog(match {
                it.entityId == project.id &&
                        it.entityTitle == project.title &&
                        it.userId == project.createdBy &&
                        it.entityType == EntityType.PROJECT &&
                        it.userAction is UserAction.CreateProject
            })
        }
    }

    @Test
    fun `should throw exception when project title is blank`() {
        //Given
        val project = FakeData.projectWithNoTitle
        every { validationCreationProjectCreation.validateCreateProject(project, adminUser) } throws EmptyTitleException()

        //When & Then
        assertThrows<EmptyTitleException> {
            createProject.createProject(project, adminUser)
        }
    }
}