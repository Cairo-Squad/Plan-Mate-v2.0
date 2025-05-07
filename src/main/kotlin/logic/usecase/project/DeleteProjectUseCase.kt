package logic.usecase.project

import logic.repositories.ProjectsRepository
import java.util.UUID

class DeleteProjectUseCase(
    private val projectsRepository: ProjectsRepository

) {
    suspend fun deleteProjectById(projectId: UUID) {
        return projectsRepository.deleteProject(projectId)
    }
}