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

        repository.editUser(userId = user.id)
        return false
    }

    private fun ensureUserTypeChanges(oldType: UserType, newType: UserType) {
        val isForbidden =
            (oldType == UserType.MATE && newType == UserType.ADMIN) ||
                    (oldType == UserType.ADMIN && newType == UserType.MATE)

        if (isForbidden) {
            throw SecurityException("Can't change user type between MATE and ADMIN")
        }
    }
}