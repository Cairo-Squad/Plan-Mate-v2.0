package data.dataSource

import data.database.UsersCsvParser
import data.dto.*
import java.util.*

class DataSourceImpl(
	private val usersCsvParser: UsersCsvParser
) : DataSource {
	override fun createUser(name: String, password: String, userType: UserType) {
		val userDto = UserDto(
			id = UUID.randomUUID(),
			name = name,
			password = password,
			type = userType
		)
		
		usersCsvParser.write(userDto)
	}
	
	override fun getAllUsers(): List<UserDto> {
		TODO("Not yet implemented")
	}
	
	override fun getAllProjects(): List<ProjectDto> {
		TODO("Not yet implemented")
	}
	
	override fun getAllTasks(): List<TaskDto> {
		TODO("Not yet implemented")
	}
	
	override fun getAllAuditRecords(): List<LogEntityDto> {
		TODO("Not yet implemented")
	}
}