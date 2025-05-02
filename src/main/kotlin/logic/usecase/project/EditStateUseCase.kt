package logic.usecase.project

import logic.exception.EmptyNameException
import logic.exception.EntityNotChangedException
import logic.model.State
import logic.repositories.ProjectsRepository

class EditStateUseCase(
    private val repository: ProjectsRepository
) {
    fun editState(newState: State, oldState: State) {
        validateUserInputs(
            newState = newState,
            oldState = oldState
        )
        repository.editState(state = newState)
    }

    private fun validateUserInputs(newState: State, oldState: State) {
        if (newState == oldState) throw EntityNotChangedException()
        if (newState.title.isBlank()) throw EmptyNameException()
    }
}