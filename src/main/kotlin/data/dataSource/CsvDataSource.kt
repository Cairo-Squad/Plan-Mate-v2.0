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
        val users= usersCsvHandler.readAll()
        return users
    }

    override fun deleteUser(user:UserDto) {
        usersCsvHandler.delete(user)
    }


    override fun getAllProjects(): List<ProjectDto> {
        TODO("Not yet implemented")
    }

    override fun getAllTasks(): List<TaskDto> {
        TODO("Not yet implemented")
    }

    override fun getAllAuditRecords(): List<LogDto> {
        TODO("Not yet implemented")
    }
}