package data.repositories

import data.dataSource.DataSource
import data.dto.UserDto
import data.dto.UserType
import data.hashing.PasswordHasher
import data.repositories.mappers.toUser
import logic.model.User
import logic.repositories.AuthenticationRepository
import java.util.UUID

class AuthenticationRepositoryImpl(
    private val dataSource : DataSource,
    private val passwordConverter : PasswordHasher
) : AuthenticationRepository {

    override fun getAllUsers() : List<User> {
        val usersDto = dataSource.getAllUsers()
        return usersDto.map { it.toUser() }
    }


    override fun deleteUser(userId : UUID) : Boolean {
        val userDto = dataSource.getAllUsers().find { it.id == userId } ?: return false
        dataSource.deleteUser(userDto)
        return true
    }

    override fun createUser(id : UUID, name : String, password : String, userType : UserType) : UserDto {
        val hashedPassword = passwordConverter.hashPassword(password)
        return dataSource.createUser(id, name, hashedPassword, userType)
    }

    override fun editUser(userId: UUID): Boolean {
        return true
    }
}
