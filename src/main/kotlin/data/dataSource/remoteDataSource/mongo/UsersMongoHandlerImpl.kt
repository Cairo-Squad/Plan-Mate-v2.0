package data.dataSource.remoteDataSource.mongo

import com.mongodb.client.MongoDatabase
import data.dataSource.util.MongoConstants
import data.dto.UserDto
import data.dto.UserType
import org.bson.Document
import java.util.*

class UsersMongoHandlerImpl(
	database: MongoDatabase
) : MongoDBHandlerImpl<UserDto>(
	database = database,
	collectionName = "users",
	getDtoId = { it.id }
) {
	override fun convertDtoToDocument(entity: UserDto): Document {
		return Document()
			.append(MongoConstants.USER_ID, entity.id.toString())
			.append(MongoConstants.USER_NAME, entity.name)
			.append(MongoConstants.USER_PASSWORD, entity.password)
			.append(MongoConstants.USER_TYPE, entity.type.name)
	}
	
	override fun convertDocumentToDto(document: Document): UserDto {
		return UserDto(
			id = UUID.fromString(document.getString(MongoConstants.USER_ID)),
			name = document.getString(MongoConstants.USER_NAME),
			password = document.getString(MongoConstants.USER_PASSWORD),
			type = UserType.valueOf(document.getString(MongoConstants.USER_TYPE))
		)
	}
}