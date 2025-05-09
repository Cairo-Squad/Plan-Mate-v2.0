package data.repositories

import data.dataSource.remoteDataSource.mongo.RemoteDataSource
import data.repositories.mappers.toLog
import data.repositories.mappers.toLogDto
import logic.model.Log
import logic.repositories.LogsRepository
import java.util.*

class LogsRepositoryImpl(
    private val remoteDataSource: RemoteDataSource,
) : LogsRepository {

    override suspend fun getProjectLogs(projectId: UUID): List<Log> {
        return remoteDataSource.getProjectLogs(projectId).map { it.toLog() }
    }

    override suspend fun addLog(log: Log) {
        remoteDataSource.recordLog(log.toLogDto())
    }

    override suspend fun getTaskLogs(taskId: UUID): List<Log> {
        return remoteDataSource.getTaskLogs(taskId).map { it.toLog() }
    }
}