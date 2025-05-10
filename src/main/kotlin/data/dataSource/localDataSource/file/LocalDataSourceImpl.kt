package data.dataSource.localDataSource.file

import data.dataSource.localDataSource.LocalDataSource
import data.dataSource.localDataSource.file.handler.FileHandler
import data.dto.*
import data.hashing.PasswordEncryptor
import java.util.*

class LocalDataSourceImpl(
    private val logsCsvHandler : FileHandler<LogDto>,
    private val projectsCsvHandler : FileHandler<ProjectDto>,
    private val statesCsvHandler : FileHandler<StateDto>,
    private val tasksCsvHandler : FileHandler<TaskDto>,
    private val usersCsvHandler : FileHandler<UserDto>,
) : LocalDataSource {
    private var currentUser : UserDto? = null

    override fun createUser(id : UUID, name : String, password : String, type : UserType) : Boolean {
        val userDto = UserDto(
            id = UUID.randomUUID(),
            name = name,
            password = password,
            type = type
        )
        return usersCsvHandler.write(userDto)
    }

    override fun getAllUsers() : List<UserDto> {
        val users = usersCsvHandler.readAll()
        return users
    }

    override fun editUser(user : UserDto) {
        return usersCsvHandler.edit(user)
    }

    override fun editState(state : StateDto) {
        return statesCsvHandler.edit(state)
    }

    override fun deleteUser(user : UserDto) {
        usersCsvHandler.delete(user)
    }

    override fun loginUser(name : String, password : String) : Boolean {
        val users = usersCsvHandler.readAll()
        val user = users.find { it.name == name && it.password == password }
        setCurrentUser(user)
        return user != null
    }

    override fun getCurrentUser() : UserDto? {
        return currentUser
    }

    private fun setCurrentUser(user : UserDto?) {
        currentUser = user
    }


    override fun createProject(project : ProjectDto) {
        projectsCsvHandler.write(project)
    }

    override fun editProject(newProject : ProjectDto) {
        projectsCsvHandler.edit(newProject)
    }

    override fun deleteProjectById(project : ProjectDto) {
        projectsCsvHandler.delete(project)
    }

    override fun getProjectById(projectId : UUID) : ProjectDto {
        return projectsCsvHandler.readAll()
            .first { projectDto -> projectDto.id == projectId }
    }

    override fun getAllProjects() : List<ProjectDto> {
        return projectsCsvHandler.readAll()
    }

    override fun getTasksByProjectId(projectId : UUID) : List<TaskDto> {
        return tasksCsvHandler.readAll().filter { it.projectId == projectId }
    }

    override fun getAllStates() : List<StateDto> {
        return statesCsvHandler.readAll()
    }

    override fun recordLog(log : LogDto) {
        logsCsvHandler.write(log)
    }


    override fun getTaskLogs(taskId : UUID) : List<LogDto> {
        return logsCsvHandler.readAll().filter { it.entityType == EntityType.TASK && it.entityId == taskId }
    }

    override fun createTask(task : TaskDto) {
        tasksCsvHandler.write(task)
    }

    override fun editTask(task : TaskDto) {
        tasksCsvHandler.edit(task)
    }

    override fun deleteTask(task : TaskDto) {
        tasksCsvHandler.delete(task)
    }

    override fun getTaskById(taskID : UUID) : TaskDto {
        val task = tasksCsvHandler.readAll().find { it.id == taskID }
        return task!!
    }

    override fun getStateById(stateId : UUID) : StateDto {
        val stateDto = statesCsvHandler.readAll().find { it.id == stateId }
        return stateDto!!
    }

    override fun createState(state : StateDto) : Boolean {
        statesCsvHandler.write(state)
        return true
    }


    override fun getProjectLogs(projectId : UUID) : List<LogDto> {
        return logsCsvHandler.readAll()
            .filter { it.entityType == EntityType.PROJECT && it.entityId == projectId }
    }
}