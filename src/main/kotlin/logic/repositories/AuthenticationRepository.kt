package logic.repositories

import data.dto.UserDto
import data.dto.UserType
import logic.model.User
import java.util.*

interface AuthenticationRepository {
    suspend fun createUser(user: User): Boolean
    suspend fun getAllUsers(): List<User>
    suspend fun deleteUser(userId: UUID): Boolean
    suspend fun editUser(user: User):Boolean
    suspend fun loginUser(name: String, password : String) :Boolean
    suspend fun getCurrentUser():User?
}