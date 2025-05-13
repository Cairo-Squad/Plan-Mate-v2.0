package logic.repositories

import logic.model.Log
import java.util.*

interface TaskLogsRepository {
    suspend fun addLog(log: Log)
    suspend fun getTaskLogs(taskId: UUID): List<Log>
}