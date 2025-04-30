package logic.usecase

import logic.repositories.AuthenticationRepository
import java.util.*

class DeleteUserUseCase(private val authenticationRepository : AuthenticationRepository) {
    fun deleteUser(userId : UUID) : Boolean {
        return false
    }
}