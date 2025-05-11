package data.repositories

import data.dataSource.remoteDataSource.RemoteDataSource
import data.repositories.mappers.toLog
import data.repositories.mappers.toLogDto
import logic.model.Log
import logic.repositories.LogsRepository
import java.util.*

class LogsRepositoryImpl(
    private val remoteDataSource: RemoteDataSource,
) : LogsRepository, BaseRepository() {

    override suspend fun getProjectLogs(projectId: UUID): List<Log> {
        return wrap { remoteDataSource.getProjectLogs(projectId).map { it.toLog() } }
    }

    override suspend fun addLog(log: Log) {
        wrap { remoteDataSource.recordLog(log.toLogDto()) }
    }

    override suspend fun getTaskLogs(taskId: UUID): List<Log> {
        return wrap { remoteDataSource.getTaskLogs(taskId).map { it.toLog() } }
    }
}