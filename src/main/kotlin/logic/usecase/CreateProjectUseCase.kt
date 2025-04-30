package logic.usecase

import data.dto.UserType
import logic.model.Project
import logic.model.State
import logic.model.User
import logic.repositories.ProjectsRepository
import java.util.*

class CreateProjectUseCase(
    private val repository: ProjectsRepository
) {
    //initial implementation
    fun createProject(project: Project, user: User): Result<Project> {
        val validProject = Project(
            id = UUID.randomUUID(),
            createdBy = UUID.randomUUID(),
            title = "food",
            description = "",
            tasks = emptyList(),
            state = State(id = UUID.randomUUID(), title = "TODO")
        )
        if (user.type==UserType.MATE){
            return Result.failure(IllegalArgumentException())
        }
           return Result.success(validProject)
    }
}