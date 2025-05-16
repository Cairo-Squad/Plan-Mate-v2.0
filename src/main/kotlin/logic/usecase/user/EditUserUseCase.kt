package logic.usecase.user

import logic.model.User
import logic.repositories.AuthenticationRepository

class EditUserUseCase(
    private val authenticationRepository: AuthenticationRepository
) {
    suspend fun editUser(newUser: User) {
       authenticationRepository.editUser(user = newUser)
    }
}