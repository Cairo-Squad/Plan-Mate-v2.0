package logic.repositories

import java.util.*

interface ProjectsRepository {

    fun editProjectTitle(newTitle: String, projectId: UUID)
    fun editProjectDescription(newDescription: String , projectId: UUID)
}