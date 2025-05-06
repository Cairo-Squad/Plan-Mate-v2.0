package logic.usecase.project

import data.dto.EntityType
import data.dto.UserAction
import data.dto.UserType
import logic.model.Log
import logic.model.Project
import logic.model.User
import logic.repositories.LogsRepository
import logic.repositories.ProjectsRepository
import logic.usecase.Log.AddLogUseCase
import java.time.LocalDateTime
import java.util.*

class CreateProjectUseCase(
    private val projectRepository: ProjectsRepository,
    private val addLogUseCase: AddLogUseCase
) {
    fun createProject(project: Project, user: User): Result<Unit> {
        return if (isValidProjectCreation(project, user)) {
            try {
                projectRepository.createProject(project, user)

                val log = Log(
                    id = UUID.randomUUID(),
                    entityId = project.id,
                    entityTitle = project.title,
                    entityType = EntityType.PROJECT,
                    dateTime = LocalDateTime.now(),
                    userId = project.createdBy,
                    userAction = UserAction.CreateProject(project.title, project.id)
                )

                addLogUseCase.addLog(log)
                Result.success(Unit)
            } catch (e: Exception) {
                Result.failure(e)
            }
        } else {
            Result.failure(IllegalArgumentException("Invalid project creation request"))
        }
    }
}


    private fun isValidProjectCreation(project: Project, user: User): Boolean {
        return user.type == UserType.ADMIN && project.title.isNotBlank()
    }
