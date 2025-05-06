package data.dataSource

import data.database.FileHandler
import data.dto.*
import data.repositories.DataSource
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
        val users = usersCsvHandler.readAll()
        return users
    }

    override fun editUser(user: UserDto) {
        return usersCsvHandler.edit(user)
    }

    override fun editState(state: StateDto) {
        return statesCsvHandler.edit(state)
    }

    override fun deleteUser(user: UserDto) {
        usersCsvHandler.delete(user)
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
        return tasksCsvHandler.readAll().filter { it.projectId == projectId }
    }

    override fun getAllStates(): List<StateDto> {
        return statesCsvHandler.readAll()
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

    override fun editTask(task: TaskDto) {
        tasksCsvHandler.edit(task)
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

    override fun createState(state: StateDto, userDto: UserDto): Boolean {
        statesCsvHandler.write(state)
        return true
    }


    override fun getProjectLogs(projectId: UUID): List<LogDto> {
        return logsCsvHandler.readAll()
            .filter { it.entityType == EntityType.PROJECT && it.entityId == projectId }
    }
}