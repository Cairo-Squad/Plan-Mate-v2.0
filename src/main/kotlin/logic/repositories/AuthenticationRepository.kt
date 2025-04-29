package logic.repositories

import data.dto.User
import data.dto.UserType

interface AuthenticationRepository {
	fun createUser(name: String, password:String, userType: UserType)
  fun getAllUser() : List<User>
  fun loginUser(name: String, password: String):Booleane

}