package logic.usecase.user

import logic.model.User
import logic.repositories.AuthenticationRepository

class GetAllUsersUseCase(private val authenticationRepository: AuthenticationRepository) {
    fun execute(): Result<List<User>> {
        return try {
            val users = authenticationRepository.getAllUsers()
            Result.success(users)
        } catch (exception: Exception) {
            Result.failure(exception)
        }
    }
}