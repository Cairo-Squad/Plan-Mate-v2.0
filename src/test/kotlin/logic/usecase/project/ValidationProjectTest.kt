package logic.usecase.project

import data.dto.UserType
import logic.exception.EmptyNameException
import logic.exception.EmptyTitleException
import logic.exception.InvalidUserException
import logic.model.Project
import logic.model.User
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import java.util.*

class ValidationProjectTest {

    private lateinit var validationProject: ValidationProject

    private val adminUser = User(
        id = UUID.randomUUID(),
        name = "Admin",
        type = UserType.ADMIN
    )

    private val mateUser = User(
        id = UUID.randomUUID(),
        name = "Mate",
        type = UserType.MATE
    )

    @BeforeEach
    fun setUp() {
        validationProject = ValidationProject()
    }

    @Test
    fun `should pass validation when project title is not blank and user is admin`() {
        // Given
        val project = Project(
            id = UUID.randomUUID(),
            title = "Project Title",
            description = "description"
        )

        // When & Then
        validationProject.validateCreateProject(project, adminUser)
    }

    @Test
    fun `should throw InvalidUserException when user is not admin`() {
        // Given
        val project = Project(
            id = UUID.randomUUID(),
            title = "Project Title",
            description = "description"
        )

        // When & Then
        assertThrows<InvalidUserException> {
            validationProject.validateCreateProject(project, mateUser)
        }
    }

    @Test
    fun `should throw EmptyTitleException when project title is blank`() {
        // Given
        val project = Project(
            id = UUID.randomUUID(),
            title = " ",
            description = "description"
        )

        // When & Then
        assertThrows<EmptyTitleException> {
            validationProject.validateCreateProject(project, adminUser)
        }
    }

    @Test
    fun `should pass validation when editing project with valid title`() {
        // Given
        val project = Project(
            id = UUID.randomUUID(),
            title = "Updated Title",
            description = null
        )

        // When & Then
        validationProject.validateEditProject(project)
    }

    @Test
    fun `should throw EmptyNameException when editing project with blank title`() {
        // Given
        val project = Project(
            id = UUID.randomUUID(),
            title = " ",
            description = null
        )

        // When & Then
        assertThrows<EmptyNameException> {
            validationProject.validateEditProject(project)
        }
    }
}
