package data.database

import data.database.util.CsvIndices
import data.database.util.UserActionConstants
import data.dto.EntityType
import data.dto.LogDto
import data.dto.UserAction
import java.time.LocalDateTime
import java.util.*

class LogsCsvHandler(
    filePath: String,
    headers: List<String>
) : CsvFileHandler<LogDto>(
    filePath = filePath,
    headers = headers,
    getDtoId = { it.id }
) {

    override fun fromDtoToCsvRow(entity: LogDto): String {
        val rowStringBuilder = StringBuilder()
        rowStringBuilder.append("${entity.id}")
        rowStringBuilder.append(",${entity.entityId}")
        rowStringBuilder.append(",${entity.entityTitle}")
        rowStringBuilder.append(",${entity.entityType.name}")
        rowStringBuilder.append(",${entity.dateTime}")
        rowStringBuilder.append(",${entity.userId}")
        rowStringBuilder.append(",${entity.userAction.parseToString()}")
        return rowStringBuilder.toString()
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

    private fun UserAction.parseToString(): String {
        return when (this) {
            is UserAction.DeleteProject -> UserActionConstants.DELETE_ACTION
            is UserAction.EditProjectTitle -> "${UserActionConstants.EDIT_PROJECT_STATE}||$oldName"
        }
    }

    private fun String.parseUserAction(): UserAction {
        val actionParts = this.split("||")
        return when (actionParts[0]) {
            UserActionConstants.DELETE_ACTION -> UserAction.DeleteProject
            UserActionConstants.EDIT_PROJECT_STATE -> UserAction.EditProjectTitle(actionParts[1])
            else -> throw Exception("")
        }
    }
}