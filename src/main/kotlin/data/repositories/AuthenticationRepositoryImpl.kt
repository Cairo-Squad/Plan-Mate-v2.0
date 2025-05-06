package data.repositories

import data.dto.UserType
import data.hashing.PasswordEncryptor
import data.repositories.mappers.toUser
import data.repositories.mappers.toUserDto
import logic.exception.UserNotFoundException
import logic.model.User
import logic.repositories.AuthenticationRepository
import java.util.*

class AuthenticationRepositoryImpl(
    private val csvDataSource: DataSource,
    private val passwordEncryptor: PasswordEncryptor
) : AuthenticationRepository {

    override fun getAllUsers(): List<User> {
        val usersDto = csvDataSource.getAllUsers()
        return usersDto.map { it.toUser() }
    }

    override fun deleteUser(userId: UUID) {
        val userDto = csvDataSource.getAllUsers()
            .find { it.id == userId }
            ?: throw UserNotFoundException()
        return csvDataSource.deleteUser(userDto)
    }

    override fun createUser(id: UUID, name: String, password: String, userType: UserType) {
        val hashedPassword = passwordEncryptor.hashPassword(password)
        return csvDataSource.createUser(id, name, hashedPassword, userType)
    }

    override fun editUser(user: User) {
        return csvDataSource.editUser(user.toUserDto())
    }
}
