package logic.usecase.user

import data.dto.UserType
import logic.model.User
import logic.repositories.AuthenticationRepository
import java.util.*

class CreateUserUseCase(
    private val authRepository : AuthenticationRepository
) {
    suspend fun createUser(user : User) : Boolean {
        return authRepository.createUser(user)
    }
}