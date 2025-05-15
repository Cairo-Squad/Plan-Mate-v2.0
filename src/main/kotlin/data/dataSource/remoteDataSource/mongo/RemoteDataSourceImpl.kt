package data.dataSource.remoteDataSource.mongo

import data.dataSource.remoteDataSource.RemoteDataSource
import data.dataSource.remoteDataSource.mongo.handler.MongoDBHandler
import data.dto.*
import data.hashing.PasswordEncryptor
import logic.model.EntityType
import java.util.*

class RemoteDataSourceImpl(
    private val taskLogsHandler : MongoDBHandler<LogDto>,
    private val projectLogsHandler : MongoDBHandler<LogDto>,
    private val projectsHandler : MongoDBHandler<ProjectDto>,
    private val statesHandler : MongoDBHandler<StateDto>,
    private val tasksHandler : MongoDBHandler<TaskDto>,
    private val usersHandler : MongoDBHandler<UserDto>,
    private val passwordEncryptor : PasswordEncryptor
) : RemoteDataSource {
    private var currentUser : UserDto? = null

    override suspend fun getAllUsers() : List<UserDto> {
        return usersHandler.readAll()
    }

    override suspend fun createUser(user : UserDto) : Boolean {
        val updatedUser = user.copy(
            id = UUID.randomUUID(),
            password = passwordEncryptor.hashPassword(user.password)
        )
        return usersHandler.write(updatedUser)
    }

    override suspend fun editUser(user : UserDto) : Boolean {
        return usersHandler.edit(user)
    }

    override suspend fun deleteUser(userId : UUID) : Boolean {
        return usersHandler.delete(userId)
    }

    override suspend fun loginUser(name : String, password : String) : Boolean {
        val users = usersHandler.readAll()
        val user = users.find { it.name == name && it.password == password }
        setCurrentUser(user)
        return user != null
    }

    override suspend fun getCurrentUser() : UserDto? {
        return currentUser
    }

    private fun setCurrentUser(user : UserDto?) {
        currentUser = user
    }


    override suspend fun createProject(project : ProjectDto) : Boolean {
        return projectsHandler.write(project)
    }

    override suspend fun editProject(newProject : ProjectDto) {
        projectsHandler.edit(newProject)
    }

    override suspend fun deleteProjectById(project : ProjectDto) {
        projectsHandler.delete(project.id!!)
    }

    override suspend fun getProjectById(projectId : UUID) : ProjectDto {
        return projectsHandler.readByEntityId(projectId)
    }

    override suspend fun getAllProjects() : List<ProjectDto> {
        return projectsHandler.readAll()
    }

    override suspend fun getTasksByProjectId(projectId : UUID) : List<TaskDto> {
        return tasksHandler.readAll().filter { it.projectId == projectId }
    }

    override suspend fun createTask(task : TaskDto) : Boolean {
        return tasksHandler.write(task)
    }

    override suspend fun getAllTasks() : List<TaskDto> {
        return tasksHandler.readAll()
    }

    override suspend fun editTask(task : TaskDto) {
        tasksHandler.edit(task)
    }

    override suspend fun deleteTask(task : TaskDto) {
        tasksHandler.delete(task.id!!)
    }

    override suspend fun getTaskById(taskID : UUID) : TaskDto {
        return tasksHandler.readByEntityId(taskID)
    }

    override suspend fun getAllStates() : List<StateDto> {
        return statesHandler.readAll()
    }

    override suspend fun getStateById(stateId : UUID) : StateDto {
        return statesHandler.readByEntityId(stateId)
    }

    override suspend fun createState(state : StateDto) : UUID {
        return statesHandler.write(state, "fakeParam").id!!
    }

    override suspend fun editState(state : StateDto) {
        statesHandler.edit(state)
    }

    override suspend fun addTaskLog(log : LogDto) {
        taskLogsHandler.write(log)
    }

    override suspend fun addProjectLog(log : LogDto) {
        projectLogsHandler.write(log)
    }

    override suspend fun getTaskLogs(taskId : UUID) : List<LogDto> {
        return taskLogsHandler.readAll()
            .filter { it.entityType == EntityType.TASK && it.entityId == taskId }
    }

    override suspend fun getProjectLogs(projectId : UUID) : List<LogDto> {
        return projectLogsHandler.readAll()
            .filter { it.entityType == EntityType.PROJECT && it.entityId == projectId }
    }
}