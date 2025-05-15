package logic.usecase.user

import data.hashing.PasswordEncryptor
import logic.model.User
import logic.repositories.AuthenticationRepository

class LoginUserUseCase(
    private val authenticationRepository: AuthenticationRepository,
    private val passwordEncryptor: PasswordEncryptor
) {
    suspend fun login(name: String, password: String) {
        return authenticationRepository.loginUser(
            name = name,
            password = passwordEncryptor.hashPassword(password)
        )
    }
}