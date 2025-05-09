package data.repositories

import data.dataSource.remoteDataSource.mongo.RemoteDataSource
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
    private val remoteDataSource: RemoteDataSource
) : ProjectsRepository {

    override suspend fun createProject(project: Project, user: User) {
        return remoteDataSource.createProject(project.toProjectDto())
    }

    override suspend fun editProject(newProject: Project) {
        return remoteDataSource.editProject(newProject.toProjectDto())
    }

    override suspend fun deleteProject(projectId: UUID) {
        val projectsDao = remoteDataSource.getAllProjects()
            .find { it.id == projectId } ?: throw ProjectNotFoundException()
        return remoteDataSource.deleteProjectById(projectsDao)
    }

    override suspend fun getProjectById(projectId: UUID): Project {
        val projectDto = remoteDataSource.getProjectById(projectId)
        return projectDto.toProject(
            projectState = getState(projectDto.stateId),
            projectTasks = getTasksForProject(projectId),
        )
    }

    override suspend fun getAllProjects(): List<Project> {
        return remoteDataSource.getAllProjects().map { projectDto ->
            projectDto.toProject(
                projectState = getState(projectDto.stateId),
                projectTasks = getTasksForProject(projectDto.id)
            )
        }
    }

    private suspend fun getState(stateId: UUID): State {
        return remoteDataSource.getAllStates()
            .first { it.id == stateId }
            .toState()
    }

    private suspend fun getTasksForProject(projectId: UUID): List<Task> {
        return remoteDataSource.getTasksByProjectId(projectId)
            .map { it.toTask(taskState = getState(it.stateId)) }
    }
}