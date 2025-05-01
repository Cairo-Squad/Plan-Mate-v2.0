package logic.repositories

import logic.model.Log
import java.util.UUID

interface LogsRepository {
    fun addProjectLog(log: Log)
    fun getProjectLog(projectId: UUID): List<Log>
}