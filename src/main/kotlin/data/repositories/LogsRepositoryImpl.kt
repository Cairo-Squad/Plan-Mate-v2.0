package data.repositories

import data.repositories.mappers.toLog
import data.repositories.mappers.toLogDto
import logic.model.Log
import logic.repositories.LogsRepository
import java.util.*

class LogsRepositoryImpl(
    private val dataSource: DataSource
) : LogsRepository {

    override suspend fun getProjectLogs(projectId: UUID): List<Log> {
        return dataSource.getProjectLogs(projectId).map { it.toLog() }
    }

    override suspend fun addLog(log: Log) {
        dataSource.recordLog(log.toLogDto())
    }

    override suspend fun getTaskLogs(taskId: UUID): List<Log> {
        return dataSource.getTaskLogs(taskId).map { it.toLog() }
    }
}