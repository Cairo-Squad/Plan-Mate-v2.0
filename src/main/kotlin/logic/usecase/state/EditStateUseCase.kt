package logic.usecase.state

import logic.model.State
import logic.repositories.StatesRepository

class EditStateUseCase(
    private val repository: StatesRepository,
    private val validationState: ValidationState
) {
    suspend fun editState(newState: State) {
        validationState.validateOfState(newState)
        repository.editState(state = newState)
    }
}