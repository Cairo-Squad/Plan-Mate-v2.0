package logic.usecase.state

import data.repositories.mappers.toStateDto
import data.repositories.mappers.toUserDto
import logic.model.State
import logic.model.User
import logic.repositories.StatesRepository

class CreateStateUseCase(
    private val stateRepository: StatesRepository,
    private val validationState: ValidationState
) {
    suspend fun createState(state: State): Boolean {
        validationState.validateOfState(state)
        return stateRepository.createState(state)
    }
}