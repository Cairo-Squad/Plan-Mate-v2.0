package data.database.mongo

import com.mongodb.client.MongoDatabase
import data.database.util.MongoConstants
import data.database.util.UserActionConstants
import data.dto.EntityType
import data.dto.LogDto
import data.dto.UserAction
import logic.exception.CsvParseException
import org.bson.Document
import java.time.LocalDateTime
import java.util.*
import kotlin.text.split
import kotlin.text.startsWith

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
			is UserAction.DeleteProject -> UserActionConstants.DELETE_PROJECT
			is UserAction.EditProjectTitle -> "${UserActionConstants.EDIT_PROJECT_TITLE}||${userAction.oldName}||${userAction.newName}"
			is UserAction.CreateProject -> "${UserActionConstants.CREATE_PROJECT}||${userAction.name}"
			is UserAction.CreateTask -> "${UserActionConstants.CREATE_TASK}||${userAction.taskName}||${userAction.taskId}||${userAction.projectId}"
			is UserAction.EditProject -> "${UserActionConstants.EDIT_PROJECT}||${userAction.projectId}||${userAction.changes}"
			is UserAction.EditTask -> "${UserActionConstants.EDIT_TASK}||${userAction.taskId}||${userAction.changes}"
			else -> "Unknown action type: $this"
		}
	}
	
	private fun deserializeUserAction(actionString: String): UserAction {
		val actionParts = actionString.split("||")

		return when {
			actionString.startsWith(UserActionConstants.DELETE_PROJECT) -> {
				if (actionParts.size < 2) {
					throw CsvParseException()
				}
				try {
					UserAction.DeleteProject(UUID.fromString(actionParts[1]))
				} catch (e: Exception) {
					throw CsvParseException()
				}
			}

			actionString.startsWith(UserActionConstants.EDIT_PROJECT_TITLE) -> {
				if (actionParts.size < 3) {
					throw CsvParseException()
				}
				UserAction.EditProjectTitle(actionParts[1], actionParts[2])
			}

			actionString.startsWith(UserActionConstants.CREATE_PROJECT) -> {
				if (actionParts.size < 2) {
					throw CsvParseException()
				}

				if (actionParts.size >= 3) {
					try {
						UserAction.CreateProject(actionParts[1], UUID.fromString(actionParts[2]))
					} catch (e: Exception) {
						throw CsvParseException()
					}
				} else {
					UserAction.CreateProject(actionParts[1], UUID.randomUUID()) // Using random UUID as fallback
				}
			}

			actionString.startsWith(UserActionConstants.CREATE_TASK) -> {
				if (actionParts.size < 4) {
					throw CsvParseException()
				}
				try {
					UserAction.CreateTask(
						actionParts[1],
						UUID.fromString(actionParts[2]),
						UUID.fromString(actionParts[3])
					)
				} catch (e: Exception) {
					throw CsvParseException()
				}
			}

			actionString.startsWith(UserActionConstants.EDIT_PROJECT) -> {
				if (actionParts.size < 3) {
					throw CsvParseException()
				}
				try {
					UserAction.EditProject(UUID.fromString(actionParts[1]), actionParts[2])
				} catch (e: Exception) {
					throw CsvParseException()
				}
			}

			actionString.startsWith(UserActionConstants.EDIT_TASK) -> {
				if (actionParts.size < 3) {
					throw CsvParseException()
				}
				try {
					UserAction.EditTask(UUID.fromString(actionParts[1]), actionParts[2])
				} catch (e: Exception) {
					throw CsvParseException()
				}
			}

			else -> throw CsvParseException()
		}
	}
}