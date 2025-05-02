package logic.usecase.user

import data.dto.UserType
import data.repositories.mappers.toUser
import logic.exception.EmptyNameException
import logic.exception.EmptyPasswordException
import logic.model.User
import logic.repositories.AuthenticationRepository
import java.util.UUID

class CreateUserUseCase(
    private val authRepo: AuthenticationRepository
) {
    fun createUser(id: UUID, name: String, password: String, type: UserType): Result<User> {
        if (name.isEmpty()) {
            return Result.failure(EmptyNameException())
        } else if (password.isEmpty()) {
            return Result.failure(EmptyPasswordException())
        } else {
            try {
                val userDto = authRepo.createUser(id, name, password, type)
                val user = userDto.toUser()
                return Result.success(user)
            } catch (e: Exception) {
                return Result.failure(e)
            }
        }
    }
}