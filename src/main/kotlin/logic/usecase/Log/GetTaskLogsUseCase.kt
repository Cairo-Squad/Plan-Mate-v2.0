package logic.usecase.Log

import logic.model.Log
import logic.repositories.LogsRepository
import java.util.UUID

class GetTaskLogsUseCase(
    private val logsRepository: LogsRepository
) {
    fun execute(taskId: UUID): List<Log> {
        return logsRepository.getTaskLogs(taskId)
    }
}