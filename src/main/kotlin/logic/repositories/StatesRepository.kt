package logic.repositories

import logic.model.State
import java.util.UUID

interface StatesRepository {
    suspend fun createState(state : State) : UUID
    suspend fun editState(state : State)
    suspend fun getAllStates() : List<State>
    suspend fun getStateById(id : UUID) : State
}