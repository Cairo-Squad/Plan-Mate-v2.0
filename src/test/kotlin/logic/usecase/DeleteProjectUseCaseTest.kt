package logic.usecase

import utils.FakeData
import com.google.common.truth.Truth.assertThat
import io.mockk.every
import io.mockk.mockk
import logic.repositories.ProjectsRepository
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
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
    fun `should return false when id is not found `() {

        val fakeId = UUID.randomUUID()
        //Given
        every { projectsRepository.getProject() } returns FakeData.fakeProjects

        //When
        val result = deleteProjectUseCase.deleteProjectById(fakeId)
        //Then
        assertThat(result).isFalse()
    }

    @Test
    fun `should return true when id is found `() {

        val validId = UUID.randomUUID()
        //Given
        every { projectsRepository.getProject() } returns FakeData.fakeProjects

        //When
        val result = deleteProjectUseCase.deleteProjectById(validId)
        //Then
        assertThat(result).isTrue()
    }
}