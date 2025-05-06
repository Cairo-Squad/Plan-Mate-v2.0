package data.database.mongo

import com.mongodb.client.MongoDatabase
import data.database.util.MongoConstants
import data.dto.EntityType
import data.dto.LogDto
import data.dto.UserAction
import org.bson.Document
import java.time.LocalDateTime
import java.util.*

class LogsMongoHandlerImpl(
	database: MongoDatabase
) : MongoDBHandlerImpl<LogDto>(
	database = database,
	collectionName = "logs",
	getDtoId = { it.id }
) {
	override fun convertDtoToDocument(entity: LogDto): Document {
		return Document()
			.append(MongoConstants.ID, entity.id.toString())
			.append(MongoConstants.LOG_ENTITY_ID, entity.entityId.toString())
			.append(MongoConstants.LOG_ENTITY_TITLE, entity.entityTitle)
			.append(MongoConstants.LOG_ENTITY_TYPE, entity.entityType.name)
			.append(MongoConstants.LOG_DATE_TIME, entity.dateTime.toString())
			.append(MongoConstants.LOG_USER_ID, entity.userId.toString())
			.append(MongoConstants.LOG_USER_ACTION, serializeUserAction(entity.userAction))
	}
	
	override fun convertDocumentToDto(document: Document): LogDto {
		return LogDto(
			id = UUID.fromString(document.getString(MongoConstants.ID)),
			entityId = UUID.fromString(document.getString(MongoConstants.LOG_ENTITY_ID)),
			entityTitle = document.getString(MongoConstants.LOG_ENTITY_TITLE),
			entityType = EntityType.valueOf(document.getString(MongoConstants.LOG_ENTITY_TYPE)),
			dateTime = LocalDateTime.parse(document.getString(MongoConstants.LOG_DATE_TIME)),
			userId = UUID.fromString(document.getString(MongoConstants.LOG_USER_ID)),
			userAction = deserializeUserAction(document.getString(MongoConstants.LOG_USER_ACTION))
		)
	}
	
	private fun serializeUserAction(userAction: UserAction): String {
		return when (userAction) {
			is UserAction.DeleteProject -> "DELETE_ACTION"
			is UserAction.EditProjectTitle -> "EDIT_PROJECT_TITLE||${userAction.oldName}"
		}
	}
	
	private fun deserializeUserAction(actionString: String): UserAction {
		val actionParts = actionString.split("||")
		return when (actionParts[0]) {
			"DELETE_ACTION" -> UserAction.DeleteProject
			"EDIT_PROJECT_TITLE" -> UserAction.EditProjectTitle(actionParts[1])
			else -> throw Exception("Unknown user action: ${actionParts[0]}")
		}
	}
}