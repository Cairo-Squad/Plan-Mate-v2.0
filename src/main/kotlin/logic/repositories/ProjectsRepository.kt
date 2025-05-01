package logic.repositories

import logic.model.Project
import logic.model.User
import java.util.UUID

interface ProjectsRepository {
    fun deleteProject(projectId: UUID): Result<Unit>
    fun getProjectById(projectId: UUID): Result<Project>
    fun getAllProjects(): Result<List<Project>>
    fun createProject(project: Project,user: User):Result<Unit>
}