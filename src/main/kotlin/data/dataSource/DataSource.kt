package data.dataSource

import data.dto.LogDto
import data.dto.ProjectDto
import data.dto.TaskDto
import data.dto.UserDto
import logic.model.Project

interface DataSource {
    fun getAllUsers(): List<UserDto>
    fun getAllTasks(): List<TaskDto>
    fun getAllAuditRecords(): List<LogDto>


    fun getAllProjects(): List<ProjectDto>
    fun editProject(newProject: Project)
}