package data.repositories

import data.dataSource.localDataSource.file.LocalDataSource
import data.dataSource.remoteDataSource.mongo.RemoteDataSource
import data.dto.UserType
import data.hashing.PasswordEncryptor
import data.repositories.mappers.toUser
import data.repositories.mappers.toUserDto
import logic.exception.UserNotFoundException
import logic.model.User
import logic.repositories.AuthenticationRepository
import java.util.UUID

class AuthenticationRepositoryImpl(
    private val remoteDataSource: RemoteDataSource,
    private val passwordEncryptor: PasswordEncryptor
) : AuthenticationRepository {

    override suspend fun getAllUsers(): List<User> {
        val usersDto = remoteDataSource.getAllUsers()
        return usersDto.map { it.toUser() }
    }

    override suspend fun deleteUser(userId: UUID): Boolean {
        val userDto = remoteDataSource.getAllUsers()
            .find { it.id == userId } ?: throw UserNotFoundException()
        remoteDataSource.deleteUser(userDto)
        return true
    }

    override suspend fun createUser(id : UUID, name : String, password : String, userType : UserType) : Boolean {
        val hashedPassword = passwordEncryptor.hashPassword(password)
        return remoteDataSource.createUser(id, name, hashedPassword, userType)
    }

    override suspend fun editUser(user: User){
        return remoteDataSource.editUser(user.toUserDto())
    }

    override suspend fun loginUser(name : String, password : String) : User? {
        val users = getAllUsers()
        val hashedPassword = passwordEncryptor.hashPassword(password)
        return users.find { it.name == name && it.password == hashedPassword }
    }
}
