package data.repositories

import data.dataSource.DataSource
import data.dto.ProjectDto
import logic.repositories.ProjectsRepository

class ProjectsRepositoryImpl(
    private val dataSource: DataSource
) : ProjectsRepository {
    override fun getProject(): List<ProjectDto> {
        return dataSource.getAllProjects()
    }

}