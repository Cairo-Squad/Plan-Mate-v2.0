package logic.usecase

import data.dto.UserType
import logic.model.Project
import logic.model.User
import logic.repositories.ProjectsRepository

class CreateProjectUseCase(
    private val repository: ProjectsRepository
) {
    fun createProject(project: Project, user: User): Result<Unit> {
        return if (isValidProjectCreation(project, user)) {
            try {
                repository.createProject(project, user)
            } catch (e: Exception) {
                Result.failure(e)
            }
        } else {
            Result.failure(IllegalArgumentException())
        }
    }

    private fun isValidProjectCreation(project: Project, user: User): Boolean {
        return user.type == UserType.ADMIN && project.title.isNotBlank()
    }
}
