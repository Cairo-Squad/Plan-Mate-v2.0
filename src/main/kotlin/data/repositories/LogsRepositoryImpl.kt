package data.repositories

import data.dataSource.DataSource
import logic.repositories.LogsRepository

class LogsRepositoryImpl(
    private val dataSource: DataSource
) : LogsRepository {
}