package data.repositories

import data.dataSource.DataSource
import logic.model.Project
import logic.repositories.ProjectsRepository

class ProjectsRepositoryImpl(
    private val dataSource: DataSource
) : ProjectsRepository {

    override fun editProject(newProject: Project) {
        dataSource.editProject(newProject)
    }


}