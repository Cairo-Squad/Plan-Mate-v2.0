package data.dataSource.localDataSource


import java.util.UUID
import data.dto.StateDto
import data.dto.*
import logic.model.User

interface LocalDataSource {
    // region Users
    fun getAllUsers(): List<UserDto>
    fun createUser(user: UserDto): Boolean
    fun editUser(user: UserDto): Boolean
    fun deleteUser(user: UserDto): Boolean
    fun loginUser(name: String, password: String): Boolean
    fun getCurrentUser(): UserDto?
    // endregion

    // region Projects
    fun getAllProjects(): List<ProjectDto>
    fun getProjectById(projectId: UUID): ProjectDto
    fun createProject(project: ProjectDto)
    fun editProject(newProject: ProjectDto)
    fun deleteProjectById(project: ProjectDto)
    // endregion

    // region Tasks
    fun getTasksByProjectId(projectId: UUID): List<TaskDto>
    fun createTask(task: TaskDto)
    fun editTask(task: TaskDto)
    fun deleteTask(task: TaskDto)
    fun getTaskById(taskID: UUID): TaskDto
    // endregion

    // region Logs
    fun recordLog(log: LogDto)
    fun getProjectLogs(projectId: UUID): List<LogDto>
    fun getTaskLogs(taskId: UUID): List<LogDto>
    // endregion

    // region States
    fun getAllStates(): List<StateDto>
    fun getStateById(stateId: UUID): StateDto
    fun createState(state: StateDto): Boolean
    fun editState(state: StateDto)
    // endregion
}