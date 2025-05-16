package logic.usecase.user

import data.hashing.PasswordEncryptor
import logic.model.UserType
import logic.repositories.AuthenticationRepository
import java.util.*

class SignUpUseCase(
    private val authRepository: AuthenticationRepository,
    private val passwordEncryptor: PasswordEncryptor,
) {
    suspend fun signUp(userName: String, userPassword: String, userType: UserType) {
        return authRepository.signUp(userName, passwordEncryptor.hashPassword(userPassword), userType)
    }
}