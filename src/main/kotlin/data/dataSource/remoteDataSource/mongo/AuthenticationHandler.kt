package data.dataSource.remoteDataSource.mongo

import data.dto.UserDto
import logic.model.UserType
import java.util.UUID

interface AuthenticationHandler {
    suspend fun login(userName: String, userPassword: String): UUID?
    suspend fun signUp(userName: String, userPassword: String, userType: UserType): UUID
}