package data.repositories

import data.dataSource.DataSource
import data.dto.UserDto
import logic.repositories.AuthenticationRepository

class AuthenticationRepositoryImpl(
    private val dataSource: DataSource
) : AuthenticationRepository {
    override fun getAllUser() : List<UserDto> {
        TODO("Not yet implemented")
    }

    override fun loginUser(name : String, password : String) : Boolean {
        TODO("Not yet implemented")
    }

}