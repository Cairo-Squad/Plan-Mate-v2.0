package data.repositories

import data.dataSource.DataSource
import data.dto.ProjectDto
import data.repositories.mappers.toProject
import data.repositories.mappers.toProjectDto
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
        val projects = dataSource.getAllProjects()
            .map { projectsDao ->
                val state = getProjectState(projectsDao.stateId)
                val tasks = getTasksForProject(projectsDao.id)
                projectsDao.toProject(
                    projectState = state,
                    projectTasks = tasks,

                    )
            }
        return Result.success(projects)
    }

    override fun deleteProject(projectId: UUID): Result<Unit> {
        val projectsDao = dataSource.getAllProjects().find { it.id == projectId } ?: return Result.failure(Exception())
        dataSource.deleteProjectById(projectsDao)
        return Result.success(Unit)
    }

    private fun getProjectState(stateId: UUID): State {
        return dataSource.getAllStates().first { it.id == stateId }.toState()
    }

    private fun getTasksForProject(projectId: UUID): List<Task> {
        return dataSource.getTasksByProjectId(projectId)
            .map { it.toTask(projectState = getProjectState(it.stateId)) }
    }

}

