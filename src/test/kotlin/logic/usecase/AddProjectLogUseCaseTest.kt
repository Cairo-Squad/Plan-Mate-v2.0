package logic.usecase

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
import java.util.*
import kotlin.test.assertEquals

class AddProjectLogUseCaseTest {
    private val logsRepository = mockk<LogsRepository>(relaxed = true)
    private lateinit var addProjectLogUseCase: AddProjectLogUseCase

    @BeforeEach
    fun setup() {
        addProjectLogUseCase = AddProjectLogUseCase(logsRepository)
    }

    @Test
    fun `Given a project log,When adding to database,Then operation succedded`() {
        //Given
        val projectLog = createLog()
        //When
        val result = addProjectLogUseCase.addProjectLog(projectLog)
        //Then
        assertEquals(expected = Result.success(Unit), actual = result)
    }

    @Test
    fun `Given a project log,When adding to database,Then return failure`() {
        //Given
        every { logsRepository.addProjectLog(any()) } throws Exception()
        val projectLog = createLog()
        //When
        val result = addProjectLogUseCase.addProjectLog(projectLog)
        //Then
        assertEquals(expected = Result.failure(Exception()), actual = result)
    }

    private fun createLog() = Log(
        id = UUID.randomUUID(),
        entityId = UUID.randomUUID(),
        entityTitle = "Entity Title",
        entityType = EntityType.PROJECT,
        dateTime = LocalDateTime.now(),
        userId = UUID.randomUUID(),
        userAction = UserAction.EditProjectTitle("Old name")
    )


}