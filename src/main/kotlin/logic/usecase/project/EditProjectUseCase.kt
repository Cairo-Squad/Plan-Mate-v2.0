package logic.usecase.project

import data.dto.EntityType
import data.dto.UserAction
import logic.model.Log
import logic.model.Project
import logic.repositories.ProjectsRepository
import logic.usecase.Log.AddLogUseCase
import java.time.LocalDateTime
import java.util.UUID

class EditProjectUseCase(
    private val projectsRepository: ProjectsRepository,
    private val addLogUseCase: AddLogUseCase,
    private val validationProject: ValidationProject
) {
    fun editProject(newProject: Project) {
        validationProject.validateEditProject(newProject)
        projectsRepository.editProject(newProject)

        val projectInfo = projectsRepository.getProjectById(newProject.id)

        val log = Log(
            id = UUID.randomUUID(),
            entityId = projectInfo.id,
            entityTitle = projectInfo.title,
            entityType = EntityType.PROJECT,
            dateTime = LocalDateTime.now(),
            userId = newProject.createdBy,
            userAction = UserAction.EditProject(newProject.id, "Updated project details")
        )

        addLogUseCase.addLog(log)

    }
}