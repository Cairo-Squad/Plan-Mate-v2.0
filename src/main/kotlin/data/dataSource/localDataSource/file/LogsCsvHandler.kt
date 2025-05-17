package data.dataSource.localDataSource.file

import data.customException.PlanMateException
import data.dataSource.localDataSource.file.handler.CsvFileHandler
import data.dataSource.util.CsvIndices
import data.dataSource.util.UserActionConstants
import logic.model.EntityType
import data.dto.LogDto
import logic.model.ActionType
import java.time.LocalDateTime
import java.util.*

class LogsCsvHandler(
    filePath: String,
    headers: List<String>
) : CsvFileHandler<LogDto>(
    filePath = filePath,
    columnNames = headers,
    getDtoId = { it.id ?: UUID.randomUUID() }
) {

    override fun fromDtoToCsvRow(entity: LogDto): String {
        return listOf(
            entity.id,
            entity.entityId,
            entity.entityTitle,
            entity.entityType?.name,
            entity.dateTime,
            entity.userId,
            entity.userAction?.parseToString()
        ).joinToString(",")
    }

    override fun fromCsvRowToDto(row: String): LogDto {
        val logData = row.split(",")
        return LogDto(
            id = UUID.fromString(logData[CsvIndices.LOG_ID]),
            entityId = UUID.fromString(logData[CsvIndices.LOG_ENTITY_ID]),
            entityTitle = logData[CsvIndices.LOG_ENTITY_TITLE],
            entityType = EntityType.valueOf(logData[CsvIndices.LOG_ENTITY_TYPE]),
            dateTime = LocalDateTime.parse(logData[CsvIndices.LOG_DATE_TIME]),
            userId = UUID.fromString(logData[CsvIndices.LOG_USER_ID]),
            userAction = logData[CsvIndices.LOG_USER_ACTION].parseUserAction()
        )
    }

    private fun ActionType.parseToString(): String {
        return when (this) {
            ActionType.CREATE_PROJECT -> UserActionConstants.CREATE_PROJECT
            ActionType.EDIT_PROJECT -> UserActionConstants.EDIT_PROJECT
            ActionType.DELETE_PROJECT -> UserActionConstants.DELETE_PROJECT
            ActionType.CREATE_TASK -> UserActionConstants.CREATE_TASK
            ActionType.EDIT_TASK -> UserActionConstants.EDIT_TASK
            ActionType.DELETE_TASK -> UserActionConstants.DELETE_TASK
        }
    }


    private fun String.parseUserAction(): ActionType {
        return when (this) {
            UserActionConstants.CREATE_PROJECT -> ActionType.CREATE_PROJECT
            UserActionConstants.EDIT_PROJECT -> ActionType.EDIT_PROJECT
            UserActionConstants.DELETE_PROJECT -> ActionType.DELETE_PROJECT

            UserActionConstants.CREATE_TASK -> ActionType.CREATE_TASK
            UserActionConstants.EDIT_TASK -> ActionType.EDIT_TASK
            UserActionConstants.DELETE_TASK -> ActionType.DELETE_TASK

            else -> throw PlanMateException.NetworkException.ParsingException("Unknown action type: $this")
        }
    }
}