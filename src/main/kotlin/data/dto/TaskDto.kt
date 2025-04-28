package data.dto

import java.util.UUID

data class TaskDto(
    val id: UUID? = null,
    val title: String? = null,
    val description: String? = null,
    val stateId: UUID? = null,
    val projectId: UUID? = null
)
