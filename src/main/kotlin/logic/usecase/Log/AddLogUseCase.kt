package logic.usecase.Log

import logic.model.Log
import logic.repositories.LogsRepository

class AddLogUseCase(
    private val logsRepository: LogsRepository
) {
    suspend fun addLog(log: Log): Result<Unit> {
        return try {
            logsRepository.addLog(log)
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}
