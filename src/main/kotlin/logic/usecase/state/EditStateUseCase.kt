package logic.usecase.state

import logic.exception.EmptyNameException
import logic.exception.EntityNotChangedException
import logic.model.State
import logic.repositories.StatesRepository

class EditStateUseCase(
    private val repository: StatesRepository
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