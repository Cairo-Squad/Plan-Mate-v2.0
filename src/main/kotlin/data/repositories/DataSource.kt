package data.repositories


import java.util.UUID
import data.dto.StateDto
import data.dto.*

interface DataSource {
    // region Users
    suspend fun getAllUsers(): List<UserDto>
    suspend fun createUser(id: UUID, name: String, password: String, type: UserType): UserDto
    suspend fun editUser(user: UserDto)
    suspend fun deleteUser(user: UserDto)
    // endregion

    // region Projects
    suspend fun getAllProjects(): List<ProjectDto>
    suspend fun getProjectById(projectId: UUID): ProjectDto
    suspend fun createProject(project: ProjectDto)
    suspend fun editProject(newProject: ProjectDto)
    suspend fun deleteProjectById(project: ProjectDto)
    // endregion

    // region Tasks
    suspend fun getTasksByProjectId(projectId: UUID): List<TaskDto>
    suspend fun createTask(task: TaskDto)
    suspend fun editTask(task: TaskDto)
    suspend fun deleteTask(task: TaskDto)
    suspend fun getTaskById(taskID: UUID): TaskDto
    // endregion

    // region Logs
    suspend fun recordLog(log: LogDto)
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