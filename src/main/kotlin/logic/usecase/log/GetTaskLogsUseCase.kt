package logic.usecase.log

import logic.model.Log
import logic.repositories.TaskLogsRepository
import java.util.UUID

class GetTaskLogsUseCase(
    private val logsRepository: TaskLogsRepository
) {
    suspend fun execute(taskId: UUID): List<Log> {
        return logsRepository.getTaskLogs(taskId)
    }
}