package logic.usecase

import data.dto.UserType
import logic.repositories.AuthenticationRepository

class LoginUserUseCase(private val authenticationRepository : AuthenticationRepository) {
    val users = authenticationRepository.getAllUser()

    fun login(name : String, password : String) : Boolean {
        val isFoundUser=users.any { it.name == name && it.password == password }
        return isFoundUser
    }
}