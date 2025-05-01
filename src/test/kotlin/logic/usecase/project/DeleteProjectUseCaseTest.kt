package logic.usecase.project

import util.FakeData
import com.google.common.truth.Truth.assertThat
import io.mockk.every
import io.mockk.mockk
import logic.repositories.ProjectsRepository
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import java.util.*
import kotlin.NoSuchElementException

class DeleteProjectUseCaseTest {
    private lateinit var projectsRepository: ProjectsRepository
    private lateinit var deleteProjectUseCase: DeleteProjectUseCase

    @BeforeEach
    fun setUp() {
        projectsRepository = mockk(relaxed = true)
        deleteProjectUseCase = DeleteProjectUseCase(projectsRepository)
    }

    @Test
    fun `should return failure when id is not found `() {
        //Given
        val fakeId = UUID.randomUUID()
        every { projectsRepository.deleteProject(fakeId) } returns Result.failure(NoSuchElementException())

        //When
        val result = deleteProjectUseCase.deleteProjectById(fakeId)
        //Then
        assertThat(result.isFailure).isTrue()
    }

    @Test
    fun `should return success when id is found `() {
        //Given
        val validId = FakeData.fakeProjects[0].id
        every { projectsRepository.deleteProject(validId) } returns Result.success(Unit)

        //When
        val result = deleteProjectUseCase.deleteProjectById(validId)
        //Then
        assertThat(result.isSuccess).isTrue()
    }
}
