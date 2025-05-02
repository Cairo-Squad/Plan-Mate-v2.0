package logic.usecase

import logic.exception.EmptyNameException
import logic.exception.EmptyPasswordException
import logic.exception.EntityNotChangedException
import logic.model.User
import logic.repositories.AuthenticationRepository
import java.lang.IllegalArgumentException

class EditUserUseCase(
    private val repository: AuthenticationRepository
) {
    fun editUser(newUser: User, oldUser: User) {
        validateUserInputs(
            newUser = newUser,
            oldUser = oldUser
        )
        repository.editUser(user = newUser)
    }

    private fun validateUserInputs(newUser: User, oldUser: User) {
        if (newUser == oldUser)
            throw EntityNotChangedException()
        if (newUser.name.isBlank()) throw EmptyNameException()
        if (newUser.password.isBlank()) throw EmptyPasswordException()
    }

}