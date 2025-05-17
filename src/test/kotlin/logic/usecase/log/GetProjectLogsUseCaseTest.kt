package logic.usecase.log

import logic.model.EntityType
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import logic.model.ActionType
import logic.model.Log
import logic.repositories.ProjectLogsRepository
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import java.time.LocalDateTime
import java.util.*
import kotlin.test.Test

class GetProjectLogsUseCaseTest {

    private lateinit var logsRepository: ProjectLogsRepository
    private lateinit var useCase: GetProjectLogsUseCase

    @BeforeEach
    fun setup() {
        logsRepository = mockk()
        useCase = GetProjectLogsUseCase(logsRepository)
    }

    @Test
    fun `getProjectLogs should return logs from repository`() = runTest {
        // Given
        val projectId = UUID.randomUUID()
        val expectedLogs = listOf(
            Log(
                id = UUID.randomUUID(),
                entityId = projectId,
                entityTitle = "Project X",
                entityType = EntityType.PROJECT,
                dateTime = LocalDateTime.now(),
                userId = UUID.randomUUID(),
                userAction = ActionType.EDIT_PROJECT
            )
        )
        coEvery { logsRepository.getProjectLogs(projectId) } returns expectedLogs

        // When
        val actualLogs = useCase.getProjectLogs(projectId)

        // Then
        assertEquals(expectedLogs, actualLogs)
        coVerify { logsRepository.getProjectLogs(projectId) }
    }
}