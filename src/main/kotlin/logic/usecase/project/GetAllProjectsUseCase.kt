package logic.usecase.project

import logic.model.Project
import logic.repositories.ProjectsRepository

class GetAllProjectsUseCase(
    private val projectsRepository: ProjectsRepository
) {
    fun getAllProjects(): Result<List<Project>> {
        return projectsRepository.getAllProjects()
    }
}