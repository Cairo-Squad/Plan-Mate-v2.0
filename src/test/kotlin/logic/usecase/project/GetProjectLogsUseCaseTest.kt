package logic.usecase.project

import com.google.common.truth.Truth.assertThat
import data.dto.EntityType
import data.dto.UserAction
import io.mockk.every
import io.mockk.mockk
import logic.model.Log
import logic.repositories.LogsRepository
import logic.usecase.Log.GetProjectLogsUseCase
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import java.time.LocalDateTime
import java.util.UUID

class GetProjectLogsUseCaseTest {
    private lateinit var getProjectLogsUseCase: GetProjectLogsUseCase
    private lateinit var logsRepository: LogsRepository

    @BeforeEach
    fun setup() {
        logsRepository = mockk(relaxed = true)
        getProjectLogsUseCase = GetProjectLogsUseCase(logsRepository)
    }

    @Test
    fun `should return an empty list when the there are no logs for this project`() {
        // Given
        every { logsRepository.getProjectLogs(UUID.randomUUID()) } returns emptyList()

        // When
        val result = getProjectLogsUseCase.getProjectLogs(UUID.randomUUID())

        // Then
        assertThat(result).isEmpty()
    }

    @Test
    fun `should return logs list when the there are logs for project`() {
        // Given
        every { logsRepository.getProjectLogs(any()) } returns getValidLogsList()

        // When
        val result = getProjectLogsUseCase.getProjectLogs(UUID.randomUUID())

        // Then
        assertThat(result).isNotEmpty()
    }

    private fun getValidLogsList(): List<Log> {
        return listOf(
            Log(
                id = UUID.randomUUID(),
                entityId = UUID.randomUUID(),
                entityTitle = "Project",
                entityType = EntityType.PROJECT,
                dateTime = LocalDateTime.now(),
                userId = UUID.randomUUID(),
                userAction = UserAction.EditProjectTitle(oldName = "Old title", newName = "New title")
            )
        )
    }
}