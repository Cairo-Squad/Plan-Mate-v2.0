package logic.usecase.state

import logic.model.State
import logic.repositories.StatesRepository
import java.util.UUID

class CreateStateUseCase(
    private val stateRepository : StatesRepository,
    private val validationState : ValidationState
) {
    suspend fun createState(state : State) : UUID {
        validationState.validateState(state)
        return stateRepository.createState(state)
    }
}