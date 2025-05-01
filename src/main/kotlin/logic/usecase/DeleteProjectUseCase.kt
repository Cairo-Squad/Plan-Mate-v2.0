package logic.usecase

import logic.repositories.ProjectsRepository
import java.lang.IllegalArgumentException
import java.util.*

class DeleteProjectUseCase(
    private val projectsRepository: ProjectsRepository
) {
    fun deleteProjectById(projectId: UUID): Result<Unit> {
        return Result.failure(IllegalArgumentException())
    }
}