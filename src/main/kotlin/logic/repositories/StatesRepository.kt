package logic.repositories

import data.dto.StateDto
import data.dto.UserDto

interface StatesRepository {
	fun createState(state: StateDto, userDto: UserDto): Boolean
}