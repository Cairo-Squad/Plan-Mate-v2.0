package logic.model

import java.util.UUID

data class Project(
    val id: UUID? = null,
    val title: String? = null,
    val description: String? = null,
    val createdBy: UUID? = null,
    val state: State? = null
)
