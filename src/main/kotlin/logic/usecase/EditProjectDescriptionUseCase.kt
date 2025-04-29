package logic.usecase

import logic.repositories.ProjectsRepository
import java.util.UUID

class EditProjectDescriptionUseCase(private val projectsRepository: ProjectsRepository) {

    fun editProjectDescription(newDescription: String, projectID: UUID):Result<Unit>{
        TODO()
    }
}