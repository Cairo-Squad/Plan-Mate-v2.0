package logic.usecase.user

import logic.repositories.AuthenticationRepository
import java.util.*

class DeleteUserUseCase(private val authenticationRepository : AuthenticationRepository) {
    fun deleteUser(userId : UUID) : Boolean {
        try {
            return authenticationRepository.deleteUser(userId)
        } catch (e : Exception) {
            throw Exception("error  during delete")
        }
    }
}