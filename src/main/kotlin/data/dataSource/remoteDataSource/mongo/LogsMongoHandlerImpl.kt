package data.dataSource.remoteDataSource.mongo

import com.mongodb.client.MongoDatabase
import data.customException.PlanMateException
import data.dataSource.remoteDataSource.mongo.handler.MongoDBHandlerImpl
import data.dataSource.util.MongoConstants
import data.dataSource.util.UserActionConstants
import logic.model.EntityType
import data.dto.LogDto
import logic.model.ActionType
import org.bson.Document
import java.time.LocalDateTime
import java.util.*

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

    private fun serializeUserAction(userAction: ActionType): String {
        return when (userAction) {
            ActionType.CREATE_PROJECT -> UserActionConstants.CREATE_PROJECT
            ActionType.EDIT_PROJECT -> UserActionConstants.EDIT_PROJECT
            ActionType.DELETE_PROJECT -> UserActionConstants.DELETE_PROJECT
            ActionType.CREATE_TASK -> UserActionConstants.CREATE_TASK
            ActionType.EDIT_TASK -> UserActionConstants.EDIT_TASK
            ActionType.DELETE_TASK -> UserActionConstants.DELETE_TASK
        }
    }

    private fun deserializeUserAction(userAction: String): ActionType {
        return when (userAction) {
            UserActionConstants.CREATE_PROJECT -> ActionType.CREATE_PROJECT
            UserActionConstants.EDIT_PROJECT -> ActionType.EDIT_PROJECT
            UserActionConstants.DELETE_PROJECT -> ActionType.DELETE_PROJECT

            UserActionConstants.CREATE_TASK -> ActionType.CREATE_TASK
            UserActionConstants.EDIT_TASK -> ActionType.EDIT_TASK
            UserActionConstants.DELETE_TASK -> ActionType.DELETE_TASK

            else -> throw PlanMateException.NetworkException.ParsingException("Unknown action type: $userAction")
        }
    }

}