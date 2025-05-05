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
import java.util.*

class ProjectsRepositoryImpl(
    private val dataSource: DataSource
) : ProjectsRepository {

    override fun createProject(project: Project, user: User): Result<Unit> {
        return dataSource.createProject(project.toProjectDto())
    }

    override fun editProject(newProject: Project) {
        dataSource.editProject(newProject.toProjectDto())
    }

    override fun deleteProject(projectId: UUID): Result<Unit> {
        val projectsDao =
            dataSource.getAllProjects().find { it.id == projectId } ?: return Result.failure(
                Exception()
            )
        dataSource.deleteProjectById(projectsDao)
        return Result.success(Unit)
    }

    override fun getProjectById(projectId: UUID): Result<Project> {
        return try {
            val projectDto = dataSource.getProjectById(projectId)

            Result.success(
                projectDto.toProject(
                    projectState = getState(projectDto.stateId),
                    projectTasks = getTasksForProject(projectId),
                )
            )
        } catch (exception: NoSuchElementException) {
            return Result.failure(exception)
        } catch (exception: Exception) {
            return Result.failure(exception)
        }
    }

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
            .map { it.toTask(taskState = getState(it.stateId)) }
    }
}