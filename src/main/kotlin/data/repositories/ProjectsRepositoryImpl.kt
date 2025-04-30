package data.repositories

import data.dataSource.DataSource
import data.repositories.mappers.toProjectDto
import logic.model.Project
import logic.model.User
import logic.repositories.ProjectsRepository

class ProjectsRepositoryImpl(
    private val dataSource: DataSource
) : ProjectsRepository {
    override fun createProject(project: Project,user: User): Result<Unit> {
        return dataSource.createProject(project.toProjectDto())
    }
}