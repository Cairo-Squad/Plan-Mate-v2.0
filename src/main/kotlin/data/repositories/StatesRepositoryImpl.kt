package data.repositories

import data.dataSource.remoteDataSource.RemoteDataSource
import data.dto.StateDto
import data.repositories.mappers.toState
import data.repositories.mappers.toStateDto
import logic.model.State
import logic.repositories.StatesRepository

class StatesRepositoryImpl(
    private val remoteDataSource: RemoteDataSource
) : StatesRepository {

    override suspend fun createState(state: StateDto): Boolean {
        return remoteDataSource.createState(state)
    }

    override suspend fun editState(state: State) {
        return remoteDataSource.editState(state.toStateDto())
    }

    override suspend fun getAllStates(): List<State> {
        return remoteDataSource.getAllStates().map { it.toState() }
    }
}