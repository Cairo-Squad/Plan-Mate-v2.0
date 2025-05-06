package logic.usecase.state

import data.repositories.mappers.toStateDto
import data.repositories.mappers.toUserDto
import logic.model.State
import logic.model.User
import logic.repositories.StatesRepository

class CreateStateUseCase(
    private val stateRepository: StatesRepository
) {
    fun createState(state: State): Boolean {
        return stateRepository.createState(state.toStateDto())
    }
}