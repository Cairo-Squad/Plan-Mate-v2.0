package logic.model

import java.util.UUID

data class Task(
	val id: UUID? = null,
	val title: String? = null,
	val description: String? = null,
	val state: State? = null,
	val projectId: UUID? = null
)
