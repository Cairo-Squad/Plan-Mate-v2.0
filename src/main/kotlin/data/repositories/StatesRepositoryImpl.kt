package data.repositories

import data.dto.StateDto
import data.dto.UserDto
import data.repositories.mappers.toState
import data.repositories.mappers.toStateDto
import logic.model.State
import logic.repositories.StatesRepository

class StatesRepositoryImpl(
    private val csvDataSource: DataSource
) : StatesRepository, BaseRepository() {

    override fun createState(state: StateDto, userDto: UserDto): Boolean {
        return tryToExecute { csvDataSource.createState(state, userDto) }
    }

    override fun editState(state: State) {
        return tryToExecute { csvDataSource.editState(state.toStateDto()) }
    }

    override fun getAllStates(): List<State> {
        return tryToExecute { csvDataSource.getAllStates().map { it.toState() } }
    }
}