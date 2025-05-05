package logic.repositories

import logic.model.Log
import java.util.UUID

interface LogsRepository {
    fun getProjectLog(projectId: UUID): List<Log>
    fun recordLog(log: Log)
    fun getTaskLogs(taskId: UUID): List<Log>
}