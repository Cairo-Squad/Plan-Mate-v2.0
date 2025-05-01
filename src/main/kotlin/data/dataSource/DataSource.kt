package data.dataSource

import data.dto.UserDto
import data.dto.ProjectDto
import data.dto.TaskDto
import data.dto.LogDto
import java.util.UUID

interface DataSource {
    fun getAllUsers(): List<UserDto>
    fun getAllProjects(): List<ProjectDto>
    fun getAllTasks(): List<TaskDto>
    fun getAllAuditRecords(): List<LogDto>
    fun createTask(task: TaskDto): Result<Unit>
    fun deleteTask(task: TaskDto)

    // region Logs
    fun recordLog(log: LogDto)
    fun getTaskLogs(taskId: UUID): List<LogDto>
    // endregion
}