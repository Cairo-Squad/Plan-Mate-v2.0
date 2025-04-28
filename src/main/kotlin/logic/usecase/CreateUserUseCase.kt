package logic.usecase

import logic.model.User
import logic.repositories.AuthenticationRepository

class CreateUserUseCase(
	private val repository: AuthenticationRepository
) {
	fun createUser(user:User): Result<User> {
		return Result.failure(IllegalArgumentException())
	}
}