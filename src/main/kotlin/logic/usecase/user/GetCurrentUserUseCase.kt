package logic.usecase.user

import logic.model.User
import logic.repositories.AuthenticationRepository

class GetCurrentUserUseCase(
    private val authenticationRepository : AuthenticationRepository,
) {
    suspend fun getCurrentUser() : User? {
        return authenticationRepository.getCurrentUser()
    }
}