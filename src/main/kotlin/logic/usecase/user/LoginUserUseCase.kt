package logic.usecase.user

import logic.model.User
import logic.repositories.AuthenticationRepository

class LoginUserUseCase(private val authenticationRepository: AuthenticationRepository) {

    fun login(name: String, password: String): User {
        try {
            val users = authenticationRepository.getAllUsers()
            val user = users.find { it.name == name && it.password == password }
                ?: throw Exception("Invalid username or password")

            return user
        } catch (e: Exception) {
            throw Exception("Error during login: ${e.message}")
        }
    }
}
