package logic.usecase.project

import logic.model.*
import logic.repositories.ProjectsRepository
import logic.usecase.log.AddProjectLogUseCase
import java.time.LocalDateTime

class EditProjectUseCase(
    private val projectsRepository: ProjectsRepository,
    private val addProjectLogUseCase: AddProjectLogUseCase,
    private val validationProject: ValidationProject
) {
    suspend fun editProject(newProject: Project) {
        validationProject.validateEditProject(newProject)
        projectsRepository.editProject(newProject)

        val projectInfo = projectsRepository.getProjectById(newProject.id!!)

        val log = Log(
            entityId = projectInfo.id!!,
            entityTitle = projectInfo.title ?: "",
            entityType = EntityType.PROJECT,
            dateTime = LocalDateTime.now(),
            userId = newProject.createdBy!!,
            userAction = ActionType.EDIT_PROJECT
        )

        addProjectLogUseCase.addProjectLog(log)
    }
}