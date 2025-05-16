package data.repositories

import data.dataSource.remoteDataSource.RemoteDataSource
import data.repositories.mappers.toUser
import data.repositories.mappers.toUserDto
import logic.model.User
import logic.model.UserType
import logic.repositories.AuthenticationRepository
import java.util.*

class AuthenticationRepositoryImpl(
    private val remoteDataSource: RemoteDataSource,
) : AuthenticationRepository, BaseRepository() {

    override suspend fun getAllUsers(): List<User> {
        return wrap {
            val usersDto = remoteDataSource.getAllUsers()
            usersDto.map { it.toUser() }
        }
    }

    override suspend fun deleteUser(userId: UUID) {
        return wrap {
            remoteDataSource.deleteUser(userId)
        }
    }

    override suspend fun signUp(userName: String, userPassword: String, userType: UserType) {
        return wrap {
            remoteDataSource.signUp(userName, userPassword, userType)
        }
    }

    override suspend fun editUser(user: User){
        return wrap { remoteDataSource.editUser(user.toUserDto()) }
    }

    override suspend fun loginUser(name: String, password: String) {
        return wrap {
            remoteDataSource.loginUser(name, password)
        }
    }

    override suspend fun getCurrentUser(): User? {
        return wrap {
            remoteDataSource.getCurrentUser()?.toUser()
        }
    }
}
