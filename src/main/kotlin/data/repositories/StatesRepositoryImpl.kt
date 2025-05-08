package data.repositories

import data.dto.StateDto
import data.repositories.mappers.toState
import data.repositories.mappers.toStateDto
import logic.model.State
import logic.repositories.StatesRepository

class StatesRepositoryImpl(
    private val dataSource: DataSource
) : StatesRepository, BaseRepository() {

    override fun createState(state: StateDto): Boolean {
        return wrap { dataSource.createState(state) }
    }

    override fun editState(state: State) {
        return wrap { dataSource.editState(state.toStateDto()) }
    }

    override fun getAllStates(): List<State> {
        return wrap { dataSource.getAllStates().map { it.toState() } }
    }
}