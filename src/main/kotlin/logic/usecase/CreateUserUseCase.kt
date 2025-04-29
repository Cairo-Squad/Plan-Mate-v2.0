package logic.usecase

import data.dto.UserType
import logic.model.User
import logic.repositories.AuthenticationRepository
import java.util.UUID

class CreateUserUseCase(
	val authRepo: AuthenticationRepository
) {
	fun createUser(id: UUID, name:String, password:String, type:UserType):Result<User> {
		return Result.failure(IllegalArgumentException())
	}
}