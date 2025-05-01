package data.dataSource

import data.dto.LogDto
import data.dto.ProjectDto
import data.dto.TaskDto
import java.util.UUID
import data.dto.StateDto
import data.dto.UserDto
import logic.model.Project

interface DataSource {
    fun getAllUsers(): List<UserDto>
    fun getAllProjects(): List<ProjectDto>
    fun getProjectById(projectId: UUID): ProjectDto

    fun getTasksByProjectId(projectId: UUID): List<TaskDto>
    fun getAllTasks(): List<TaskDto>
    fun getAllStates(): List<StateDto>

    fun getAllAuditRecords(): List<LogDto>
    fun getProjectLog(projectId: UUID): List<LogDto>
    fun createProject(project: ProjectDto):Result<Unit>


    fun editProject(newProject: ProjectDto)
}