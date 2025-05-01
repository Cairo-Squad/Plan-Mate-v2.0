package logic.usecase.project

import logic.model.Log
import logic.repositories.LogsRepository

class AddProjectLogUseCase(private val logsRepository: LogsRepository) {

    fun addProjectLog(log: Log): Result<Unit> {
        return try {
            logsRepository.addProjectLog(log)
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}