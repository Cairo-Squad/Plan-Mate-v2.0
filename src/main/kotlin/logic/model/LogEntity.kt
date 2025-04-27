package logic.model

import data.dto.EntityType
import data.dto.UserAction
import java.time.LocalDateTime
import java.util.UUID

data class LogEntity(
    val id: UUID = UUID.randomUUID(),
    val entityId: UUID,
    val entityType: EntityType,
    val dataTime: LocalDateTime,
    val userId: UUID,
    val userAction: UserAction
)
