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
    override fun createUser(id: UUID, name: String, password: String, type: UserType): UserDto {
        val userDto = UserDto(
            id = UUID.randomUUID(),
            name = name,
            password = password,
            type = type
        )
        
        usersCsvHandler.write(userDto)
        return userDto
    }
    
    override fun getAllUsers(): List<UserDto> {
        TODO("Not yet implemented")
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