package data.repositories

import data.dataSource.remoteDataSource.RemoteDataSource
import data.repositories.mappers.toLog
import data.repositories.mappers.toLogDto
import logic.model.Log
import logic.repositories.TaskLogsRepository
import java.util.*

class TaskLogsRepositoryImpl(
    private val remoteDataSource: RemoteDataSource,
) : TaskLogsRepository, BaseRepository() {

    override suspend fun addLog(log: Log) {
        wrap { remoteDataSource.recordTaskLog(log.toLogDto()) }
    }

    override suspend fun getTaskLogs(taskId: UUID): List<Log> {
        return wrap { remoteDataSource.getTaskLogs(taskId).map { it.toLog() } }
    }
}