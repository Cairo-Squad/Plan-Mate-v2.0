package data.repositories

import data.dataSource.DataSource
import logic.model.Project
import logic.repositories.ProjectsRepository

class ProjectsRepositoryImpl(
    private val dataSource: DataSource
) : ProjectsRepository {
    override fun getAllProjects(): Result<List<Project>> {
        return Result.failure(NoSuchElementException())
    }
}