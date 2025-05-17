package logic.model

import java.time.LocalDateTime
import java.util.UUID

data class Log(
    val id: UUID? = null,
    val entityId: UUID? = null,
    val entityTitle: String? = null,
    val entityType: EntityType? = null,
    val dateTime: LocalDateTime? = null,
    val userId: UUID? = null,
    val userAction: ActionType? = null
)