package logic.usecase.project

import logic.model.Project
import logic.repositories.ProjectsRepository
import java.util.UUID

class GetProjectByIdUseCase(
    private val projectsRepository: ProjectsRepository
) {
    suspend fun getProjectById(projectId: UUID): Project {
        return projectsRepository.getProjectById(projectId)
    }
}