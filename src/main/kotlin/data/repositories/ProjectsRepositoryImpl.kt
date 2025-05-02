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
    private val csvDataSource: DataSource
) : ProjectsRepository {

    override fun createProject(project: Project, user: User): Result<Unit> {
        return csvDataSource.createProject(project.toProjectDto())
    }

    override fun editProject(newProject: Project) {
        csvDataSource.editProject(newProject.toProjectDto())
    }

    override fun deleteProject(projectId: UUID): Result<Unit> {
        val projectsDao =
            csvDataSource.getAllProjects().find { it.id == projectId } ?: return Result.failure(
                Exception()
            )
        csvDataSource.deleteProjectById(projectsDao)
        return Result.success(Unit)
    }

    override fun getProjectById(projectId: UUID): Result<Project> {
        return try {
            val projectDto = csvDataSource.getProjectById(projectId)

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
        val projects = csvDataSource.getAllProjects().map { projectDto ->
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
        return csvDataSource.getAllStates()
            .first { it.id == stateId }
            .toState()
    }

    private fun getTasksForProject(projectId: UUID): List<Task> {
        return csvDataSource.getTasksByProjectId(projectId)
            .map { it.toTask(taskState = getState(it.stateId)) }
    }
}