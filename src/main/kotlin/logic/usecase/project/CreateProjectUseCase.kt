package logic.usecase.project

import logic.model.*
import logic.repositories.ProjectsRepository
import logic.usecase.log.AddProjectLogUseCase
import java.time.LocalDateTime
import java.util.UUID

class CreateProjectUseCase(
    private val projectRepository: ProjectsRepository,
    private val addProjectLogUseCase: AddProjectLogUseCase,
    private val validationProject: ValidationProject
) {
    suspend fun createProject(project: Project, user: User): UUID {
        validationProject.validateCreateProject(project, user)

        val projectId = projectRepository.createProject(project)

        val log = Log(
            entityId = projectId,
            entityTitle = project.title ?: "",
            entityType = EntityType.PROJECT,
            dateTime = LocalDateTime.now(),
            userId = project.createdBy!!,
            userAction = UserAction.CreateProject(projectId, "Created project")
        )

        addProjectLogUseCase.addProjectLog(log)

        return projectId
    }
}