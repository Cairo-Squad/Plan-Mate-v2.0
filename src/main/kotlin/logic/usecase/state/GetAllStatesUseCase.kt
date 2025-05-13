package logic.usecase.state

import logic.model.State
import logic.repositories.StatesRepository

class GetAllStatesUseCase(
    private val statesRepository: StatesRepository
) {
    suspend fun getAllStateById(): List<State> {
        return statesRepository.getAllStates()
    }
}