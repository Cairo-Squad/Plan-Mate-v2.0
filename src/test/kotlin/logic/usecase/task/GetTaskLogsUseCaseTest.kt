//package logic.usecase.task
//
//import com.google.common.truth.Truth
//import data.dto.EntityType
//import data.dto.UserAction
//import io.mockk.every
//import io.mockk.mockk
//import logic.model.Log
//import logic.repositories.LogsRepository
//import logic.usecase.Log.GetTaskLogsUseCase
//import org.junit.jupiter.api.BeforeEach
//import org.junit.jupiter.api.Test
//import org.junit.jupiter.api.assertThrows
//import java.time.LocalDateTime
//import java.util.UUID
//
//class GetTaskLogsUseCaseTest {
//
//    private lateinit var getTaskLogsUseCase: GetTaskLogsUseCase
//    private lateinit var logsRepository: LogsRepository
//
//    @BeforeEach
//    fun setup() {
//        logsRepository = mockk(relaxed = true)
//        getTaskLogsUseCase = GetTaskLogsUseCase(logsRepository)
//    }
//
//    @Test
//    fun `should throw an exception when the logs repository throws an exception`() {
//        // Given
//        every { logsRepository.getTaskLogs(any()) } throws Exception()
//
//        // When, Then
//        assertThrows<Exception> {
//            getTaskLogsUseCase.execute(UUID.randomUUID())
//        }
//    }
//
//    @Test
//    fun `should return an empty list when the there is no logs for this task id`() {
//        // Given
//        every { logsRepository.getTaskLogs(any()) } returns emptyList()
//
//        // When
//        val result = getTaskLogsUseCase.execute(UUID.randomUUID())
//
//        // Then
//        Truth.assertThat(result).isEmpty()
//    }
//
//    @Test
//    fun `should return the logs list when the there is logs for this task id`() {
//        // Given
//        every { logsRepository.getTaskLogs(any()) } returns getValidLogsList()
//
//        // When
//        val result = getTaskLogsUseCase.execute(UUID.randomUUID())
//
//        // Then
//        Truth.assertThat(result).isNotEmpty()
//    }
//
//    private fun getValidLogsList(): List<Log> {
//        return listOf(
//            Log(
//                id = UUID.randomUUID(),
//                entityId = UUID.randomUUID(),
//                entityTitle = "Task 1",
//                entityType = EntityType.TASK,
//                dateTime = LocalDateTime.now(),
//                userId = UUID.randomUUID(),
//                userAction = UserAction.CreateTask(
//                    taskName = "Task 1",
//                    taskId = UUID.randomUUID(),
//                    projectId = UUID.randomUUID()
//                )
//            )
//        )
//    }
//}