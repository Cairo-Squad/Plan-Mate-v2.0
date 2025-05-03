package logic.usecase.state

import logic.model.State
import logic.repositories.StatesRepository

class GetAllStatesUseCase(
    private val statesRepository: StatesRepository
) {
    fun execute(): List<State> {
        return statesRepository.getAllStates()
    }
}