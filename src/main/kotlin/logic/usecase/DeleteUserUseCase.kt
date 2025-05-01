package logic.usecase

import logic.repositories.AuthenticationRepository
import java.util.*

class DeleteUserUseCase(private val authenticationRepository : AuthenticationRepository) {
    fun deleteUser(userId : UUID) : Boolean {
        try {
            val isDeleted = authenticationRepository.deleteUser(userId)
            return isDeleted
        } catch (e : Exception) {
            throw Exception("error  during delete")
        }
    }
}