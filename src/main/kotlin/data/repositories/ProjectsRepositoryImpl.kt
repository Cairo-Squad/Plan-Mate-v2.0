package data.repositories

import data.dataSource.DataSource
import data.repositories.mappers.toProject
import data.repositories.mappers.toState
import data.repositories.mappers.toTask
import logic.model.Project
import logic.model.State
import logic.model.Task
import logic.repositories.ProjectsRepository
import java.util.UUID

class ProjectsRepositoryImpl(
    private val dataSource: DataSource
) : ProjectsRepository {
    override fun getAllProjects(): Result<List<Project>> {
        val projects = dataSource.getAllProjects().map { projectDto ->
            val state = getState(projectDto.stateId)
            val tasks = getTasksForProject(projectDto.id)
            projectDto.toProject(
                projectState = state,
                projectTasks = tasks
            )
        }
        return Result.success(projects)
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