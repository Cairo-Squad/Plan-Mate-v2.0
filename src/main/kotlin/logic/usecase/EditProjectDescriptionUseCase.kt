package logic.usecase

import logic.repositories.ProjectsRepository
import java.util.UUID

class EditProjectDescriptionUseCase(private val projectsRepository: ProjectsRepository) {

    fun editProjectDescription(newDescription: String, projectID: UUID): Result<Unit> {
        return try {
            projectsRepository.editProjectDescription(newDescription, projectID)
            Result.success(Unit)
        } catch (exception: Exception) {
            Result.failure(exception)
        }
    }
}