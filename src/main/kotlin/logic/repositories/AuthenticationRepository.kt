package logic.repositories

import data.dto.UserDto
import data.dto.UserType
import logic.model.User
import java.util.*

interface AuthenticationRepository {
    suspend fun createUser(id: UUID, name: String, password: String, userType: UserType): UserDto
    suspend fun getAllUsers(): List<User>
    suspend fun deleteUser(userId: UUID): Boolean
    suspend fun editUser(user: User)
}