package logic.usecase.log

import logic.model.EntityType
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import logic.model.ActionType
import logic.model.Log
import logic.repositories.ProjectLogsRepository
import org.junit.jupiter.api.BeforeEach
import java.time.LocalDateTime
import java.util.*
import kotlin.test.Test

class AddProjectLogUseCaseTest {

    private lateinit var logsRepository: ProjectLogsRepository
    private lateinit var useCase: AddProjectLogUseCase

    @BeforeEach
    fun setup() {
        logsRepository = mockk(relaxed = true)
        useCase = AddProjectLogUseCase(logsRepository)
    }

    @Test
    fun `addProjectLog should call projectLogsRepository addProjectLog with correct log`() = runTest {
        // Given
        val log = Log(
            id = UUID.randomUUID(),
            entityId = UUID.randomUUID(),
            entityTitle = "Test Project",
            entityType = EntityType.PROJECT,
            dateTime = LocalDateTime.now(),
            userId = UUID.randomUUID(),
            userAction = ActionType.EDIT_PROJECT
        )

        // When
        useCase.addProjectLog(log)

        // Then
        coVerify { logsRepository.addProjectLog(log) }
    }
}