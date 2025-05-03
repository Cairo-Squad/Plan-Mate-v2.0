package logic.usecase.user

import data.hashing.PasswordEncryptor
import logic.model.User
import logic.repositories.AuthenticationRepository

class LoginUserUseCase(
	private val authenticationRepository: AuthenticationRepository,
	private val passwordEncryptor: PasswordEncryptor
) {
	
	fun login(name: String, password: String): User {
		try {
			val users = authenticationRepository.getAllUsers()
			val hashedPassword = passwordEncryptor.hashPassword(password)
			val user = users.find { it.name == name && it.password == hashedPassword }
				?: throw Exception("Invalid username or password")
			
			return user
		} catch (e: Exception) {
			throw Exception("Error during login: ${e.message}")
		}
	}
}
