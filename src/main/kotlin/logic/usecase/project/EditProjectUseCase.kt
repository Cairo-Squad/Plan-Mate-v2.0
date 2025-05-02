
package logic.usecase.project

import logic.model.Project
import logic.repositories.ProjectsRepository

class EditProjectUseCase(private val projectsRepository: ProjectsRepository) {

    fun editProject(newProject: Project): Result<Unit> {
        return try {
            require(newProject.title.isNotBlank()) { "Project title can't be empty" }
            projectsRepository.editProject(newProject)
            Result.success(Unit)
        } catch (exception: Exception) {
            Result.failure(exception)
        }
    }
}
