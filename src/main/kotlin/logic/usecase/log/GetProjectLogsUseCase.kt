package logic.usecase.log

import logic.model.Log
import logic.repositories.ProjectLogsRepository
import java.util.*

class GetProjectLogsUseCase(
    private val projectLogsRepository: ProjectLogsRepository
) {
    suspend fun getProjectLogs(projectId: UUID): List<Log> {
        return projectLogsRepository.getProjectLogs(projectId)
    }
}