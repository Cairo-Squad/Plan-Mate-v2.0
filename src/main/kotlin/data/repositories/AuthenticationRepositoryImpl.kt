package data.repositories

import data.dataSource.remoteDataSource.RemoteDataSource
import data.hashing.PasswordEncryptor
import data.repositories.mappers.toUser
import data.repositories.mappers.toUserDto
import logic.exception.*
import logic.model.User
import logic.repositories.AuthenticationRepository
import java.util.*

class AuthenticationRepositoryImpl(
    private val remoteDataSource: RemoteDataSource,
    private val passwordEncryptor: PasswordEncryptor
) : AuthenticationRepository, BaseRepository() {

    override suspend fun getAllUsers(): List<User> {
        return try {
            wrap {
                val usersDto = remoteDataSource.getAllUsers()
                usersDto.map { it.toUser() }
            }
        } catch (e: NotFoundException) {
            throw NotFoundException()
        } catch (e: GeneralException) {
            throw GeneralException()
        } catch (e: Exception) {
            throw UnknownException()
        }
    }

    override suspend fun deleteUser(userId: UUID): Boolean {
        return try {
            wrap {
                val userDto = remoteDataSource.getAllUsers()
                    .find { it.id == userId } ?: throw NotFoundException()
                remoteDataSource.deleteUser(userDto)
                true
            }
        } catch (e: NotFoundException) {
            throw e
        } catch (e: GeneralException) {
            throw GeneralException()
        } catch (e: Exception) {
            throw UnknownException()
        }
    }

    override suspend fun createUser(user: User): Boolean {
        return try {
            wrap {
                remoteDataSource.createUser(user.toUserDto())
            }
        } catch (e: EntityNotChangedException) {
            throw e
        } catch (e: UserException) {
            throw InvalidUserException()
        } catch (e: Exception) {
            throw UnknownException()
        }
    }

    override suspend fun editUser(user: User): Boolean {
        return try {
            wrap { remoteDataSource.editUser(user.toUserDto()) }
        } catch (e: EntityNotChangedException) {
            throw e
        } catch (e: UserException) {
            throw InvalidUserException()
        } catch (e: Exception) {
            throw UnknownException()
        }
    }

    override suspend fun loginUser(name: String, password: String): Boolean {
        return try {
            wrap {
                val hashedPassword = passwordEncryptor.hashPassword(password)
                if (!remoteDataSource.loginUser(name, hashedPassword)) {
                    throw InvalidUserCredentialsException()
                }
                true
            }
        } catch (e: InvalidUserCredentialsException) {
            throw e
        } catch (e: UserException) {
            throw InvalidUserException()
        } catch (e: Exception) {
            throw UnknownException()
        }
    }

    override suspend fun getCurrentUser(): User? {
        return try {
            wrap {
                remoteDataSource.getCurrentUser()?.toUser()
            }
        } catch (e: NotFoundException) {
            throw e
        } catch (e: GeneralException) {
            throw GeneralException()
        } catch (e: Exception) {
            throw UnknownException()
        }
    }
}
