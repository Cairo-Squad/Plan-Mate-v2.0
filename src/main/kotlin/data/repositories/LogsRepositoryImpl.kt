package data.repositories

import data.dataSource.DataSource
import data.repositories.mappers.toLogDto
import logic.model.Log
import logic.repositories.LogsRepository

class LogsRepositoryImpl(
    private val dataSource: DataSource
) : LogsRepository {
    override fun addProjectLog(log: Log) {
        dataSource.addProjectLog(log.toLogDto())
    }
}