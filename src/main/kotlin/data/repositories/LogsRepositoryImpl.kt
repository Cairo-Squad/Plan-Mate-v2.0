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
        tryToExecute { csvDataSource.addProjectLog(log.toLogDto()) }
    }

    override fun getProjectLog(projectId: UUID): List<Log> {
        return tryToExecute { csvDataSource.getProjectLog(projectId).map { it.toLog() } }
    }

    override fun recordLog(log: Log) {
        tryToExecute { csvDataSource.recordLog(log.toLogDto()) }
    }

    override fun getTaskLogs(taskId: UUID): List<Log> {
        return tryToExecute { csvDataSource.getTaskLogs(taskId).map { it.toLog() } }
    }

    private fun <T> tryToExecute(function: () -> T): T {
        return try {
            function()
        } catch (e: Exception) {
            throw e
        }
    }
}