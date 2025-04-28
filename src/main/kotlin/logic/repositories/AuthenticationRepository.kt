package logic.repositories

import data.dto.UserType

interface AuthenticationRepository {
	fun createUser(name: String, password:String, userType: UserType)
}