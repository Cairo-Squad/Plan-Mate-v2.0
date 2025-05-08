package logic.repositories

import logic.model.Log
import java.util.UUID

interface LogsRepository {
    fun addLog(log: Log)
    fun getProjectLogs(projectId: UUID): List<Log>
    fun getTaskLogs(taskId: UUID): List<Log>
}