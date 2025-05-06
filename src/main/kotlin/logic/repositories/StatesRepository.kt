package logic.repositories

import data.dto.StateDto
import data.dto.UserDto
import logic.model.State

interface StatesRepository {
    fun createState(state: StateDto): Boolean
    fun editState(state: State)
    fun getAllStates(): List<State>
}