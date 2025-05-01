package data.repositories

import data.dataSource.DataSource
import data.dto.ProjectDto
import data.repositories.mappers.toProject
import data.repositories.mappers.toProjectDto
import data.repositories.mappers.toState
import logic.model.Project
import logic.model.State
import logic.repositories.ProjectsRepository
import java.util.UUID

class ProjectsRepositoryImpl(
    private val dataSource: DataSource
) : ProjectsRepository {
    override fun getAllProjects(): List<Project> {
        val projectsDao = dataSource.getAllProjects()
        return projectsDao.map {
            it.toProject(
                projectTasks = emptyList(),
                projectState = State(id = UUID.randomUUID(), title = "Done")
            )
        }
    }

    override fun deleteProject(projectId: UUID): Result<Unit> {
        val projectsDao = dataSource.getAllProjects().find { it.id == projectId } ?: return Result.failure(Exception())
        dataSource.deleteProjectById(projectsDao)
        return Result.success(Unit)
    }
}

