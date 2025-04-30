package logic.repositories

import data.dto.UserType
import logic.model.User

interface AuthenticationRepository {
    fun createUser(name : String, password : String, userType : UserType)
    fun getAllUser() : List<User>

}