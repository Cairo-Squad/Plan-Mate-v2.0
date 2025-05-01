package logic.usecase.project

import logic.model.Log
import logic.repositories.LogsRepository
import java.util.UUID

class GetProjectLogUseCase(
    private val logsRepository: LogsRepository
){
    fun getProjectLog(projectId: UUID): List<Log>{
        return emptyList()
    }
}