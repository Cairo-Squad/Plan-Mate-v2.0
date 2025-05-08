package data.repositories

import data.dto.UserDto
import data.dto.UserType
import data.hashing.PasswordEncryptor
import data.repositories.mappers.toUser
import data.repositories.mappers.toUserDto
import logic.model.User
import logic.repositories.AuthenticationRepository
import logic.util.NotFoundException
import java.util.*

class AuthenticationRepositoryImpl(
    private val dataSource: DataSource,
    private val passwordEncryptor: PasswordEncryptor
) : AuthenticationRepository, BaseRepository() {

    override fun getAllUsers(): List<User> {
        return wrap {
            val usersDto = dataSource.getAllUsers()
            usersDto.map { it.toUser() }
        }
    }

    override fun deleteUser(userId: UUID): Boolean {
        return wrap {
            val userDto = dataSource.getAllUsers()
                .find { it.id == userId } ?: throw NotFoundException()
            dataSource.deleteUser(userDto)
            true
        }
    }

    override fun createUser(id: UUID, name: String, password: String, userType: UserType): UserDto {
        return wrap {
            val hashedPassword = passwordEncryptor.hashPassword(password)
            dataSource.createUser(id, name, hashedPassword, userType)
        }
    }

    override fun editUser(user: User) {
        return wrap { dataSource.editUser(user.toUserDto()) }
    }
}
