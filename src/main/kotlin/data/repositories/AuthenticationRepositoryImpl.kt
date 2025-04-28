package data.repositories

import data.dataSource.DataSource
import data.dto.UserDto
import logic.repositories.AuthenticationRepository

class AuthenticationRepositoryImpl (
    private val dataSource: DataSource
): AuthenticationRepository {
    override fun getUser() : List<UserDto> {
        return dataSource.getAllUsers()
    }
}