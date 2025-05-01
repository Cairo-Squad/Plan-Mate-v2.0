package logic.usecase.project

import com.google.common.truth.Truth.assertThat
import data.dto.EntityType
import data.dto.UserAction
import io.mockk.every
import io.mockk.mockk
import logic.model.Log
import logic.repositories.LogsRepository
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import java.time.LocalDateTime
import java.util.UUID

class GetProjectLogUseCaseTest {
    private lateinit var getProjectLogUseCase: GetProjectLogUseCase
    private lateinit var logsRepository: LogsRepository

    @BeforeEach
    fun setup() {
        logsRepository = mockk(relaxed = true)
        getProjectLogUseCase = GetProjectLogUseCase(logsRepository)
    }

    @Test
    fun `should throw an exception when the logs repository throws an exception`() {
        // Given
        every { logsRepository.getProjectLog(UUID.randomUUID()) } throws Exception()

        // When, Then
        assertThrows<Exception> {
            getProjectLogUseCase.getProjectLog(UUID.randomUUID())
        }
    }

    @Test
    fun `should return an empty list when the there are no logs for this project`() {
        // Given
        every { logsRepository.getProjectLog(UUID.randomUUID()) } returns emptyList()

        // When
        val result = getProjectLogUseCase.getProjectLog(UUID.randomUUID())

        // Then
        assertThat(result).isEmpty()
    }

    @Test
    fun `should return logs list when the there are logs for project`() {
        // Given
        every { logsRepository.getProjectLog(any()) } returns getValidLogsList()

        // When
        val result = getProjectLogUseCase.getProjectLog(UUID.randomUUID())

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
                userAction = UserAction.EditProjectTitle("New title")
            )
        )
    }
}