package data.repositories

import data.repositories.mappers.toProject
import data.repositories.mappers.toProjectDto
import data.repositories.mappers.toState
import data.repositories.mappers.toTask
import logic.exception.ProjectNotFoundException
import logic.model.Project
import logic.model.State
import logic.model.Task
import logic.model.User
import logic.repositories.ProjectsRepository
import java.util.*

class ProjectsRepositoryImpl(
    private val dataSource: DataSource
) : ProjectsRepository {

    override suspend fun createProject(project: Project, user: User) {
        return dataSource.createProject(project.toProjectDto())
    }

    override suspend fun editProject(newProject: Project) {
        return dataSource.editProject(newProject.toProjectDto())
    }

    override suspend fun deleteProject(projectId: UUID) {
        val projectsDao = dataSource.getAllProjects()
            .find { it.id == projectId } ?: throw ProjectNotFoundException()
        return dataSource.deleteProjectById(projectsDao)
    }

    override suspend fun getProjectById(projectId: UUID): Project {
        val projectDto = dataSource.getProjectById(projectId)
        return projectDto.toProject(
            projectState = getState(projectDto.stateId),
            projectTasks = getTasksForProject(projectId),
        )
    }

    override suspend fun getAllProjects(): List<Project> {
        return dataSource.getAllProjects().map { projectDto ->
            projectDto.toProject(
                projectState = getState(projectDto.stateId),
                projectTasks = getTasksForProject(projectDto.id)
            )
        }
    }

    private suspend fun getState(stateId: UUID): State {
        return dataSource.getAllStates()
            .first { it.id == stateId }
            .toState()
    }

    private suspend fun getTasksForProject(projectId: UUID): List<Task> {
        return dataSource.getTasksByProjectId(projectId)
            .map { it.toTask(taskState = getState(it.stateId)) }
    }
}