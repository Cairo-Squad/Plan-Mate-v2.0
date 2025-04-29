package data.dto

import java.time.LocalDateTime
import java.util.UUID

data class LogDto(
    val id: UUID? = null,
    val entityId: UUID? = null,
    val entityTitle: String? = null,
    val entityType: EntityType,
    val dateTime: LocalDateTime? = null,
    val userId: UUID? = null,
    val userAction: UserAction? = null
)
