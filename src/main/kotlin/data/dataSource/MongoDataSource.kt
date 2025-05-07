package data.dataSource

import com.mongodb.client.MongoDatabase
import data.database.mongo.*
import data.dto.*
import data.repositories.DataSource
import java.util.*

class MongoDataSource(
	database: MongoDatabase
) : DataSource {
	private val logsHandler = LogsMongoHandlerImpl(database)
	private val projectsHandler = ProjectsMongoHandlerImpl(database)
	private val statesHandler = StatesMongoHandlerImpl(database)
	private val tasksHandler = TasksMongoHandlerImpl(database)
	private val usersHandler = UsersMongoHandlerImpl(database)

	override suspend fun getAllUsers(): List<UserDto> {
		return usersHandler.readAll()
	}

	override suspend fun createUser(id: UUID, name: String, password: String, type: UserType): UserDto {
		val userDto = UserDto(
			id = UUID.randomUUID(),
			name = name,
			password = password,
			type = type
		)

		usersHandler.write(userDto)
		return userDto
	}

	override suspend fun editUser(user: UserDto) {
		usersHandler.edit(user)
	}
	
	override suspend fun deleteUser(user: UserDto) {
		usersHandler.delete(user)
	}
	
	override suspend fun createProject(project: ProjectDto) {
		projectsHandler.write(project)
	}
	
	override suspend fun editProject(newProject: ProjectDto) {
		projectsHandler.edit(newProject)
	}
	
	override suspend fun deleteProjectById(project: ProjectDto) {
		projectsHandler.delete(project)
	}
	
	override suspend fun getProjectById(projectId: UUID): ProjectDto {
		return projectsHandler.readByEntityId(projectId)
	}
	
	override suspend fun getAllProjects(): List<ProjectDto> {
		return projectsHandler.readAll()
	}

	override suspend fun getTasksByProjectId(projectId: UUID): List<TaskDto> {
		return tasksHandler.readAll().filter { it.projectId == projectId }
	}

	override suspend fun createTask(task: TaskDto) {
		tasksHandler.write(task)
	}

	override suspend fun editTask(task: TaskDto) {
		tasksHandler.edit(task)
	}

	override suspend fun deleteTask(task: TaskDto) {
		tasksHandler.delete(task)
	}

	override suspend fun getTaskById(taskID: UUID): TaskDto {
		return tasksHandler.readByEntityId(taskID)
	}
	
	override suspend fun getAllStates(): List<StateDto> {
		return statesHandler.readAll()
	}

	override suspend fun getStateById(stateId: UUID): StateDto {
		return statesHandler.readByEntityId(stateId)
	}

	override suspend fun createState(state: StateDto): Boolean {
		statesHandler.write(state)
		return true
	}

	override suspend fun editState(state: StateDto) {
		statesHandler.edit(state)
	}
	
	override suspend fun recordLog(log: LogDto) {
		logsHandler.write(log)
	}
	
	override suspend fun getTaskLogs(taskId: UUID): List<LogDto> {
		return logsHandler.readAll().filter { it.entityType == EntityType.TASK && it.entityId == taskId }
	}

	override suspend fun getProjectLogs(projectId: UUID): List<LogDto> {
		return logsHandler.readAll()
			.filter { it.entityType == EntityType.PROJECT && it.entityId == projectId }
	}
}