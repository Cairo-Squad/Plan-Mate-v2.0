package logic.usecase.project

import logic.exception.ErrorMessageException
import logic.repositories.ProjectsRepository
import java.util.UUID

class DeleteProjectUseCase(
    private val projectsRepository: ProjectsRepository
) {
    fun deleteProjectById(projectId: UUID): Boolean {
        try {
            return projectsRepository.deleteProject(projectId)
        } catch (exception: Exception) {
            throw ErrorMessageException()
        }
    }
}