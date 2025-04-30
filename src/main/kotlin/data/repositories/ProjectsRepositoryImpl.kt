package data.repositories

import data.dataSource.DataSource
import data.repositories.mappers.toProject
import data.repositories.mappers.toState
import data.repositories.mappers.toTask
import logic.model.Project
import logic.model.State
import logic.model.Task
import logic.repositories.ProjectsRepository
import java.util.*

class ProjectsRepositoryImpl(
    private val dataSource: DataSource
) : ProjectsRepository {
    override fun getProjectById(projectId: UUID): Result<Project> {
        val projectDto = dataSource.getProjectById(projectId)
            ?: return Result.failure(NoSuchElementException("Project not found"))

        return Result.success(
            projectDto.toProject(
                projectState = getState(projectDto.stateId),
                projectTasks = getTasksForProject(projectId),
            )
        )
    }

    private fun getState(stateId: UUID): State {
        return dataSource.getAllStates()
            .first { it.id == stateId }
            .toState()
    }

    private fun getTasksForProject(projectId: UUID): List<Task> {
        return dataSource.getTasksByProjectId(projectId)
            .map { it.toTask(projectState = getState(it.stateId)) }
    }
}