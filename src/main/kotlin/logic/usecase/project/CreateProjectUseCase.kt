package logic.usecase.project

import logic.model.Project
import logic.model.User
import logic.repositories.ProjectsRepository

class CreateProjectUseCase(
    private val projectRepository: ProjectsRepository,
    private val validationProject: ValidationProject
) {
    suspend fun createProject(project: Project, user: User):Boolean {
        validationProject.validateCreateProject(project, user)
        return projectRepository.createProject(project, user)
    }
}