package data.repositories

import data.dto.UserDto
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
) : AuthenticationRepository, BaseRepository() {

    override fun getAllUsers(): List<User> {
        return tryToExecute {
            val usersDto = csvDataSource.getAllUsers()
            usersDto.map { it.toUser() }
        }
    }

    override fun deleteUser(userId: UUID): Boolean {
        return tryToExecute {
            val userDto = csvDataSource.getAllUsers()
                .find { it.id == userId }
                ?: throw UserNotFoundException()
            csvDataSource.deleteUser(userDto)
            true
        }
    }

    override fun createUser(id: UUID, name: String, password: String, userType: UserType): UserDto {
        val hashedPassword = passwordEncryptor.hashPassword(password)
        return tryToExecute { csvDataSource.createUser(id, name, hashedPassword, userType) }
    }

    override fun editUser(user: User) {
        return tryToExecute { csvDataSource.editUser(user.toUserDto()) }
    }
}
