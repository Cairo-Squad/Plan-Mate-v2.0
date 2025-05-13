package logic.repositories

import logic.model.State

interface StatesRepository {
    suspend fun createState(state: State): Boolean
    suspend fun editState(state: State)
    suspend fun getAllStates(): List<State>
}