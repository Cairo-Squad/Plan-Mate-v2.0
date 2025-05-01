package data.dataSource

import data.dto.*
import java.util.UUID

interface DataSource {
    fun getAllUsers(): List<UserDto>
    fun getAllProjects(): List<ProjectDto>
    fun getAllTasks(): List<TaskDto>
    fun getAllAuditRecords(): List<LogDto>
    fun getTaskById(taskID:UUID):TaskDto
    fun getStateById(stateId:UUID): StateDto
}