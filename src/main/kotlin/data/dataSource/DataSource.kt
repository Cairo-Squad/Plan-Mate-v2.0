package data.dataSource

import data.dto.*
import java.util.UUID
import data.dto.UserDto
import data.dto.ProjectDto
import data.dto.TaskDto
import data.dto.LogDto

interface DataSource {
    fun getAllUsers(): List<UserDto>
    fun getAllProjects(): List<ProjectDto>
    fun getAllTasks(): List<TaskDto>
    fun getAllAuditRecords(): List<LogDto>
    fun createTask(task: TaskDto): Result<Unit>

    // region Logs
    fun recordLog(log: LogDto)
    fun getTaskLogs(taskId: UUID): List<LogDto>
    // endregion
    fun getTaskById(taskID:UUID):TaskDto
    fun getStateById(stateId:UUID): StateDto
}