package data.dto

import java.time.LocalDateTime
import java.util.UUID

data class LogEntityDto(
    val id: UUID? = null,
    val entityId: UUID? = null,
    val entityType: EntityType,
    val dataTime: LocalDateTime? = null,
    val userId: UUID? = null,
    val userAction: UserAction? = null
)
