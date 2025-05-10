package data.dataSource.localDataSource.file

import data.dataSource.localDataSource.file.handler.CsvFileHandler
import data.dataSource.util.CsvIndices
import data.dataSource.util.UserActionConstants
import data.dto.EntityType
import data.dto.LogDto
import data.dto.UserAction
import logic.exception.CsvParseException
import java.time.LocalDateTime
import java.util.*

class LogsCsvHandler(
    filePath: String,
    headers: List<String>
) : CsvFileHandler<LogDto>(
    filePath = filePath,
    columnNames = headers,
    getDtoId = { it.id }
) {

    override fun fromDtoToCsvRow(entity: LogDto): String {
        return listOf(
            entity.id,
            entity.entityId,
            entity.entityTitle,
            entity.entityType.name,
            entity.dateTime,
            entity.userId,
            entity.userAction.parseToString()
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

    private fun UserAction.parseToString(): String {
        return when (this) {
            is UserAction.EditProject -> "${UserActionConstants.EDIT_PROJECT}||$projectId||$changes"
            is UserAction.EditTask -> "${UserActionConstants.EDIT_TASK}||$taskId||$changes"
            else -> "Unknown action type: $this"
        }
    }


    private fun String.parseUserAction(): UserAction {
        val actionParts = this.split("||")
        return when {
            this.startsWith(UserActionConstants.EDIT_PROJECT) -> {
                if (actionParts.size < 3) {
                    throw CsvParseException()
                }
                try {
                    UserAction.EditProject(UUID.fromString(actionParts[1]), actionParts[2])
                } catch (e: Exception) {
                    throw CsvParseException()
                }
            }

            this.startsWith(UserActionConstants.EDIT_TASK) -> {
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