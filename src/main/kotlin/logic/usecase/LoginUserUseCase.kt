package logic.usecase

import data.dto.UserType
import logic.repositories.AuthenticationRepository

class LoginUserUseCase(private val authenticationRepository : AuthenticationRepository) {

    fun login(name : String, password : String) : Boolean {
        try {
            val users = authenticationRepository.getAllUsers()
            return users.any { it.name == name && it.password == password }
        } catch (e: Exception) {
            throw Exception("error  during login")
        }
    }
}