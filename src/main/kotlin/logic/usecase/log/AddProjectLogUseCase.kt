package logic.usecase.log

import logic.model.Log
import logic.repositories.ProjectLogsRepository

class AddProjectLogUseCase (
    private val logsRepository: ProjectLogsRepository
) {
    suspend fun addLog(log: Log) = logsRepository.addLog(log)
}