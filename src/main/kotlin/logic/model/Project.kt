package logic.model

import java.util.UUID

data class Project(
    val id: UUID = UUID.randomUUID(),
    val title: String? = null,
    val description: String? = null,
    val tasks: List<Task>? = null,
    val state: State? = null
)
