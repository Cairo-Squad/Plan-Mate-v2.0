package logic.usecase

import data.dto.UserType
import logic.exception.NameNotEmptyException
import logic.exception.PasswordNotEmptyException
import logic.model.User
import logic.repositories.AuthenticationRepository

class CreateUserUseCase(
	private val repository: AuthenticationRepository
) {
	fun createUser(name: String, password: String, userType: UserType): Result<User> {
		if (name.isEmpty()) {
			return Result.failure(NameNotEmptyException("Name cannot be empty"))
		}
		
		if (password.isEmpty()) {
			return Result.failure(PasswordNotEmptyException("Password cannot be empty"))
		}
		
		repository.createUser(name, password, userType)
		
		val user = User(name = name, password = password, type = userType)
		return Result.success(user)
	}
}