package data.repositories

import data.dataSource.DataSource
import data.dto.UserDto
import data.dto.UserType
import data.hashing.Hashing
import logic.repositories.AuthenticationRepository
import java.util.UUID

class AuthenticationRepositoryImpl(
	private val dataSource: DataSource,
	private val passwordConverter: Hashing
) : AuthenticationRepository {
  
    override fun getAllUser() : List<UserDto> {
        TODO("Not yet implemented")
    }

    override fun loginUser(name : String, password : String) : Boolean {
        TODO("Not yet implemented")
    }
   
	override fun createUser(id: UUID, name: String, password: String, userType: UserType): UserDto {
		val hashedPassword = passwordConverter.hash(password)
		return dataSource.createUser(id, name, hashedPassword, userType)
	}
}