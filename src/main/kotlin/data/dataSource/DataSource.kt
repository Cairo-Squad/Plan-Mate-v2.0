package data.dataSource

import java.util.UUID
import data.dto.StateDto
import data.dto.*

interface DataSource {
    fun getAllUsers(): List<UserDto>
    fun getAllProjects(): List<ProjectDto>
    fun getProjectById(projectId: UUID): ProjectDto
    fun getAllTasks(): List<TaskDto>
    fun getAllAuditRecords(): List<LogDto>
    fun getProjectLog(projectId: UUID): List<LogDto>
    fun createProject(project: ProjectDto):Result<Unit>
    fun deleteProjectById(project: ProjectDto): Result<Unit>
    fun getAllStates(): List<StateDto>
    fun getTasksByProjectId(projectId: UUID): List<TaskDto>

}