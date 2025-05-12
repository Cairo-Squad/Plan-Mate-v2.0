package data.repositories

import data.dataSource.remoteDataSource.RemoteDataSource
import data.dto.StateDto
import data.repositories.mappers.toProjectDto
import data.repositories.mappers.toState
import data.repositories.mappers.toStateDto
import logic.model.State
import logic.repositories.StatesRepository

class StatesRepositoryImpl(
    private val remoteDataSource: RemoteDataSource
) : StatesRepository, BaseRepository() {

    override suspend fun createState(state: State): Boolean {
        return wrap {
            val x = remoteDataSource.createState(state.toStateDto())
            println("repo"+ x)
             x
        }
    }

    override suspend fun editState(state: State) {
        return wrap { remoteDataSource.editState(state.toStateDto()) }
    }

    override suspend fun getAllStates(): List<State> {
        return wrap { remoteDataSource.getAllStates().map { it.toState() } }
    }
}