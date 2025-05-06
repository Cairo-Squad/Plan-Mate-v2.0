package logic.repositories

import data.dto.UserDto
import data.dto.UserType
import logic.model.User
import java.util.*

interface AuthenticationRepository {
    fun createUser(id: UUID, name: String, password: String, userType: UserType)
    fun getAllUsers(): List<User>
    fun deleteUser(userId: UUID)
    fun editUser(user: User)
}