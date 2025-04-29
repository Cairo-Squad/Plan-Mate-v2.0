package logic.usecase

import logic.model.Project
import logic.repositories.ProjectsRepository
import java.util.*
import kotlin.NoSuchElementException

class DeleteProjectUseCase(
    private val projectsRepository: ProjectsRepository
) {
    fun deleteProjectById(projectId: UUID): Result<Project> {
        return Result.failure(NoSuchElementException())
    }
}
