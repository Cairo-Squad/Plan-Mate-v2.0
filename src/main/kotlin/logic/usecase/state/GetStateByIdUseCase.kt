package logic.usecase.state

import logic.model.State
import logic.repositories.StatesRepository
import java.util.*

class GetStateByIdUseCase(
    private val stateRepository : StatesRepository
) {
    suspend fun getStateById(stateId : UUID) : State {
        return stateRepository.getStateById(stateId)
    }
}