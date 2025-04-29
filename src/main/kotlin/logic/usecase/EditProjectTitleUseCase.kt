package logic.usecase

import logic.repositories.ProjectsRepository
import java.io.IOException
import java.util.*

class EditProjectTitleUseCase(private val projectsRepository: ProjectsRepository) {

    fun editProjectTitle(projectId: UUID, newTitle: String): Result<Unit> {
        return try {
            require(newTitle.isNotBlank()) { "Title can't be blank" }
            projectsRepository.editProjectTitle(projectId = projectId, newTitle = newTitle)
            Result.success(Unit)
        } catch (exception: Exception) {
            Result.failure(exception)
        }
    }
}