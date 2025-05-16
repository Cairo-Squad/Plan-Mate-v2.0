package data.dataSource.remoteDataSource.mongo

import com.mongodb.client.MongoDatabase
import data.dataSource.remoteDataSource.mongo.handler.MongoDBHandlerImpl
import data.dataSource.util.MongoConstants
import data.dto.UserDto
import logic.model.UserType
import org.bson.Document
import java.util.*

class UsersMongoHandlerImpl(
	database: MongoDatabase
) : MongoDBHandlerImpl<UserDto>(
	database = database,
	collectionName = "users",
	getDtoId = { it.id?: UUID.randomUUID() }
) {
	override fun convertDtoToDocument(entity: UserDto): Document {
		return Document()
			.append(MongoConstants.USER_ID, entity.id)
			.append(MongoConstants.USER_NAME, entity.name)
			.append(MongoConstants.USER_TYPE, entity.type.name)
	}
	
	override fun convertDocumentToDto(document: Document): UserDto {
		return UserDto(
			id = document.get(MongoConstants.USER_ID,UUID::class.java),
			name = document.getString(MongoConstants.USER_NAME),
			type = UserType.valueOf(document.getString(MongoConstants.USER_TYPE))
		)
	}
}