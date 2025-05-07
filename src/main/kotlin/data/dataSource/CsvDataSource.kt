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
    override suspend fun createUser(id: UUID, name: String, password: String, type: UserType): UserDto {
        val userDto = UserDto(
            id = UUID.randomUUID(),
            name = name,
            password = password,
            type = type
        )
        usersCsvHandler.write(userDto)
        return userDto
    }

    override suspend fun getAllUsers(): List<UserDto> {
        val users = usersCsvHandler.readAll()
        return users
    }

    override suspend fun editUser(user: UserDto) {
        return usersCsvHandler.edit(user)
    }

    override suspend fun editState(state: StateDto) {
        return statesCsvHandler.edit(state)
    }

    override suspend fun deleteUser(user: UserDto) {
        usersCsvHandler.delete(user)
    }

    override suspend fun createProject(project: ProjectDto) {
        projectsCsvHandler.write(project)
    }

    override suspend fun editProject(newProject: ProjectDto) {
        projectsCsvHandler.edit(newProject)
    }

    override suspend fun deleteProjectById(project: ProjectDto) {
        projectsCsvHandler.delete(project)
    }

    override suspend fun getProjectById(projectId: UUID): ProjectDto {
        return projectsCsvHandler.readAll()
            .first { projectDto -> projectDto.id == projectId }
    }

    override suspend fun getAllProjects(): List<ProjectDto> {
        return projectsCsvHandler.readAll()
    }

    override suspend fun getTasksByProjectId(projectId: UUID): List<TaskDto> {
        return tasksCsvHandler.readAll().filter { it.projectId == projectId }
    }

    override suspend fun getAllStates(): List<StateDto> {
        return statesCsvHandler.readAll()
    }

    override suspend fun recordLog(log: LogDto) {
        logsCsvHandler.write(log)
    }



    override suspend fun getTaskLogs(taskId: UUID): List<LogDto> {
        return logsCsvHandler.readAll().filter { it.entityType == EntityType.TASK && it.entityId == taskId }
    }

    override suspend fun createTask(task: TaskDto) {
        tasksCsvHandler.write(task)
    }

    override suspend fun editTask(task: TaskDto) {
        tasksCsvHandler.edit(task)
    }

    override suspend fun deleteTask(task: TaskDto) {
        tasksCsvHandler.delete(task)
    }

    override suspend fun getTaskById(taskID: UUID): TaskDto {
        val task = tasksCsvHandler.readAll().find { it.id == taskID }
        return task!!
    }

    override suspend fun getStateById(stateId: UUID): StateDto {
        val stateDto = statesCsvHandler.readAll().find { it.id == stateId }
        return stateDto!!
    }

    override suspend fun createState(state: StateDto): Boolean {
        statesCsvHandler.write(state)
        return true
    }


    override suspend fun getProjectLogs(projectId: UUID): List<LogDto> {
        return logsCsvHandler.readAll()
            .filter { it.entityType == EntityType.PROJECT && it.entityId == projectId }
    }
}