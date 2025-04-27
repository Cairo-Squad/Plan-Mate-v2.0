package data.dataSource

import data.database.UsersCsvParser
import data.dto.LogEntityDto
import data.dto.ProjectDto
import data.dto.TaskDto
import data.dto.UserDto

class DataSourceImpl(
    private val usersCsvParser: UsersCsvParser


): DataSource {
    override fun getAllUsers(): List<UserDto> {
        TODO("Not yet implemented")
    }

    override fun getAllProjects(): List<ProjectDto> {
        TODO("Not yet implemented")
    }

    override fun getAllTasks(): List<TaskDto> {
        TODO("Not yet implemented")
    }

    override fun getAllAuditRecords(): List<LogEntityDto> {
        TODO("Not yet implemented")
    }
}