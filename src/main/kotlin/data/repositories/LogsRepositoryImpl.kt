package data.repositories

import data.repositories.mappers.toLog
import data.repositories.mappers.toLogDto
import logic.model.Log
import logic.repositories.LogsRepository
import java.util.*

class LogsRepositoryImpl(
    private val csvDataSource: DataSource
) : LogsRepository {

    override fun addProjectLog(log: Log) {
        csvDataSource.addProjectLog(log.toLogDto())
    }

    override fun getProjectLog(projectId: UUID): List<Log> {
        return csvDataSource.getProjectLog(projectId).map { it.toLog() }
    }

    override fun recordLog(log: Log) {
        csvDataSource.recordLog(log.toLogDto())
    }

    override fun getTaskLogs(taskId: UUID): List<Log> {
        return csvDataSource.getTaskLogs(taskId).map { it.toLog() }
    }
}