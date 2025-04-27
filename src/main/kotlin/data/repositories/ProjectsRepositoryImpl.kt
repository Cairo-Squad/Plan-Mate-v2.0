package data.repositories

import data.dataSource.DataSource
import logic.repositories.ProjectsRepository

class ProjectsRepositoryImpl (
    private val dataSource: DataSource
): ProjectsRepository {

}