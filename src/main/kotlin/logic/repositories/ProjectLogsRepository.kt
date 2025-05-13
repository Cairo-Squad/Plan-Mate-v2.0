package logic.repositories

import logic.model.Log
import java.util.*

interface ProjectLogsRepository {
    suspend fun addLog(log: Log)
    suspend fun getProjectLogs(projectId: UUID): List<Log>
}