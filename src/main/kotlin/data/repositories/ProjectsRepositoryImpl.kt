package data.repositories

import data.dataSource.DataSource
import logic.repositories.ProjectsRepository
import java.util.*

class ProjectsRepositoryImpl(
    private val dataSource: DataSource
) : ProjectsRepository {

    override fun editProjectTitle(newTitle: String, projectId: UUID) =
        dataSource.editProjectTitle(newTitle, projectId)

    override fun editProjectDescription(newDescription: String, projectId: UUID) =
        dataSource.editProjectDescription(newDescription, projectId)


}