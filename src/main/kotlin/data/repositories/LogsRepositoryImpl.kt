package data.repositories

import data.repositories.mappers.toLog
import data.repositories.mappers.toLogDto
import logic.model.Log
import logic.repositories.LogsRepository
import java.util.*

class LogsRepositoryImpl(
    private val dataSource: DataSource
) : LogsRepository {

    override fun addProjectLog(log: Log) {
        dataSource.addProjectLog(log.toLogDto())
    }

    override fun getProjectLog(projectId: UUID): List<Log> {
        return dataSource.getProjectLog(projectId).map { it.toLog() }
    }

    override fun addTaskLog(log: Log) {
        dataSource.recordLog(log.toLogDto())
    }

    override fun getTaskLogs(taskId: UUID): List<Log> {
        return dataSource.getTaskLogs(taskId).map { it.toLog() }
    }
}