package logic.usecase

import logic.repositories.ProjectsRepository
import java.util.*

class EditProjectTitleUseCase(private val projectsRepository: ProjectsRepository) {

    fun editProjectTitle(projectId: UUID, newTitle: String): Result<Unit> {
        TODO()
    }
}