package data.dataSource

import data.database.FileHandler
import data.dto.*
import java.util.*

class CsvDataSource(
    private val logsCsvHandler: FileHandler<LogDto>,
    private val projectsCsvHandler: FileHandler<ProjectDto>,
    private val statesCsvHandler: FileHandler<StateDto>,
    private val tasksCsvHandler: FileHandler<TaskDto>,
    private val usersCsvHandler: FileHandler<UserDto>
) : DataSource {
    override fun getAllUsers(): List<UserDto> {
        TODO("Not yet implemented")
    }

    override fun createProject(project: ProjectDto): Result<Unit> {
        return try {
            projectsCsvHandler.write(project)
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override fun editProject(newProject: ProjectDto) {
        projectsCsvHandler.edit(newProject)
    }

    override fun deleteProjectById(project: ProjectDto): Result<Unit> {
        return try {
            projectsCsvHandler.delete(project)
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override fun getProjectById(projectId: UUID): ProjectDto {
        return projectsCsvHandler.readAll()
            .first { projectDto -> projectDto.id == projectId }
    }

    override fun getAllProjects(): List<ProjectDto> {
        return projectsCsvHandler.readAll()
    }

    override fun getTasksByProjectId(projectId: UUID): List<TaskDto> {
        return tasksCsvHandler.readAll().filter { it.id == projectId }
    }

    override fun getAllStates(): List<StateDto> {
        return statesCsvHandler.readAll()
    }

    override fun getAllAuditRecords(): List<LogDto> {
        TODO("Not yet implemented")
    }

    override fun addProjectLog(logDto: LogDto) {
        logsCsvHandler.write(logDto)
    }

    override fun getProjectLog(projectId: UUID): List<LogDto> {
        return logsCsvHandler.readAll()
            .filter { it.entityType == EntityType.PROJECT && it.entityId == projectId }
    }
}