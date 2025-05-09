package logic.repositories

import logic.model.Project
import logic.model.State
import logic.model.User
import java.util.*

interface ProjectsRepository {
    suspend fun createProject(project: Project, user: User)
    suspend fun editProject(newProject: Project)
    suspend fun deleteProject(projectId: UUID)
    suspend fun getProjectById(projectId: UUID): Project
    suspend fun getAllProjects(): List<Project>
}