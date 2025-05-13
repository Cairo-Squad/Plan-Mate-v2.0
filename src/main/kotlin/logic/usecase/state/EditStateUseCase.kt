package logic.usecase.state

import logic.model.State
import logic.repositories.StatesRepository

class EditStateUseCase(
    private val statesRepository: StatesRepository,
    private val validationState: ValidationState
) {
    suspend fun editState(newState: State) {
        validationState.validateState(newState)
        statesRepository.editState(state = newState)
    }
}