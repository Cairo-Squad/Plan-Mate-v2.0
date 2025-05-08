package logic.usecase.log

import logic.model.Log
import logic.repositories.LogsRepository

class AddLogUseCase(
    private val logsRepository: LogsRepository
) {
    fun addLog(log: Log) {
        logsRepository.addLog(log)
    }
}