package logic.repositories

import data.dto.UserType
import logic.model.User
import java.util.*

interface AuthenticationRepository {
    fun createUser(name : String, password : String, userType : UserType)
    fun getAllUsers() : List<User>
    fun loginUser(name : String, password : String) : Boolean
    fun deleteUser(userId: UUID): Boolean
}