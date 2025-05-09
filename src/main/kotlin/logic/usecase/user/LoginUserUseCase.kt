package logic.usecase.user

import logic.model.User
import logic.repositories.AuthenticationRepository

class LoginUserUseCase(
    private val authenticationRepository : AuthenticationRepository,
) {
    fun login(name : String, password : String) : User? {
        return  authenticationRepository.loginUser(
            name = name,
            password = password
        ) ?: throw Exception("Invalid username or password.")
    }
}