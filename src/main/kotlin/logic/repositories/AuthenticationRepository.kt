package logic.repositories

import data.dto.UserDto
import data.dto.UserType
import logic.model.User
import java.util.*

interface AuthenticationRepository {
    fun createUser(id: UUID, name : String, password : String, userType : UserType):UserDto
    fun getAllUsers() : List<User>
    fun loginUser(name : String, password : String) : Boolean
    fun deleteUser(userId: UUID): Boolean

}
