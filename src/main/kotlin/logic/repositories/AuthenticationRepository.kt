package logic.repositories

import data.dto.UserDto
import data.dto.UserType

interface AuthenticationRepository {
    fun getAllUser() : List<UserDto>
    fun loginUser(name: String, password: String):Boolean

}