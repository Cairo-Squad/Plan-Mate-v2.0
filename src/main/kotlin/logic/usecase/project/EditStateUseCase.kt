package logic.usecase.project

import logic.model.State
import logic.repositories.ProjectsRepository

class EditStateUseCase(
    private val repository: ProjectsRepository
) {
    fun editState(newState: State, oldState: State){

    }

}