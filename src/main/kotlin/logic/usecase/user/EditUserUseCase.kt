package logic.usecase.user

import logic.exception.EmptyNameException
import logic.exception.EmptyPasswordException
import logic.exception.EntityNotChangedException
import logic.model.User
import logic.repositories.AuthenticationRepository

class EditUserUseCase(
    private val authenticationRepository: AuthenticationRepository
) {
    suspend fun editUser(newUser: User) {
        authenticationRepository.editUser(user = newUser)
    }
}