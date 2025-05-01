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
    override fun addProjectLog(log: Log) {
        dataSource.addProjectLog(log.toLogDto())
    }
    override fun getProjectLog(projectId: UUID): List<Log> {
        return dataSource.getProjectLog(projectId).map { it.toLog() }
    }
}