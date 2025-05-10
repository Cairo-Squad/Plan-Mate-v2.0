package logic.usecase.state

import logic.model.State
import logic.repositories.StatesRepository

class EditStateUseCase(
    private val repository: StatesRepository
) {
    suspend fun editState(newState: State, oldState: State) {
        repository.editState(state = newState)
    }


}