package data.database

import data.database.util.CsvIndices
import data.database.util.UserActionConstants
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
    headers = headers,
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
            is UserAction.DeleteProject -> UserActionConstants.DELETE_PROJECT
            is UserAction.EditProjectTitle -> "${UserActionConstants.EDIT_PROJECT_TITLE}||$oldName||$newName"
            is UserAction.CreateProject -> "${UserActionConstants.CREATE_PROJECT}||$name"
            is UserAction.CreateTask -> "${UserActionConstants.CREATE_TASK}||$taskName||$taskId||$projectId"
            is UserAction.EditProject -> "${UserActionConstants.EDIT_PROJECT}||$projectId||$changes"
            is UserAction.EditTask -> "${UserActionConstants.EDIT_TASK}||$taskId||$changes"
            else -> "Unknown action type: $this"
        }
    }


    private fun String.parseUserAction(): UserAction {
        val actionParts = this.split("||")

        return when {
            this.startsWith(UserActionConstants.DELETE_PROJECT) -> {
                if (actionParts.size < 2) {
                    throw CsvParseException("Invalid DeleteProject format: $this")
                }
                try {
                    UserAction.DeleteProject(UUID.fromString(actionParts[1]))
                } catch (e: Exception) {
                    throw CsvParseException("Invalid UUID in DeleteProject: ${actionParts[1]}")
                }
            }

            this.startsWith(UserActionConstants.EDIT_PROJECT_TITLE) -> {
                if (actionParts.size < 3) {
                    throw CsvParseException("Invalid EditProjectTitle format: $this")
                }
                UserAction.EditProjectTitle(actionParts[1], actionParts[2])
            }

            this.startsWith(UserActionConstants.CREATE_PROJECT) -> {
                if (actionParts.size < 2) {
                    throw CsvParseException("Invalid CreateProject format: $this")
                }
                // Note: The implementation you provided expected 2 parameters but in parseToString there's only 1
                // Adapting to handle both formats:
                if (actionParts.size >= 3) {
                    try {
                        UserAction.CreateProject(actionParts[1], UUID.fromString(actionParts[2]))
                    } catch (e: Exception) {
                        throw CsvParseException("Invalid UUID in CreateProject: ${actionParts[2]}")
                    }
                } else {
                    UserAction.CreateProject(actionParts[1], UUID.randomUUID()) // Using random UUID as fallback
                }
            }

            this.startsWith(UserActionConstants.CREATE_TASK) -> {
                if (actionParts.size < 4) {
                    throw CsvParseException("Invalid CreateTask format: $this")
                }
                try {
                    UserAction.CreateTask(
                        actionParts[1],
                        UUID.fromString(actionParts[2]),
                        UUID.fromString(actionParts[3])
                    )
                } catch (e: Exception) {
                    throw CsvParseException("Invalid UUID in CreateTask: $this")
                }
            }

            this.startsWith(UserActionConstants.EDIT_PROJECT) -> {
                if (actionParts.size < 3) {
                    throw CsvParseException("Invalid EditProject format: $this")
                }
                try {
                    UserAction.EditProject(UUID.fromString(actionParts[1]), actionParts[2])
                } catch (e: Exception) {
                    throw CsvParseException("Invalid UUID in EditProject: ${actionParts[1]}")
                }
            }

            this.startsWith(UserActionConstants.EDIT_TASK) -> {
                if (actionParts.size < 3) {
                    throw CsvParseException("Invalid EditTask format: $this")
                }
                try {
                    UserAction.EditTask(UUID.fromString(actionParts[1]), actionParts[2])
                } catch (e: Exception) {
                    throw CsvParseException("Invalid UUID in EditTask: ${actionParts[1]}")
                }
            }

            else -> throw CsvParseException("Unknown action type: $this")
        }
    }
}