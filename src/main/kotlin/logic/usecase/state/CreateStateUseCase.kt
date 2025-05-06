package logic.usecase.state

import data.repositories.mappers.toStateDto
import data.repositories.mappers.toUserDto
import logic.model.State
import logic.model.User
import logic.repositories.StatesRepository

class CreateStateUseCase(
    private val stateRepo: StatesRepository
) {
    fun createState(state: State): Boolean {
        return stateRepo.createState(state.toStateDto())
    }
}