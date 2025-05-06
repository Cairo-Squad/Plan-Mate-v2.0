package logic.usecase.project

import data.dto.UserType
import logic.exception.EmptyTitleException
import logic.exception.InvalidUserException
import logic.model.Project
import logic.model.User
import logic.repositories.ProjectsRepository

class CreateProjectUseCase(
    private val projectRepository: ProjectsRepository,
) {
    fun createProject(project: Project, user: User) {
        validateProjectCreation(project, user)
        projectRepository.createProject(project, user)
    }

    private fun validateProjectCreation(project: Project, user: User) {
        if (user.type != UserType.ADMIN) throw InvalidUserException()
        if (project.title.isBlank()) throw EmptyTitleException()
    }
}