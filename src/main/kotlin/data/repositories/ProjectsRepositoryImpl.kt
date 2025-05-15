package data.repositories

import data.dataSource.remoteDataSource.RemoteDataSource
import data.repositories.mappers.toProject
import data.repositories.mappers.toProjectDto
import data.repositories.mappers.toState
import data.repositories.mappers.toTask
import data.customException.PlanMateException
import logic.model.Project
import logic.model.State
import logic.model.Task
import logic.model.User
import logic.repositories.ProjectsRepository
import java.util.*

class ProjectsRepositoryImpl(
    private val remoteDataSource: RemoteDataSource
) : ProjectsRepository, BaseRepository() {

    override suspend fun createProject(project: Project, user: User): Boolean {
	    return wrap {
            remoteDataSource.createProject(project.toProjectDto())
        }
    }

    override suspend fun editProject(newProject: Project) {
        return wrap { remoteDataSource.editProject(newProject.toProjectDto()) }
    }

    override suspend fun deleteProject(projectId: UUID) {
        return wrap {
            val projectsDao = remoteDataSource.getAllProjects()
                .find { it.id == projectId } ?: throw PlanMateException.NetworkException.IOException()
            remoteDataSource.deleteProjectById(projectsDao)
        }
    }

    override suspend fun getProjectById(projectId: UUID): Project {
        return wrap {
            val projectDto = remoteDataSource.getProjectById(projectId)
            projectDto.toProject(
                projectState = getState(projectDto.stateId!!),
                projectTasks = getTasksForProject(projectId),
            )
        }
    }

    override suspend fun getAllProjects(): List<Project> {
        return wrap {
           val allProjects =  remoteDataSource.getAllProjects()
            if (allProjects.isEmpty()) emptyList<Project>()
            else
            allProjects.map { projectDto ->
                projectDto.toProject(
                    projectState = getState(projectDto.stateId!!),
                    projectTasks = getTasksForProject(projectDto.id!!)
                )
            }
        }
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