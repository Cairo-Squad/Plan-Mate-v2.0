package data.dto

import java.util.UUID

data class ProjectDto(
    val id: UUID? = null,
    val title: String? = null,
    val description: String? = null,
    val tasks: List<UUID> = emptyList(),
    val stateId: UUID? = null
)
