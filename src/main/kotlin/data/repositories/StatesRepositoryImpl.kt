package data.repositories

import data.dataSource.remoteDataSource.RemoteDataSource
import data.repositories.mappers.toState
import data.repositories.mappers.toStateDto
import logic.model.State
import logic.repositories.StatesRepository
import java.util.UUID

class StatesRepositoryImpl(
    private val remoteDataSource : RemoteDataSource
) : StatesRepository, BaseRepository() {

    override suspend fun createState(state : State) : UUID {
        return wrap {
            remoteDataSource.createState(state.toStateDto())
        }
    }

    override suspend fun editState(state : State) {
        return wrap { remoteDataSource.editState(state.toStateDto()) }
    }

    override suspend fun getAllStates() : List<State> {
        return wrap { remoteDataSource.getAllStates().map { it.toState() } }
    }

    override suspend fun getStateById(stateId : UUID) : State {
        return wrap { remoteDataSource.getStateById(stateId).toState() }
    }
}