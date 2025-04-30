package data.dataSource

import data.dto.UserDto
import data.dto.ProjectDto
import data.dto.TaskDto
import data.dto.LogDto
import data.dto.StateDto
import java.util.UUID

interface DataSource {
    fun getAllUsers(): List<UserDto>
    fun getAllProjects(): List<ProjectDto>
    fun getProjectById(projectId: UUID): ProjectDto?

    fun getTasksByProjectId(projectId: UUID): List<TaskDto>
    fun getAllTasks(): List<TaskDto>
    fun getAllStates(): List<StateDto>

    fun getAllAuditRecords(): List<LogDto>
}