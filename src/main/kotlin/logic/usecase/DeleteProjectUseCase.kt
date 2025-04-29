package logic.usecase

import logic.repositories.ProjectsRepository
import java.util.*

class DeleteProjectUseCase(
    private val projectsRepository: ProjectsRepository
) {
    fun deleteProjectById(projectId: UUID): Boolean {
        return false
    }
}
