package logic.usecase

import logic.repositories.ProjectsRepository
import java.util.*
import kotlin.NoSuchElementException

class DeleteProjectUseCase(
    private val projectsRepository: ProjectsRepository
) {
    fun deleteProjectById(projectId: UUID): Result<Unit> {
        projectsRepository.deleteProject(projectId).onSuccess {
            return Result.success(Unit)
        }
        return Result.failure(NoSuchElementException())
    }
}
