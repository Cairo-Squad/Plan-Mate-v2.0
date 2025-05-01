package data.dataSource

import data.database.FileHandler
import data.dto.*

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

    override fun getAllProjects(): List<ProjectDto> {
       return projectsCsvHandler.readAll()
    }

    override fun getAllTasks(): List<TaskDto> {
        TODO("Not yet implemented")
    }

    override fun getAllAuditRecords(): List<LogDto> {
        TODO("Not yet implemented")
    }
    override fun deleteProjectById(project: ProjectDto):Result<Unit>{
        return try {
            projectsCsvHandler.delete(project)
            Result.success(Unit)
        }catch (e:Exception){
            Result.failure(e)
        }
    }

    override fun getAllStates(): List<StateDto> {
        return statesCsvHandler.readAll()
    }
}