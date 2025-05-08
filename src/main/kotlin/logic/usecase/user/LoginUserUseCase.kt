package logic.usecase.user

import data.hashing.PasswordEncryptor
import logic.model.User
import logic.repositories.AuthenticationRepository
import logic.util.InvalidUserCredentialsException

class LoginUserUseCase(
    private val authenticationRepository: AuthenticationRepository,
    private val passwordEncryptor: PasswordEncryptor
) {
    fun login(name: String, password: String): User {
        val users = authenticationRepository.getAllUsers()
        val hashedPassword = passwordEncryptor.hashPassword(password)
        return users.find { it.name == name && it.password == hashedPassword }
            ?: throw InvalidUserCredentialsException()
    }
}
