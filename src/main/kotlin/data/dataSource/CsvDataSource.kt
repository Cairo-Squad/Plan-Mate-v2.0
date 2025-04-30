package data.dataSource

import data.database.AttributeToBeChanged
import data.database.FileHandler
import data.database.ProjectsCsvHandler
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

    override fun getAllProjects(): List<ProjectDto> {
        TODO("Not yet implemented")
    }

    override fun editProjectTitle(newTitle: String, projectID: UUID) {
        projectsCsvHandler.updateValue(id = projectID, newValue = newTitle, AttributeToBeChanged.TITLE)
    }

    override fun editProjectDescription(newDescription: String, projectID: UUID) {
        projectsCsvHandler.updateValue(id = projectID, newValue = newDescription, AttributeToBeChanged.DESCRIPTION)
    }

    override fun getAllTasks(): List<TaskDto> {
        TODO("Not yet implemented")
    }

    override fun getAllAuditRecords(): List<LogDto> {
        TODO("Not yet implemented")
    }
}