package logic.usecase.Log

import logic.model.Log
import logic.repositories.LogsRepository
import java.util.*

class GetProjectLogUseCase(
    private val logsRepository: LogsRepository
) {
    suspend fun getProjectLogs(projectId: UUID): List<Log> {
        return logsRepository.getProjectLogs(projectId)
    }
}