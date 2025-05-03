package logic.usecase.Log

import logic.model.Log
import logic.repositories.LogsRepository
import java.util.UUID

class GetTaskLogsUseCase(
    private val logsRepository: LogsRepository
) {
    fun execute(taskId: UUID): List<Log> {
        try {
            return logsRepository.getTaskLogs(taskId)
        } catch (exception: Exception) {
            throw Exception("An error happened! Please try again.")
        }
    }
}