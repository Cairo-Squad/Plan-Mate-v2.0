package logic.usecase.log

import logic.model.Log
import logic.repositories.TaskLogsRepository

class AddTaskLogUseCase(
    private val logsRepository: TaskLogsRepository
) {
    suspend fun addLog(log: Log) = logsRepository.addLog(log)
}