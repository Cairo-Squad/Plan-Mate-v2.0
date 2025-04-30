package logic.usecase.project

import logic.model.Project
import logic.repositories.ProjectsRepository
import java.util.UUID

class GetProjectByIdUseCase(
    private val projectsRepository: ProjectsRepository
) {
    fun getProjectById(projectId: UUID): Result<Project> {
        return projectsRepository.getProjectById(projectId)
    }
}