package logic.usecase.project

import logic.exception.EmptyNameException
import logic.model.Project
import logic.repositories.ProjectsRepository

class EditProjectUseCase(private val projectsRepository: ProjectsRepository) {

    fun editProject(newProject: Project){
            validateNewProject(newProject)
            projectsRepository.editProject(newProject)
    }

    private fun validateNewProject(newProject:Project) {
        if (newProject.title.isBlank()) {
            throw EmptyNameException()
        }
    }
}