package logic.usecase

import logic.model.User
import logic.repositories.AuthenticationRepository

class EditUserUseCase(
    private val repository: AuthenticationRepository
) {
    fun editUser(user: User): Boolean{
        return false
    }
}