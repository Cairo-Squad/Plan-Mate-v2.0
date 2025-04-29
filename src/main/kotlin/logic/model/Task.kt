package logic.model

import java.util.UUID

data class Task(
    val id: UUID,
    val title: String,
    val description: String,
    val state: State,
    val projectId: UUID
)
