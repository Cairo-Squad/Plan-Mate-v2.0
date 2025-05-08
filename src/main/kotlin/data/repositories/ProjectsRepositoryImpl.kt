package data.repositories

import data.repositories.mappers.toProject
import data.repositories.mappers.toProjectDto
import data.repositories.mappers.toState
import data.repositories.mappers.toTask
import logic.model.Project
import logic.model.State
import logic.model.Task
import logic.model.User
import logic.repositories.ProjectsRepository
import logic.util.NotFoundException
import java.util.*

class ProjectsRepositoryImpl(
    private val dataSource: DataSource
) : ProjectsRepository, BaseRepository() {
    override fun createProject(project: Project, user: User) {
        return wrap { dataSource.createProject(project.toProjectDto()) }
    }

    override fun editProject(newProject: Project) {
        return wrap { dataSource.editProject(newProject.toProjectDto()) }
    }

    override fun deleteProject(projectId: UUID) {
        return wrap {
            val projectsDao = dataSource.getAllProjects()
                .find { it.id == projectId } ?: throw NotFoundException()
            dataSource.deleteProjectById(projectsDao)
        }
    }

    override fun getProjectById(projectId: UUID): Project {
        return wrap {
            val projectDto = dataSource.getProjectById(projectId)
            projectDto.toProject(
                projectState = getState(projectDto.stateId),
                projectTasks = getTasksForProject(projectId),
            )
        }
    }

    override fun getAllProjects(): List<Project> {
        return wrap {
            dataSource.getAllProjects().map { projectDto ->
                projectDto.toProject(
                    projectState = getState(projectDto.stateId),
                    projectTasks = getTasksForProject(projectDto.id)
                )
            }
        }
    }

    private fun getState(stateId: UUID): State {
        return wrap { dataSource.getAllStates().first { it.id == stateId }.toState() }
    }

    private fun getTasksForProject(projectId: UUID): List<Task> {
        return wrap {
            dataSource.getTasksByProjectId(projectId)
                .map { it.toTask(taskState = getState(it.stateId)) }
        }
    }
}