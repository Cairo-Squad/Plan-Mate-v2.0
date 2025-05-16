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

    override suspend fun createProject(project: Project): UUID {
	    return wrap { remoteDataSource.createProject(project.toProjectDto()) }
    }

    override suspend fun editProject(newProject: Project) {
        return wrap { remoteDataSource.editProject(newProject.toProjectDto()) }
    }

    override suspend fun deleteProject(projectId: UUID) {
        return wrap { remoteDataSource.deleteProjectById(projectId) }
    }

    override suspend fun getProjectById(projectId: UUID): Project {
        return wrap {
            val projectDto = remoteDataSource.getProjectById(projectId)
            projectDto.toProject(
                projectState = getState(projectDto.stateId!!),
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
}