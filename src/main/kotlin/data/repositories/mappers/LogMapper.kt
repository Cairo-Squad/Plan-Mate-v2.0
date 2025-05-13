package data.repositories.mappers

import data.dto.LogDto
import logic.model.Log

fun LogDto.toLog(): Log {
    return Log(
        id = this.id,
        entityId = this.entityId,
        entityTitle = this.entityTitle,
        entityType = this.entityType,
        dateTime = this.dateTime,
        userId = this.userId,
        userAction = this.userAction
    )
}

fun Log.toLogDto(): LogDto {
    return LogDto(
        id = this.id,
        entityId = this.entityId,
        entityTitle = this.entityTitle,
        entityType = this.entityType,
        dateTime = this.dateTime,
        userId = this.userId,
        userAction = this.userAction,
    )
}