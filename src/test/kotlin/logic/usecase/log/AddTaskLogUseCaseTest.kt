package logic.usecase.log

import logic.model.EntityType
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import logic.model.ActionType
import logic.model.Log
import logic.repositories.TaskLogsRepository
import org.junit.jupiter.api.BeforeEach
import java.time.LocalDateTime
import java.util.*
import kotlin.test.Test

class AddTaskLogUseCaseTest {

    private lateinit var logsRepository: TaskLogsRepository
    private lateinit var useCase: AddTaskLogUseCase

    @BeforeEach
    fun setup() {
        logsRepository = mockk(relaxed = true)
        useCase = AddTaskLogUseCase(logsRepository)
    }

    @Test
    fun `addTaskLog should call taskLogsRepository addTaskLog with correct log`() = runTest {
        // Given
        val log = Log(
            id = UUID.randomUUID(),
            entityId = UUID.randomUUID(),
            entityTitle = "Generic Entity",
            entityType = EntityType.TASK,
            dateTime = LocalDateTime.now(),
            userId = UUID.randomUUID(),
            userAction = ActionType.CREATE_TASK
        )

        // When
        useCase.addTaskLog(log)

        // Then
        coVerify { logsRepository.addTaskLog(log) }
    }
}