package data.dataSource.remoteDataSource.mongo

import com.mongodb.client.MongoDatabase
import data.dataSource.remoteDataSource.mongo.handler.MongoDBHandlerImpl
import data.dataSource.util.MongoConstants
import data.dataSource.util.UserActionConstants
import logic.model.EntityType
import data.dto.LogDto
import logic.model.UserAction
import org.bson.Document
import java.io.IOException
import java.time.LocalDateTime
import java.util.*
import kotlin.text.split
import kotlin.text.startsWith

class LogsMongoHandlerImpl(
    database: MongoDatabase,
    collectionName: String
) : MongoDBHandlerImpl<LogDto>(
    database = database,
    collectionName = collectionName,
    getDtoId = { it.id ?: UUID.randomUUID() }
) {
    override fun convertDtoToDocument(entity: LogDto): Document {
        return Document()
            .append(MongoConstants.ID, getDtoId(entity))
            .append(MongoConstants.LOG_ENTITY_ID, entity.entityId)
            .append(MongoConstants.LOG_ENTITY_TITLE, entity.entityTitle)
            .append(MongoConstants.LOG_ENTITY_TYPE, entity.entityType?.name)
            .append(MongoConstants.LOG_DATE_TIME, entity.dateTime.toString())
            .append(MongoConstants.LOG_USER_ID, entity.userId)
            .append(
                MongoConstants.LOG_USER_ACTION,
                serializeUserAction(entity.userAction!!)
            )
    }

    override fun convertDocumentToDto(document: Document): LogDto {
        return LogDto(
            id = document.get(MongoConstants.ID, UUID::class.java),
            entityId = document.get(MongoConstants.LOG_ENTITY_ID, UUID::class.java),
            entityTitle = document.getString(MongoConstants.LOG_ENTITY_TITLE),
            entityType = EntityType.valueOf(document.getString(MongoConstants.LOG_ENTITY_TYPE)),
            dateTime = LocalDateTime.parse(document.getString(MongoConstants.LOG_DATE_TIME)),
            userId = document.get(MongoConstants.LOG_USER_ID, UUID::class.java),
            userAction = deserializeUserAction(document.getString(MongoConstants.LOG_USER_ACTION))
        )
    }

    private fun serializeUserAction(userAction: UserAction): String {
        return when (userAction) {
            is UserAction.EditProject -> "${UserActionConstants.EDIT_PROJECT}||${userAction.projectId}||${userAction.changes}"
            is UserAction.EditTask -> "${UserActionConstants.EDIT_TASK}||${userAction.taskId}||${userAction.changes}"
            else -> "Unknown action type: $this"
        }
    }

    private fun deserializeUserAction(actionString: String): UserAction {
        val actionParts = actionString.split("||")

        return when {

            actionString.startsWith(UserActionConstants.EDIT_PROJECT) -> {
                if (actionParts.size < 3) {
                    throw IOException()
                }
                UserAction.EditProject(UUID.fromString(actionParts[1]), actionParts[2])
            }

            actionString.startsWith(UserActionConstants.EDIT_TASK) -> {
                if (actionParts.size < 3) {
                    throw IOException()
                }
                UserAction.EditTask(UUID.fromString(actionParts[1]), actionParts[2])
            }

            else -> throw IOException()
        }
    }

}