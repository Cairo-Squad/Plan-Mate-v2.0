package data.repositories

import data.dto.StateDto
import data.dto.UserDto
import data.repositories.mappers.toState
import data.repositories.mappers.toStateDto
import logic.model.State
import logic.repositories.StatesRepository

class StatesRepositoryImpl(
    private val csvDataSource: DataSource
) : StatesRepository {

    override fun createState(state: StateDto, userDto: UserDto): Boolean {
        return csvDataSource.createState(state, userDto)
    }

    override fun editState(state: State) {
        return csvDataSource.editState(state.toStateDto())
    }

    override fun getAllStates(): List<State> {
        return csvDataSource.getAllStates().map { it.toState() }
    }
}