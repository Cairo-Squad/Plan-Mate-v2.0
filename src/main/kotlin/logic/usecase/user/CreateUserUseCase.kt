package logic.usecase.user

import logic.model.User
import logic.repositories.AuthenticationRepository

class CreateUserUseCase(
    private val authRepository : AuthenticationRepository
) {
    suspend fun createUser(user : User) : Boolean {
        return authRepository.createUser(user)
    }
}