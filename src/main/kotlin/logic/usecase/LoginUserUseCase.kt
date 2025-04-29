package logic.usecase

import data.dto.UserType
import logic.repositories.AuthenticationRepository

class LoginUserUseCase(private val authenticationRepository : AuthenticationRepository) {
    val users = authenticationRepository.getUser()

    fun login(name : String, password : String) : Boolean {
        return false
    }
}