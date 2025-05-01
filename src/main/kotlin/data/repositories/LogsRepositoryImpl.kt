package data.repositories

import data.dataSource.DataSource
import data.repositories.mappers.toLog
import logic.model.Log
import logic.repositories.LogsRepository
import java.util.UUID

class LogsRepositoryImpl(
    private val dataSource: DataSource
) : LogsRepository {
    override fun getProjectLog(projectId: UUID): List<Log> {
        return dataSource.getProjectLog(projectId).map { it.toLog() }
    }
}