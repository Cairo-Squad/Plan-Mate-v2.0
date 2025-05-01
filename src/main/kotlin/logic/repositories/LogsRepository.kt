package logic.repositories

import logic.model.Log
import java.util.*

interface LogsRepository {
    fun recordLog(log: Log)
    fun getTaskLogs(taskId: UUID): List<Log>
}