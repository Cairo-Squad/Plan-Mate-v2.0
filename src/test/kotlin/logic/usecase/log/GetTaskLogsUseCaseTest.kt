package logic.usecase.log

import data.dto.EntityType
import data.dto.UserAction
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import logic.model.Log
import logic.repositories.TaskLogsRepository
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import java.time.LocalDateTime
import java.util.*
import kotlin.test.Test

class GetTaskLogsUseCaseTest {

    private lateinit var logsRepository: TaskLogsRepository
    private lateinit var useCase: GetTaskLogsUseCase

    @BeforeEach
    fun setup() {
        logsRepository = mockk()
        useCase = GetTaskLogsUseCase(logsRepository)
    }

    @Test
    fun `execute should return logs from repository`() = runTest {
        // Given
        val taskId = UUID.randomUUID()
        val expectedLogs = listOf(
            Log(
                id = UUID.randomUUID(),
                entityId = taskId,
                entityTitle = "Task A",
                entityType = EntityType.TASK,
                dateTime = LocalDateTime.now(),
                userId = UUID.randomUUID(),
                userAction = UserAction.EditTask(UUID.randomUUID(), "Changed description")
            )
        )
        coEvery { logsRepository.getTaskLogs(taskId) } returns expectedLogs

        // When
        val actualLogs = useCase.execute(taskId)

        // Then
        assertEquals(expectedLogs, actualLogs)
        coVerify { logsRepository.getTaskLogs(taskId) }
    }
}