package logic.usecase.project

import data.dto.EntityType
import data.dto.UserAction
import logic.exception.EmptyNameException
import logic.exception.ProjectNotFoundException
import logic.model.Log
import logic.model.Project
import logic.repositories.ProjectsRepository
import logic.usecase.Log.AddLogUseCase
import java.time.LocalDateTime
import java.util.UUID

class EditProjectUseCase(private val projectsRepository: ProjectsRepository, private val addLogUseCase: AddLogUseCase) {

    fun editProject(newProject: Project) {
        validateNewProject(newProject)
        projectsRepository.editProject(newProject)

        val projectInfo = projectsRepository.getProjectById(newProject.id).getOrNull()
        if (projectInfo == null) throw ProjectNotFoundException()

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

    private fun validateNewProject(newProject: Project) {
        if (newProject.title.isBlank()) {
            throw EmptyNameException()
        }
    }
}