package logic.repositories

import logic.model.Log
import java.util.UUID

interface LogsRepository {
    suspend fun getProjectLogs(projectId: UUID): List<Log>
    suspend fun addLog(log: Log)
    suspend fun getTaskLogs(taskId: UUID): List<Log>
}