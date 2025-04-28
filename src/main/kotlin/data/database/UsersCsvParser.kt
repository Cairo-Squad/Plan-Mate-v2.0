package data.database

import data.dto.UserDto
import data.util.Constants
import java.io.File
import java.io.FileWriter

class UsersCsvParser : Parser {
	override fun <T> write(entity: T) {
		val userDto = entity as UserDto
		
		val file = File(Constants.USERS_CSV_FILE_PATH)
		val isNewFile = !file.exists()
		
		FileWriter(file, true).use { writer ->
			if (isNewFile) {
				writer.write("id,name,password,type\n")
			}
			
			
			writer.append("${userDto.id},${userDto.name},${userDto.password},${userDto.type}\n")
			writer.flush()
		}
	}
	
	override fun <T> read(): T {
		TODO("Not yet implemented")
	}
}