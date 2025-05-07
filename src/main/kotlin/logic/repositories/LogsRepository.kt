package logic.repositories

import logic.model.Log
import java.util.UUID

interface LogsRepository {
    fun getProjectLogs(projectId: UUID): List<Log>
    fun addLog(log: Log)
    fun getTaskLogs(taskId: UUID): List<Log>
}