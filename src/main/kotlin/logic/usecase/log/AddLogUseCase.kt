package logic.usecase.log

import logic.model.Log
import logic.repositories.LogsRepository

class AddLogUseCase(
    private val logsRepository: LogsRepository
) {
    suspend fun addLog(log: Log) {
        logsRepository.addLog(log)
    }
}
