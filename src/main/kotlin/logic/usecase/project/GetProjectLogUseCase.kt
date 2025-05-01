package logic.usecase.project

import logic.model.Log
import logic.repositories.LogsRepository
import java.util.UUID

class GetProjectLogUseCase(
    private val logsRepository: LogsRepository
) {
    fun getProjectLog(projectId: UUID): List<Log> {
        try {
            return logsRepository.getProjectLog(projectId)
        } catch (exception: Exception) {
            throw Exception("An error happened! Please try again.")
        }
    }
}