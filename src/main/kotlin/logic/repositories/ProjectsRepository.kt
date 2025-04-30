package logic.repositories

import logic.model.Project
import logic.model.User

interface ProjectsRepository {
    fun createProject(project: Project,user: User):Result<Unit>
}