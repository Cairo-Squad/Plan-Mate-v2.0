package data.repositories

import data.dataSource.DataSource
import data.dto.StateDto
import data.dto.UserDto
import logic.repositories.StatesRepository

class StatesRepositoryImpl(
    private val dataSource: DataSource
) : StatesRepository {
    override fun createState(state: StateDto, userDto: UserDto): Boolean {
       return false
    }
}