package logic.usecase.project

import data.dto.EntityType
import data.dto.UserAction
import logic.model.Log
import logic.model.Project
import logic.repositories.ProjectsRepository
import logic.usecase.log.AddLogUseCase
import java.time.LocalDateTime
import java.util.*

class EditProjectUseCase(
    private val projectsRepository: ProjectsRepository,
    private val addLogUseCase: AddLogUseCase
) {

    fun editProject(newProject: Project) {
        projectsRepository.editProject(newProject)
        val log = Log(
            id = UUID.randomUUID(),
            entityId = newProject.id,
            entityTitle = newProject.title,
            entityType = EntityType.PROJECT,
            dateTime = LocalDateTime.now(),
            userId = newProject.createdBy,
            userAction = UserAction.EditProject(newProject.id, "Updated project details")
        )
        addLogUseCase.addLog(log)
    }
}