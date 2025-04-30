package logic.usecase

import data.dto.UserType
import logic.model.Project
import logic.model.User
import logic.repositories.ProjectsRepository

class CreateProjectUseCase(
    private val repository: ProjectsRepository
) {
    fun createProject(project: Project, user: User): Result<Project> {
            return Result.failure(IllegalArgumentException())
        }



}
