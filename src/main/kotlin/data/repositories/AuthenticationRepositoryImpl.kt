package data.repositories

import data.dto.UserDto
import data.dto.UserType
import data.hashing.PasswordEncryptor
import data.repositories.mappers.toUser
import data.repositories.mappers.toUserDto
import logic.model.User
import logic.repositories.AuthenticationRepository
import java.util.UUID

class AuthenticationRepositoryImpl(
    private val csvDataSource: DataSource,
    private val passwordEncryptor: PasswordEncryptor
) : AuthenticationRepository {

    override fun getAllUsers(): List<User> {
        val usersDto = csvDataSource.getAllUsers()
        return usersDto.map { it.toUser() }
    }

    override fun deleteUser(userId: UUID): Boolean {
        val userDto = csvDataSource.getAllUsers().find { it.id == userId } ?: return false
        csvDataSource.deleteUser(userDto)
        return true
    }

    override fun createUser(id: UUID, name: String, password: String, userType: UserType): UserDto {
        val hashedPassword = passwordEncryptor.hashPassword(password)
        return csvDataSource.createUser(id, name, hashedPassword, userType)
    }

    override fun editUser(user: User) {
        return csvDataSource.editUser(user.toUserDto())
    }
}
