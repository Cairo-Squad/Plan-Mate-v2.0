package data.dataSource

import data.dto.UserDto
import data.dto.ProjectDto
import data.dto.TaskDto
import data.dto.LogDto
import java.util.*

interface DataSource {
    fun getAllUsers(): List<UserDto>
    fun deleteUser(user:UserDto)
    fun getAllProjects(): List<ProjectDto>
    fun getAllTasks(): List<TaskDto>
    fun getAllAuditRecords(): List<LogDto>
}