package logic.usecase

import logic.model.Log
import logic.repositories.LogsRepository
import java.util.UUID

class GetTaskLogsUseCase(
    private val logsRepository: LogsRepository
) {
    fun execute(taskId: UUID): List<Log> {
        return emptyList()
    }
}