package logic.usecase

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
            throw IllegalStateException("user is not changed")
        if (newUser.name.isBlank()) throw IllegalArgumentException("Can't put the name empty")
        if (newUser.password.isBlank()) throw IllegalArgumentException("Can't put the password empty")
    }

}