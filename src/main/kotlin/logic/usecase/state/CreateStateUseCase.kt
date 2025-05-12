package logic.usecase.state

import logic.model.State
import logic.repositories.StatesRepository

class CreateStateUseCase(
    private val stateRepository: StatesRepository,
    private val validationState: ValidationState
) {
    suspend fun createState(state: State): State {
        validationState.validateState(state)
        return stateRepository.createState(state)
    }
}