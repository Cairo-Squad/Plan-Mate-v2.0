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

    override fun recordLog(log: LogDto) {
        logsCsvHandler.write(log)
    }

    override fun getTaskLogs(taskId: UUID): List<LogDto> {
        return logsCsvHandler.readAll().filter { it.entityType == EntityType.TASK && it.entityId == taskId }
    }

    override fun createTask(task: TaskDto): Result<Unit> {
        return try {
            tasksCsvHandler.write(task)
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override fun deleteTask(task: TaskDto) {
        tasksCsvHandler.delete(task)
    }
    override fun getTaskById(taskID: UUID): TaskDto {

        val task = tasksCsvHandler.readAll().find { it.id == taskID }
        return task!!

    }

    override fun getStateById(stateId: UUID): StateDto {
        val stateDto = statesCsvHandler.readAll().find { it.id == stateId }
        return stateDto!!
    }

    override fun editUser(user: UserDto) {
        return usersCsvHandler.edit(user)
    }
}