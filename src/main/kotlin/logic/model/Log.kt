package logic.model

import data.dto.EntityType
import data.dto.UserAction
import java.time.LocalDateTime
import java.util.UUID

data class Log(
    val id: UUID,
    val entityId: UUID,
    val entityTitle: String,
    val entityType: EntityType,
    val dateTime: LocalDateTime,
    val userId: UUID,
    val userAction: UserAction
)