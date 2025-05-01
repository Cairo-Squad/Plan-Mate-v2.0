package data.dataSource

import data.dto.UserDto
import data.dto.ProjectDto
import data.dto.TaskDto
import data.dto.LogDto
import data.dto.*
import java.util.*

interface DataSource {
    fun createUser(id: UUID, name:String, password:String, type:UserType): UserDto
    fun getAllUsers(): List<UserDto>
    fun editUser(user: UserDto)
    fun deleteUser(user:UserDto)
    fun getAllProjects(): List<ProjectDto>
    fun getAllTasks(): List<TaskDto>
    fun getAllAuditRecords(): List<LogDto>
    fun createTask(task: TaskDto): Result<Unit>
    fun deleteTask(task: TaskDto)

    // region Logs
    fun recordLog(log: LogDto)
    fun getTaskLogs(taskId: UUID): List<LogDto>

    // endregion
    fun getTaskById(taskID: UUID): TaskDto
    fun getStateById(stateId: UUID): StateDto
}