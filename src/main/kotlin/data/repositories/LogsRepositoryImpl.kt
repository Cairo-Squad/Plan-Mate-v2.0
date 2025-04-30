package data.repositories

import data.dataSource.DataSource
import logic.model.Log
import logic.repositories.LogsRepository
import java.util.*

class LogsRepositoryImpl(
    private val dataSource: DataSource
) : LogsRepository {
    override fun createLog(log: Log) {
        TODO("Not yet implemented")
    }

    override fun getTaskLogs(taskId: UUID): List<Log> {
        TODO("Not yet implemented")
    }
}