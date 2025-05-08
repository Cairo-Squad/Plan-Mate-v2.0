package logic.usecase.user

import data.dto.UserType
import data.repositories.mappers.toUser
import logic.model.User
import logic.repositories.AuthenticationRepository
import java.util.*

class CreateUserUseCase(
    private val authRepository: AuthenticationRepository
) {
    fun createUser(id: UUID, name: String, password: String, type: UserType): User {
        return authRepository.createUser(id, name, password, type).toUser()
    }
}