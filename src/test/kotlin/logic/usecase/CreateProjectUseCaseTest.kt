package logic.usecase

import com.google.common.truth.Truth.assertThat
import data.dto.UserType
import io.mockk.mockk
import logic.model.User
import logic.repositories.ProjectsRepository
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import java.util.*
import utils.FakeData

class CreateProjectUseCaseTest() {
    private lateinit var repository: ProjectsRepository
    private lateinit var createProject: CreateProjectUseCase

    @BeforeEach
    fun setUp() {
        repository = mockk(relaxed = true)
        createProject = CreateProjectUseCase(repository)
    }

    @Test
    fun `should successfully create a project when user type is admin`() {
        val user = User(name = "admin", password = "76598", id = UUID.randomUUID(), type = UserType.ADMIN)
        val project = FakeData.validProject
        val result = createProject.createProject(project, user)
        assertThat(result.isSuccess).isTrue()
    }

    @Test
    fun `should not create a project when user type is mate`() {
        val user = User(name = "admin", password = "98543", id = UUID.randomUUID(), type = UserType.MATE)
        val project = FakeData.invalidProject

        val result = createProject.createProject(project, user)
        assertThat(result.isFailure).isTrue()
    }

    /*  @Test
    fun `should create a project when description is empty`() {
        val user = User(name = "admin", password = "98543", id = UUID.randomUUID(), type = UserType.MATE)
        val project = FakeData.projectWithNoDescription

        val result = createProject.createProject(project, user)
        assertThat(result.isSuccess).isTrue()
    }
   */
}