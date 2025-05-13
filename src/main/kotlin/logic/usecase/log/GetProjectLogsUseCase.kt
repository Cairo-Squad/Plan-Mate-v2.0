package logic.usecase.log

import logic.model.Log
import logic.repositories.ProjectLogsRepository
import java.util.*

class GetProjectLogsUseCase(
    private val logsRepository: ProjectLogsRepository
) {
    suspend fun getProjectLogs(projectId: UUID): List<Log> {
        return logsRepository.getProjectLogs(projectId)
    }
}