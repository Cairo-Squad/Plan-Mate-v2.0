package logic.usecase

import data.dto.UserType
import logic.model.User
import logic.repositories.AuthenticationRepository
import java.lang.IllegalArgumentException

class EditUserUseCase(
    private val repository: AuthenticationRepository
) {
    fun editUser(user: User): Boolean {
        if (user.name == "") throw IllegalArgumentException("Can't put the name empty")
        if (user.password == "") throw IllegalArgumentException("Can't put the password empty")

        return repository.editUser(userId = user.id)

    }

}