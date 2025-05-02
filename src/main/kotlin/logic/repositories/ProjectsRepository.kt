package logic.repositories

import logic.model.Project
import logic.model.State
import logic.model.User
import java.util.*

interface ProjectsRepository {
    fun createProject(project: Project, user: User): Result<Unit>
    fun editProject(newProject: Project)
    fun deleteProject(projectId: UUID): Result<Unit>
    fun getProjectById(projectId: UUID): Result<Project>
    fun getAllProjects(): Result<List<Project>>
    fun editState(state: State)
}