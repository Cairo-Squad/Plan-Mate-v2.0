package data.repositories

import data.repositories.mappers.toLog
import data.repositories.mappers.toLogDto
import logic.model.Log
import logic.repositories.LogsRepository
import java.util.*

class LogsRepositoryImpl(
    private val csvDataSource: DataSource
) : LogsRepository, BaseRepository() {

    override fun addProjectLog(log: Log) {
        tryToExecute { csvDataSource.addProjectLog(log.toLogDto()) }
    }

    override fun getProjectLog(projectId: UUID): List<Log> {
        return tryToExecute { csvDataSource.getProjectLog(projectId).map { it.toLog() } }
    }

    override fun addTaskLog(log: Log) {
        tryToExecute { csvDataSource.recordLog(log.toLogDto()) }
    }

    override fun getTaskLogs(taskId: UUID): List<Log> {
        return tryToExecute { csvDataSource.getTaskLogs(taskId).map { it.toLog() } }
    }
}