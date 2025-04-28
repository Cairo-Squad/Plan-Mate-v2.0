package data.repositories

import data.dataSource.DataSource
import data.dto.UserType
import logic.repositories.AuthenticationRepository

class AuthenticationRepositoryImpl(
	private val dataSource: DataSource
) : AuthenticationRepository {
	override fun createUser(name: String, password: String, userType: UserType) {
		dataSource.createUser(name, password, userType)
	}
}