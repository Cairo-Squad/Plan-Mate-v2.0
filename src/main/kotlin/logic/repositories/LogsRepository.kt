package logic.repositories

import logic.model.Log
import java.util.*

interface LogsRepository {
    fun createLog(log: Log)
    fun getTaskLogs(taskId: UUID): List<Log>
}