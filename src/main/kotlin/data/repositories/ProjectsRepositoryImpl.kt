package data.repositories

import data.dataSource.remoteDataSource.RemoteDataSource
import data.repositories.mappers.toProject
import data.repositories.mappers.toProjectDto
import data.repositories.mappers.toState
import data.repositories.mappers.toTask
import logic.exception.NotFoundException
import logic.model.Project
import logic.model.State
import logic.model.Task
import logic.model.User
import logic.repositories.ProjectsRepository
import java.util.*

class ProjectsRepositoryImpl(
    private val remoteDataSource: RemoteDataSource
) : ProjectsRepository, BaseRepository() {

    override suspend fun createProject(project: Project, user: User): UUID? {
	    return wrap { remoteDataSource.createProject(project.toProjectDto())}
    }

    override suspend fun editProject(newProject: Project) {
        return wrap { remoteDataSource.editProject(newProject.toProjectDto()) }
    }

    override suspend fun deleteProject(projectId: UUID) {
        return wrap {
            val projectsDao = remoteDataSource.getAllProjects()
                .find { it.id == projectId } ?: throw NotFoundException()
            remoteDataSource.deleteProjectById(projectsDao)
        }
    }

    override suspend fun getProjectById(projectId: UUID): Project {
        return wrap {
            val projectDto = remoteDataSource.getProjectById(projectId)
            projectDto.toProject(
                projectState = getState(projectDto.stateId),
                projectTasks = getTasksForProject(projectId),
            )
        }
    }

    override suspend fun getAllProjects(): List<Project> {
        return wrap {
            remoteDataSource.getAllProjects().map { projectDto ->
                projectDto.toProject(
                    projectState = createDefaultState(),
                    projectTasks = getTasksForProject(projectDto.id!!)
                )
            }
        }
    }
    
    // Create a default state to use when a referenced state cannot be found
    private fun createDefaultState(): State {
        return State(
            id = UUID.randomUUID(),
            title = "Unknown State",
        )
    }

    private suspend fun getState(stateId: UUID): State {
        return wrap {
            remoteDataSource.getAllStates()
                .first { it.id == stateId }
                .toState()
        }
    }

    private suspend fun getTasksForProject(projectId: UUID): List<Task> {
        return wrap {
            remoteDataSource.getTasksByProjectId(projectId)
                .map { it.toTask(taskState = getState(it.stateId)) }
        }
    }
}