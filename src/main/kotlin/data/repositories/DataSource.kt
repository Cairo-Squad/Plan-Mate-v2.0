package data.repositories


import java.util.UUID
import data.dto.StateDto
import data.dto.*

interface DataSource {
    // region Users
    fun getAllUsers(): List<UserDto>
    fun createUser(id: UUID, name: String, password: String, type: UserType): UserDto
    fun editUser(user: UserDto)
    fun deleteUser(user: UserDto)
    // endregion

    // region Projects
    fun getAllProjects(): List<ProjectDto>
    fun getProjectById(projectId: UUID): ProjectDto
    fun createProject(project: ProjectDto): Result<Unit>
    fun editProject(newProject: ProjectDto)
    fun deleteProjectById(project: ProjectDto): Result<Unit>
    // endregion

    // region Tasks
    fun getTasksByProjectId(projectId: UUID): List<TaskDto>
    fun createTask(task: TaskDto): Result<Unit>
    fun editTask(task: TaskDto)
    fun deleteTask(task: TaskDto)
    fun getTaskById(taskID: UUID): TaskDto
    // endregion

    // region Logs
    fun recordLog(log: LogDto)
    fun addProjectLog(logDto: LogDto)
    fun getAllAuditRecords(): List<LogDto>
    fun getProjectLog(projectId: UUID): List<LogDto>
    fun getTaskLogs(taskId: UUID): List<LogDto>
    // endregion

    // region States
    fun getAllStates(): List<StateDto>
    fun getStateById(stateId: UUID): StateDto
    fun createState(state: StateDto): Boolean
    fun editState(state: StateDto)
    // endregion
}