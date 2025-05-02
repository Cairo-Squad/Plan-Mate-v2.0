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


    fun getAllStates(): List<StateDto>

    fun getTasksByProjectId(projectId: UUID): List<TaskDto>
    fun createTask(task: TaskDto): Result<Unit>
    fun editTask(task: TaskDto)
    fun deleteTask(task: TaskDto)
    fun getTaskById(taskID: UUID): TaskDto


    // region Logs
    fun recordLog(log: LogDto)
    fun addProjectLog(logDto: LogDto)
    fun getAllAuditRecords(): List<LogDto>
    fun getProjectLog(projectId: UUID): List<LogDto>
    fun getTaskLogs(taskId: UUID): List<LogDto>
    // endregion
    fun getStateById(stateId: UUID): StateDto

    fun createState(state: StateDto, userDto: UserDto): Boolean
    fun editState(state: StateDto)


}