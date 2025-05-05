package logic.usecase.user

import logic.repositories.AuthenticationRepository
import java.util.*

class DeleteUserUseCase(
    private val authenticationRepository: AuthenticationRepository
) {
    fun deleteUser(userId: UUID): Boolean {
        return authenticationRepository.deleteUser(userId)
    }
}