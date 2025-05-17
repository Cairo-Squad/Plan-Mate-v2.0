package logic.usecase.log

import logic.model.Log
import logic.repositories.ProjectLogsRepository

class AddProjectLogUseCase (
    private val projectLogsRepository: ProjectLogsRepository
) {
    suspend fun addProjectLog(log: Log) : Unit = projectLogsRepository.addProjectLog(log)
}