package logic.usecase.user

import data.dto.UserType
import logic.repositories.AuthenticationRepository
import java.util.*

class CreateUserUseCase(
    private val authRepository : AuthenticationRepository
) {
    suspend fun createUser(id : UUID, name : String, password : String, type : UserType) : Boolean {
        return authRepository.createUser(id, name, password, type)
    }
}