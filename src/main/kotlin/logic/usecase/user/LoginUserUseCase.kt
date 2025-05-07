package logic.usecase.user

import data.hashing.PasswordEncryptor
import logic.model.User
import logic.repositories.AuthenticationRepository

class LoginUserUseCase(
    private val authenticationRepository: AuthenticationRepository,
    private val passwordEncryptor: PasswordEncryptor
) {
    suspend fun login(name: String, password: String): User {
        val users = authenticationRepository.getAllUsers()
        val hashedPassword = passwordEncryptor.hashPassword(password)
        return users.find { it.name == name && it.password == hashedPassword }
            ?: throw Exception("Invalid username or password")
    }
}
