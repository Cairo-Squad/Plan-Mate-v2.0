package logic.usecase.user

import logic.model.User
import logic.repositories.AuthenticationRepository

class GetAllUsersUseCase(
    private val authenticationRepository: AuthenticationRepository
) {
    suspend fun getAllUsers(): List<User> {
        return authenticationRepository.getAllUsers()
    }
}