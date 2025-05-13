
package logic.usecase.project

import logic.model.Project
import logic.repositories.ProjectsRepository

class GetAllProjectsUseCase(
    private val projectsRepository: ProjectsRepository
) {
    suspend fun getAllProjects(): List<Project> {
        return projectsRepository.getAllProjects()
    }
}
