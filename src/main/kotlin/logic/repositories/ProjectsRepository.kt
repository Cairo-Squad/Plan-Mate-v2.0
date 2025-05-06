package logic.repositories

import logic.model.Project
import logic.model.State
import logic.model.User
import java.util.*

interface ProjectsRepository {
    fun createProject(project: Project, user: User)
    fun editProject(newProject: Project)
    fun deleteProject(projectId: UUID)
    fun getProjectById(projectId: UUID): Project
    fun getAllProjects(): List<Project>
}