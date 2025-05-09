package logic.usecase.project

import data.dto.UserType
import logic.exception.EmptyTitleException
import logic.exception.InvalidUserException
import logic.model.Project
import logic.model.User
import logic.repositories.ProjectsRepository
import logic.usecase.Log.AddLogUseCase

class CreateProjectUseCase(
    private val projectRepository: ProjectsRepository,
    private val addLogUseCase: AddLogUseCase,
    private val validationProject: ValidationProject
) {
    fun createProject(project: Project, user: User) {
        validationProject.validateCreateProject(project, user)
        projectRepository.createProject(project, user)

       /* val log = Log(
            id = UUID.randomUUID(),
            entityId = project.id,
            entityTitle = project.title,
            entityType = EntityType.PROJECT,
            dateTime = LocalDateTime.now(),
            userId = project.createdBy,
            userAction = UserAction.CreateProject(project.title, project.id)
        )

        addLogUseCase.addLog(log)*/

    }
}

    private fun validateProjectCreation(project: Project, user: User) {
        if (user.type != UserType.ADMIN) throw InvalidUserException()
        if (project.title.isBlank()) throw EmptyTitleException()
    }