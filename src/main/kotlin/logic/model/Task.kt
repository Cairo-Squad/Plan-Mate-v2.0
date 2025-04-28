package logic.model

import java.util.UUID

data class Task(
    val id: UUID = UUID.randomUUID(),
    val title: String,
    val description: String,
    val state: State,
    val projectId: UUID
)
