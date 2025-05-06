package data.repositories

import data.repositories.mappers.toProject
import data.repositories.mappers.toProjectDto
import data.repositories.mappers.toState
import data.repositories.mappers.toTask
import logic.exception.projectNotFoundException
import logic.model.Project
import logic.model.State
import logic.model.Task
import logic.model.User
import logic.repositories.ProjectsRepository
import java.util.*

class ProjectsRepositoryImpl(
    private val csvDataSource: DataSource
) : ProjectsRepository {

    override fun createProject(project: Project, user: User) {
        return csvDataSource.createProject(project.toProjectDto())
    }

    override fun editProject(newProject: Project) {
        return csvDataSource.editProject(newProject.toProjectDto())
    }

    override fun deleteProject(projectId: UUID) {
        val projectsDao = csvDataSource.getAllProjects()
            .find { it.id == projectId } ?: throw projectNotFoundException()
        return csvDataSource.deleteProjectById(projectsDao)
    }

    override fun getProjectById(projectId: UUID): Project {
        val projectDto = csvDataSource.getProjectById(projectId)
        return projectDto.toProject(
            projectState = getState(projectDto.stateId),
            projectTasks = getTasksForProject(projectId),
        )
    }

    override fun getAllProjects(): List<Project> {
        return csvDataSource.getAllProjects().map { projectDto ->
            val state = getState(projectDto.stateId)
            val tasks = getTasksForProject(projectDto.id)
            projectDto.toProject(
                projectState = state,
                projectTasks = tasks
            )
        }
    }

    private fun getState(stateId: UUID): State {
        return csvDataSource.getAllStates()
            .first { it.id == stateId }
            .toState()
    }

    private fun getTasksForProject(projectId: UUID): List<Task> {
        return csvDataSource.getTasksByProjectId(projectId)
            .map { it.toTask(taskState = getState(it.stateId)) }
    }
}