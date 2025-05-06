package data.repositories

import data.dto.StateDto
import data.dto.UserDto
import data.repositories.mappers.toState
import data.repositories.mappers.toStateDto
import logic.model.State
import logic.repositories.StatesRepository

class StatesRepositoryImpl(
    private val dataSource: DataSource
) : StatesRepository {

    override fun createState(state: StateDto): Boolean {
        return dataSource.createState(state)
    }

    override fun editState(state: State) {
        return dataSource.editState(state.toStateDto())
    }

    override fun getAllStates(): List<State> {
        return dataSource.getAllStates().map { it.toState() }
    }
}