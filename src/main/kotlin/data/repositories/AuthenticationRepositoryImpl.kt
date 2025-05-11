package data.repositories

import data.dataSource.remoteDataSource.RemoteDataSource
import data.dto.UserType
import data.hashing.PasswordEncryptor
import data.repositories.mappers.toUser
import data.repositories.mappers.toUserDto
import logic.exception.NotFoundException
import logic.model.User
import logic.repositories.AuthenticationRepository
import java.util.*

class AuthenticationRepositoryImpl(
    private val remoteDataSource: RemoteDataSource,
    private val passwordEncryptor: PasswordEncryptor
) : AuthenticationRepository, BaseRepository() {

    override suspend fun getAllUsers(): List<User> {
        return wrap {
            val usersDto = remoteDataSource.getAllUsers()
            usersDto.map { it.toUser() }
        }
    }

    override suspend fun deleteUser(userId: UUID): Boolean {
        return wrap {
            val userDto = remoteDataSource.getAllUsers()
                .find { it.id == userId } ?: throw NotFoundException()
            remoteDataSource.deleteUser(userDto)
            true
        }
    }

    override suspend fun createUser(user: User): Boolean {
        return wrap {
            val hashedPassword = passwordEncryptor.hashPassword(user.password)
            val updatedUser = user.copy(id = UUID.randomUUID(), password = hashedPassword)
            remoteDataSource.createUser(updatedUser.toUserDto())
        }
    }


    override suspend fun editUser(user: User) {
        return wrap { remoteDataSource.editUser(user.toUserDto()) }
    }

    override suspend fun loginUser(name: String, password: String): Boolean {
        return wrap {
            val hashedPassword = passwordEncryptor.hashPassword(password)
            remoteDataSource.loginUser(name, hashedPassword)
        }
    }

    override suspend fun getCurrentUser(): User? {
        return wrap {
            remoteDataSource.getCurrentUser()?.toUser()
        }
    }
}
