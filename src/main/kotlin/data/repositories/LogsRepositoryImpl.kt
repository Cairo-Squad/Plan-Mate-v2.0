package data.repositories

import data.dataSource.DataSource
import data.repositories.mappers.toLog
import data.repositories.mappers.toLogDto
import logic.model.Log
import logic.repositories.LogsRepository
import java.util.*

class LogsRepositoryImpl(
    private val dataSource: DataSource
) : LogsRepository {
    override fun createLog(log: Log) {
        val logDto = log.toLogDto()
        dataSource.createLog(logDto)
    }

    override fun getTaskLogs(taskId: UUID): List<Log> {
        val taskLogsDto = dataSource.getTaskLogs(taskId)
        return taskLogsDto.map { it.toLog() }
    }
}