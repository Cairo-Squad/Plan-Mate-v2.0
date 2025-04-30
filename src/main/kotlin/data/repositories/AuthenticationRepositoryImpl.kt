package data.repositories

import data.dataSource.DataSource
import data.dto.UserDto
import data.dto.UserType
import data.repositories.mappers.toUser
import logic.model.User
import logic.repositories.AuthenticationRepository

class AuthenticationRepositoryImpl(
    private val dataSource : DataSource
) : AuthenticationRepository {
    override fun getAllUser() : List<User> {
        val usersDto = dataSource.getAllUsers()
        return usersDto.map { it.toUser() }
    }


    override fun createUser(name : String, password : String, userType : UserType) {
        TODO("Not yet implemented")
    }

}