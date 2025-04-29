package data.repositories

import data.dataSource.DataSource
import logic.model.Project
import logic.repositories.ProjectsRepository
import java.util.UUID

class ProjectsRepositoryImpl (
    private val dataSource: DataSource
): ProjectsRepository {
    override fun getProjectById(projectId: UUID): Result<Project> {
        return Result.failure(NoSuchElementException())
    }
}