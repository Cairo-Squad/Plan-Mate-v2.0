package logic.repositories

import data.dto.UserDto
import data.dto.UserType
import java.util.*

interface AuthenticationRepository {
	fun createUser(id: UUID, name: String, password:String, userType: UserType): UserDto
}