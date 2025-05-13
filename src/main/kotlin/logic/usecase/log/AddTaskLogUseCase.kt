package logic.usecase.log

import logic.model.Log
import logic.repositories.TaskLogsRepository

class AddTaskLogUseCase(
    private val taskLogsRepository: TaskLogsRepository
) {
    suspend fun addTaskLog(log: Log) = taskLogsRepository.addTaskLog(log)
}