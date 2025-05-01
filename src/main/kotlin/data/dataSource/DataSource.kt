package data.dataSource

import data.dto.*
import java.util.UUID

interface DataSource {
    fun getAllUsers(): List<UserDto>
    fun getAllProjects(): List<ProjectDto>
    fun getAllTasks(): List<TaskDto>
    fun getAllAuditRecords(): List<LogDto>
    fun deleteProjectById(project: ProjectDto): Result<Unit>
    fun getAllStates(): List<StateDto>
    fun getTasksByProjectId(projectId: UUID): List<TaskDto>

}