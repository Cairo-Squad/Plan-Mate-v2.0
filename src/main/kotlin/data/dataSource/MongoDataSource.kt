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
	
	override fun createUser(id: UUID, name: String, password: String, type: UserType): UserDto {
		TODO("Not yet implemented")
	}
	
	override fun getAllUsers(): List<UserDto> {
		TODO("Not yet implemented")
	}
	
	override fun editUser(user: UserDto) {
		TODO("Not yet implemented")
	}
	
	override fun deleteUser(user: UserDto) {
		TODO("Not yet implemented")
	}
	
	override fun createProject(project: ProjectDto): Result<Unit> {
		projectsHandler.write(project)
		return Result.success(Unit)
	}
	
	override fun editProject(newProject: ProjectDto) {
		projectsHandler.edit(newProject)
	}
	
	override fun deleteProjectById(project: ProjectDto): Result<Unit> {
		projectsHandler.delete(project)
		return Result.success(Unit)
	}
	
	override fun getProjectById(projectId: UUID): ProjectDto {
		return projectsHandler.read(projectId)
	}
	
	override fun getAllProjects(): List<ProjectDto> {
		return projectsHandler.readAll()
	}

	override fun getTasksByProjectId(projectId: UUID): List<TaskDto> {
		return tasksHandler.readAll().filter { it.id == projectId }
	}

	override fun createTask(task: TaskDto): Result<Unit> {
		tasksHandler.write(task)
		return Result.success(Unit)
	}

	override fun editTask(task: TaskDto) {
		tasksHandler.edit(task)
	}

	override fun deleteTask(task: TaskDto) {
		tasksHandler.delete(task)
	}

	override fun getTaskById(taskID: UUID): TaskDto {
		return tasksHandler.read(taskID)
	}
	
	override fun getAllStates(): List<StateDto> {
		return statesHandler.readAll()
	}

	override fun getStateById(stateId: UUID): StateDto {
		return statesHandler.read(stateId)
	}

	override fun createState(state: StateDto): Boolean {
		statesHandler.write(state)
		return true
	}

	override fun editState(state: StateDto) {
		statesHandler.edit(state)
	}
	
	override fun getAllAuditRecords(): List<LogDto> {
		return logsHandler.readAll()
	}
	
	override fun recordLog(log: LogDto) {
		logsHandler.write(log)
	}
	
	override fun getTaskLogs(taskId: UUID): List<LogDto> {
		return logsHandler.readAll().filter { it.entityType == EntityType.TASK && it.entityId == taskId }
	}
	
	override fun addProjectLog(logDto: LogDto) {
		logsHandler.write(logDto)
	}
	
	override fun getProjectLog(projectId: UUID): List<LogDto> {
		return logsHandler.readAll()
			.filter { it.entityType == EntityType.PROJECT && it.entityId == projectId }
	}
}
