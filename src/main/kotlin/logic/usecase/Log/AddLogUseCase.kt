package logic.usecase.Log

import logic.model.Log
import logic.repositories.LogsRepository

class AddLogUseCase(
    private val logsRepository: LogsRepository
) {
    fun recordLog(log: Log): Result<Unit> {
        return try {
            logsRepository.recordLog(log)
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}