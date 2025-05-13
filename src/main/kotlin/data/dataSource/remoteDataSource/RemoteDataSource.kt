package data.dataSource.remoteDataSource

import data.dto.*
import java.util.*

interface RemoteDataSource {
    // region Users
    suspend fun getAllUsers(): List<UserDto>
    suspend fun createUser(user: UserDto): Boolean
    suspend fun editUser(user: UserDto): Boolean
    suspend fun deleteUser(user: UserDto): Boolean
    suspend fun loginUser(name: String, password: String): Boolean
    suspend fun getCurrentUser(): UserDto?

	// endregion
	
	// region Projects
	suspend fun getAllProjects(): List<ProjectDto>
	suspend fun getProjectById(projectId: UUID): ProjectDto
	suspend fun createProject(project: ProjectDto):Boolean
	suspend fun editProject(newProject: ProjectDto)
	suspend fun deleteProjectById(project: ProjectDto)
	// endregion
	
	// region Tasks
	suspend fun getTasksByProjectId(projectId: UUID): List<TaskDto>
	suspend fun createTask(task: TaskDto):TaskDto
	suspend fun editTask(task: TaskDto)
	suspend fun deleteTask(task: TaskDto)
	suspend fun getTaskById(taskID: UUID): TaskDto
	// endregion
	
	// region Logs
	suspend fun recordTaskLog(log: LogDto)
	suspend fun recordProjectLog(log: LogDto)
	suspend fun getProjectLogs(projectId: UUID): List<LogDto>
	suspend fun getTaskLogs(taskId: UUID): List<LogDto>
	// endregion
	
	// region States
	suspend fun getAllStates(): List<StateDto>
	suspend fun getStateById(stateId: UUID): StateDto
	suspend fun createState(state: StateDto): StateDto
	suspend fun editState(state: StateDto)
	// endregion
}