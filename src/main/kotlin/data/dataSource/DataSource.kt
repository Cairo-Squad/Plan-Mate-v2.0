package data.dataSource


import java.util.UUID
import data.dto.StateDto
import data.dto.*

interface DataSource {
    fun editUser(user: UserDto)
    fun createUser(id: UUID, name:String, password:String, type:UserType): UserDto
    fun getAllUsers(): List<UserDto>

    fun createProject(project: ProjectDto):Result<Unit>
    fun editProject(newProject: ProjectDto)
    fun deleteProjectById(project: ProjectDto): Result<Unit>
    fun getProjectById(projectId: UUID): ProjectDto
    fun deleteUser(user:UserDto)
    fun getAllProjects(): List<ProjectDto>

    fun getTasksByProjectId(projectId: UUID): List<TaskDto>

    fun getAllStates(): List<StateDto>

    fun getAllAuditRecords(): List<LogDto>
    fun addProjectLog(logDto: LogDto)
    fun getProjectLog(projectId: UUID): List<LogDto>

    fun createTask(task: TaskDto): Result<Unit>
    fun editTask(task: TaskDto)
    fun deleteTask(task: TaskDto)

    // region Logs
    fun recordLog(log: LogDto)
    fun getTaskLogs(taskId: UUID): List<LogDto>
    // endregion
    fun getTaskById(taskID: UUID): TaskDto
    fun getStateById(stateId: UUID): StateDto
    
    fun createState(state: StateDto, userDto: UserDto): Boolean
}