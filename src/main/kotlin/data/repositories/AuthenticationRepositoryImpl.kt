package data.repositories

import data.dataSource.DataSource
import data.dto.UserDto
import data.dto.UserType
import logic.model.User
import logic.repositories.AuthenticationRepository
import java.util.UUID

class AuthenticationRepositoryImpl(
	private val dataSource: DataSource
) : AuthenticationRepository {
  
    override fun getAllUser() : List<User> {
        TODO("Not yet implemented")
    }

    override fun loginUser(name : String, password : String) : Boolean {
        TODO("Not yet implemented")
    }
   
	override fun createUser(id: UUID, name: String, password: String, userType: UserType): UserDto {
		return dataSource.createUser(id, name, password, userType)
	}
}