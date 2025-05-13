package data.dataSource.remoteDataSource

import data.dto.*
import java.util.*

interface RemoteDataSource {
    // region Users
    suspend fun getAllUsers(): List<UserDto>
    suspend fun createUser(user: UserDto): Boolean
    suspend fun editUser(user: UserDto): Boolean
    suspend fun deleteUser(userId: UUID): Boolean
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
	suspend fun createTask(task: TaskDto):Boolean
	suspend fun editTask(task: TaskDto)
	suspend fun deleteTask(task: TaskDto)
	suspend fun getTaskById(taskID: UUID): TaskDto
	suspend fun getAllTasks():List<TaskDto>
	// endregion
	
	// region Logs
	suspend fun addTaskLog(log: LogDto)
	suspend fun addProjectLog(log: LogDto)
	suspend fun getProjectLogs(projectId: UUID): List<LogDto>
	suspend fun getTaskLogs(taskId: UUID): List<LogDto>
	// endregion
	
	// region States
	suspend fun getAllStates(): List<StateDto>
	suspend fun getStateById(stateId: UUID): StateDto
	suspend fun createState(state: StateDto): Boolean
	suspend fun editState(state: StateDto)
	// endregion
}