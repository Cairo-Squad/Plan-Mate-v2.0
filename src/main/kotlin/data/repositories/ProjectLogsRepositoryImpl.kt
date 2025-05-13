package data.repositories

import data.dataSource.remoteDataSource.RemoteDataSource
import data.repositories.mappers.toLog
import data.repositories.mappers.toLogDto
import logic.model.Log
import logic.repositories.ProjectLogsRepository
import java.util.*

class ProjectLogsRepositoryImpl(
    private val remoteDataSource: RemoteDataSource,
) : ProjectLogsRepository, BaseRepository() {

    override suspend fun addLog(log: Log) {
        wrap { remoteDataSource.recordProjectLog(log.toLogDto()) }
    }

    override suspend fun getProjectLogs(projectId: UUID): List<Log> {
        return wrap { remoteDataSource.getProjectLogs(projectId).map { it.toLog() } }
    }
}