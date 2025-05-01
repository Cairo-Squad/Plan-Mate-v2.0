package data.repositories

import data.dataSource.DataSource
import data.dto.ProjectDto
import data.repositories.mappers.toProjectDto
import logic.model.Project
import logic.repositories.ProjectsRepository
import java.util.UUID

class ProjectsRepositoryImpl(
    private val dataSource: DataSource
) : ProjectsRepository {
    override fun getAllProjects(): List<ProjectDto> {
        return dataSource.getAllProjects()
    }

}