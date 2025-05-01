package logic.usecase

import logic.model.Project
import logic.repositories.LogsRepository
import logic.repositories.ProjectsRepository

class DeleteProjectUseCase(
    private val projectsRepository: ProjectsRepository,
    private val logsRepo: LogsRepository
) {
    fun deleteProject(project: Project) {
        projectsRepository.deleteProject(project)
        logsRepo.addProjectLog()
    }
}