package logic.repositories

import data.dto.StateDto
import data.dto.UserDto
import logic.model.State

interface StatesRepository {
    suspend fun createState(state: StateDto): Boolean
    suspend fun editState(state: State)
    suspend fun getAllStates(): List<State>
}