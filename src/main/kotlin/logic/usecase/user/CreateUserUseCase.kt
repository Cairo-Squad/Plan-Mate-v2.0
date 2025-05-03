package logic.usecase.user

import data.dto.UserType
import data.repositories.mappers.toUser
import logic.exception.EmptyNameException
import logic.exception.EmptyPasswordException
import logic.model.User
import logic.repositories.AuthenticationRepository
import java.util.UUID

class CreateUserUseCase(
    private val authRepository: AuthenticationRepository
) {
    fun createUser(name: String, password: String, type: UserType): Result<User> {
        if (name.isEmpty()) {
            return Result.failure(EmptyNameException())
        }

        if (password.isEmpty()) {
            return Result.failure(EmptyPasswordException())
        }

        try {
            val userDto = authRepository.createUser(UUID.randomUUID(), name, password, type)
            val user = userDto.toUser()
            return Result.success(user)
        } catch (e: Exception) {
            return Result.failure(e)
        }
    }
}