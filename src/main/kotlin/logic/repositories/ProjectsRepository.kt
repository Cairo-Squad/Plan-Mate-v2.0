package logic.repositories

import data.dto.ProjectDto
import logic.model.Project
import java.util.UUID

interface ProjectsRepository {
    fun getAllProjects(): Result<List<Project>>
    fun deleteProject(projectId: UUID): Result<Unit>
}