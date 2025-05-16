package logic.usecase.user

import logic.repositories.AuthenticationRepository
import java.util.*

class DeleteUserUseCase(
    private val authenticationRepository: AuthenticationRepository
) {
    suspend fun deleteUser(userId: UUID) {
        authenticationRepository.deleteUser(userId)
    }
}