package data.dto

import java.util.UUID

data class ProjectDto(
    var id: UUID?,
    val title: String,
    val description: String,
    val createdBy: UUID,
    val taskIds: List<UUID>,
    val stateId: UUID?
)
