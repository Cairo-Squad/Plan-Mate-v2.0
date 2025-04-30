package data.dataSource

import data.database.FileHandler
import data.dto.*
import data.repositories.mappers.toProjectDto
import logic.model.Project

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
        TODO("Not yet implemented")
    }

    override fun editProject(newProject: Project) {
        projectsCsvHandler.edit(newProject.toProjectDto())
    }


    override fun getAllTasks(): List<TaskDto> {
        TODO("Not yet implemented")
    }

    override fun getAllAuditRecords(): List<LogDto> {
        TODO("Not yet implemented")
    }
}