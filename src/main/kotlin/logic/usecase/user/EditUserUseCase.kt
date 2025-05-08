package logic.usecase.user

import logic.model.User
import logic.repositories.AuthenticationRepository

class EditUserUseCase(
    private val repository: AuthenticationRepository
) {
    fun editUser(newUser: User, oldUser: User) {
        repository.editUser(user = newUser)
    }
}