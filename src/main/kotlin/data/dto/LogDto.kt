package data.dto

import java.time.LocalDateTime
import java.util.UUID

data class LogDto(
    val id: UUID,
    val entityId: UUID,
    val entityTitle: String,
    val entityType: EntityType,
    val dateTime: LocalDateTime,
    val userId: UUID,
    val userAction: UserAction
)
