package data.repositories

import data.customException.PlanMateException
import data.dataSource.remoteDataSource.RemoteDataSource
import data.hashing.PasswordEncryptor
import data.repositories.mappers.toUser
import data.repositories.mappers.toUserDto
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
            remoteDataSource.deleteUser(userId)
        }
    }

    override suspend fun createUser(user: User): Boolean {
        return wrap {
            remoteDataSource.createUser(user.toUserDto())
        }
    }

    override suspend fun editUser(user: User): Boolean {
        return wrap { remoteDataSource.editUser(user.toUserDto()) }
    }

    override suspend fun loginUser(name: String, password: String): Boolean {
        return wrap {
            val hashedPassword = passwordEncryptor.hashPassword(password)
            if (!remoteDataSource.loginUser(name, hashedPassword)) {
                throw PlanMateException.ValidationException.InvalidCredentialsException()
            }
            true
        }
    }

    override suspend fun getCurrentUser(): User? {
        return wrap {
            remoteDataSource.getCurrentUser()?.toUser()
        }
    }
}
