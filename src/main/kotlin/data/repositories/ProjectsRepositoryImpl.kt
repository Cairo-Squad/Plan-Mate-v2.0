package data.repositories

import data.dataSource.DataSource
import logic.repositories.ProjectsRepository
import java.util.*

class ProjectsRepositoryImpl(
    private val dataSource: DataSource
) : ProjectsRepository {

    override fun editProjectTitle(newTitle: String, projectId: UUID) {
        TODO()
    }

    override fun editProjectDescription(newDescription: String, projectId: UUID) {
        TODO()
    }


}