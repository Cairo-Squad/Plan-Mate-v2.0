package logic.usecase.state

import logic.exception.EmptyNameException
import logic.exception.EntityNotChangedException
import logic.model.State
import logic.repositories.StatesRepository

class EditStateUseCase(
    private val repository: StatesRepository
) {
    fun editState(newState: State, oldState: State) {

        repository.editState(state = newState)
    }


}