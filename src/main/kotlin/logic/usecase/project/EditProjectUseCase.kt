package logic.usecase.project

import logic.exception.EmptyNameException
import logic.model.Project
import logic.repositories.ProjectsRepository

class EditProjectUseCase(private val projectsRepository: ProjectsRepository) {

    fun editProject(newProject: Project): Result<Unit> {
        return try {
            validateNewProject(newProject)
            projectsRepository.editProject(newProject)
            Result.success(Unit)
        } catch (exception: Exception) {
            Result.failure(exception)
        }
    }

    private fun validateNewProject(newProject:Project) {
        if (newProject.title.isBlank()) {
            throw EmptyNameException()
        }
    }
}