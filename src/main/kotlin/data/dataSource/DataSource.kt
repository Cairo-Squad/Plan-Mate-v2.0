package data.dataSource

import data.dto.UserDto
import data.dto.ProjectDto
import data.dto.TaskDto
import data.dto.LogEntityDto

interface DataSource {
    fun getAllUsers(): List<UserDto>
    fun getAllProjects(): List<ProjectDto>
    fun getAllTasks(): List<TaskDto>
    fun getAllAuditRecords(): List<LogEntityDto>
}