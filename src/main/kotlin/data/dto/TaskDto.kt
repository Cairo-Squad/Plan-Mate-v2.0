package data.dto

import java.util.UUID

data class TaskDto(
    var id: UUID?,
    val title: String,
    val description: String,
    val stateId: UUID,
    val projectId: UUID
)
