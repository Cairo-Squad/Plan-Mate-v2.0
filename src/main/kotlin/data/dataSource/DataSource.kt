package data.dataSource

import data.dto.UserDto
import data.dto.ProjectDto
import data.dto.TaskDto
import data.dto.LogDto
import java.util.UUID

interface DataSource {
    fun getAllUsers(): List<UserDto>
    fun getAllTasks(): List<TaskDto>
    fun getAllAuditRecords(): List<LogDto>


    fun getAllProjects(): List<ProjectDto>
    fun editProjectTitle(newTitle: String, projectID: UUID)
    fun editProjectDescription(newDescription: String, projectID: UUID)
}