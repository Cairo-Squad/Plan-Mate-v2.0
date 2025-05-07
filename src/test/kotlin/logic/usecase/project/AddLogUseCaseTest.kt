package logic.usecase.project

import data.dto.EntityType
import data.dto.UserAction
import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import logic.exception.UnknownException
import logic.exception.WriteException
import logic.model.Log
import logic.repositories.LogsRepository
import logic.usecase.Log.AddLogUseCase
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import java.time.LocalDateTime
import java.util.*
import kotlin.test.assertEquals

class AddLogUseCaseTest {
    private val logsRepository = mockk<LogsRepository>(relaxed = true)
    private lateinit var addLogUseCase: AddLogUseCase

    @BeforeEach
    fun setup() {
        addLogUseCase = AddLogUseCase(logsRepository)
    }

    @Test
    fun `Given a project log,When adding to database,Then operation succedded`() = runTest {
        //Given
        val projectLog = createLog()
        //When
        val result = addLogUseCase.addLog(projectLog)
        //Then
        assertEquals(expected = Result.success(Unit), actual = result)
    }

    @Test
    fun `Given a project log,When adding to database,Then return failure`() = runTest {
        //Given
        coEvery { logsRepository.addLog(any()) } throws WriteException()
        val projectLog = createLog()
        //When
        val result = addLogUseCase.addLog(projectLog)
        //Then
        val expected = Result.failure<Exception>(WriteException())
        assertEquals(expected = expected.exceptionOrNull()!!::class, actual = result.exceptionOrNull()!!::class)
    }

    @Test
    fun `Given a project log,When adding to database,Then return  unKnown failure`() = runTest {
        //Given
        coEvery { logsRepository.addLog(any()) } throws UnknownException()
        val projectLog = createLog()
        //When
        val result = addLogUseCase.addLog(projectLog)
        //Then
        val expected = Result.failure<Exception>(UnknownException())
        assertEquals(expected = expected.exceptionOrNull()!!::class, actual = result.exceptionOrNull()!!::class)
    }

    private fun createLog() = Log(
        id = UUID.randomUUID(),
        entityId = UUID.randomUUID(),
        entityTitle = "Entity Title",
        entityType = EntityType.PROJECT,
        dateTime = LocalDateTime.now(),
        userId = UUID.randomUUID(),
        userAction = UserAction.EditProjectTitle(oldName = "Old name", newName = "New name")
    )
}