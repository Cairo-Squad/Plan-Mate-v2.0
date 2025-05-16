package logic.repositories

import logic.model.User
import logic.model.UserType
import java.util.*

interface AuthenticationRepository {
    suspend fun signUp(userName:String, userPassword:String, userType: UserType)
    suspend fun getAllUsers(): List<User>
    suspend fun deleteUser(userId: UUID)
    suspend fun editUser(user: User)
    suspend fun loginUser(name: String, password : String)
    suspend fun getCurrentUser():User?
}