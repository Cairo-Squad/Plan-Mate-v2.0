package logic.usecase.project

import logic.model.ActionType
import logic.model.EntityType
import logic.model.Log
import logic.repositories.ProjectsRepository
import logic.usecase.log.AddProjectLogUseCase
import java.time.LocalDateTime
import java.util.UUID

class DeleteProjectUseCase(
    private val projectsRepository: ProjectsRepository,
    private val addProjectLogUseCase: AddProjectLogUseCase,
    private val getProjectByIdUseCase: GetProjectByIdUseCase,
) {
    suspend fun deleteProjectById(projectId: UUID) {
        projectsRepository.deleteProject(projectId)

        val deletedProject = getProjectByIdUseCase.getProjectById(projectId)

        val log = Log(
            entityId = deletedProject.id!!,
            entityTitle = deletedProject.title ?: "",
            entityType = EntityType.PROJECT,
            dateTime = LocalDateTime.now(),
            userId = deletedProject.createdBy!!,
            userAction = ActionType.EDIT_PROJECT
        )

        addProjectLogUseCase.addProjectLog(log)
    }
}