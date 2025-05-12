package data.dto

import java.util.UUID

data class ProjectDto(
    val id: UUID?,
    val title: String,
    val description: String,
    val createdBy: UUID,
    val taskIds: List<UUID>,
    val stateId: UUID?
)
