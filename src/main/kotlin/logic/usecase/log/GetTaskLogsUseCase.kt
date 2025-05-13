package logic.usecase.log

import logic.model.Log
import logic.repositories.TaskLogsRepository
import java.util.UUID

class GetTaskLogsUseCase(
    private val taskLogsRepository: TaskLogsRepository
) {
    suspend fun execute(taskId: UUID): List<Log> {
        return taskLogsRepository.getTaskLogs(taskId)
    }
}