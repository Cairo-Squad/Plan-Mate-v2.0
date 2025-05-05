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
	
	override fun editState(state: StateDto) {
		TODO("Not yet implemented")
	}
	
	override fun deleteUser(user: UserDto) {
		TODO("Not yet implemented")
	}
	
	override fun createProject(project: ProjectDto): Result<Unit> {
		TODO("Not yet implemented")
	}
	
	override fun editProject(newProject: ProjectDto) {
		TODO("Not yet implemented")
	}
	
	override fun deleteProjectById(project: ProjectDto): Result<Unit> {
		TODO("Not yet implemented")
	}
	
	override fun getProjectById(projectId: UUID): ProjectDto {
		TODO("Not yet implemented")
	}
	
	override fun getAllProjects(): List<ProjectDto> {
		TODO("Not yet implemented")	}
	
	override fun getTasksByProjectId(projectId: UUID): List<TaskDto> {
		TODO("Not yet implemented")	}
	
	override fun getAllStates(): List<StateDto> {
		TODO("Not yet implemented")	}
	
	override fun getAllAuditRecords(): List<LogDto> {
		TODO("Not yet implemented")	}
	
	override fun recordLog(log: LogDto) {
		TODO("Not yet implemented")	}
	
	override fun getTaskLogs(taskId: UUID): List<LogDto> {
		TODO("Not yet implemented")	}
	
	override fun createTask(task: TaskDto): Result<Unit> {
		TODO("Not yet implemented")
	}
	
	override fun editTask(task: TaskDto) {
		TODO("Not yet implemented")	}
	
	override fun deleteTask(task: TaskDto) {
		TODO("Not yet implemented")	}
	
	override fun getTaskById(taskID: UUID): TaskDto {
		TODO("Not yet implemented")	}
	
	override fun getStateById(stateId: UUID): StateDto {
		TODO("Not yet implemented")	}
	
	override fun createState(state: StateDto, userDto: UserDto): Boolean {
		TODO("Not yet implemented")
	}
	
	override fun addProjectLog(logDto: LogDto) {
		TODO("Not yet implemented")	}
	
	override fun getProjectLog(projectId: UUID): List<LogDto> {
		TODO("Not yet implemented")
	}
}
