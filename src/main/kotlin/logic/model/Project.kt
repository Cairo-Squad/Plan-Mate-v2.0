package logic.model

import java.util.UUID

data class Project(
    val id: UUID = UUID.randomUUID(),
    val title: String,
    val description: String,
    val createdBy: UUID,
    val tasks: List<Task>,
    val state: State
)
