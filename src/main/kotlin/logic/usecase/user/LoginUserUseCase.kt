package logic.usecase.user

import logic.model.User
import logic.repositories.AuthenticationRepository

class LoginUserUseCase(
    private val authenticationRepository : AuthenticationRepository,
) {
    suspend fun login(name : String, password : String) : Boolean{
        return  authenticationRepository.loginUser(
            name = name,
            password = password
        )
    }
}