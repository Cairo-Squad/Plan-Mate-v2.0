package logic.usecase.project

import logic.model.Project
import logic.model.User
import logic.repositories.ProjectsRepository
import logic.usecase.log.AddLogUseCase
import java.util.UUID

class CreateProjectUseCase(
    private val projectRepository: ProjectsRepository,
    private val addLogUseCase: AddLogUseCase, // TODO
    private val validationProject: ValidationProject
) {
    suspend fun createProject(project: Project, user: User):UUID {
        validationProject.validateCreateProject(project, user)
        return projectRepository.createProject(project, user)

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