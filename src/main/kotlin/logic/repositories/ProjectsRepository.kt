package logic.repositories

import logic.model.Project
import java.util.UUID

interface ProjectsRepository {
    fun getProjectById(projectId: UUID): Result<Project>
    fun getAllProjects(): Result<List<Project>>
}