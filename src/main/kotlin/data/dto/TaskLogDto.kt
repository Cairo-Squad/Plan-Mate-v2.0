package data.dto

import java.time.LocalDateTime
import java.util.*

data class TaskLogDto(
    val taskId: UUID,
    val userId: UUID,
    val action: UserAction,
    val time: LocalDateTime = LocalDateTime.now()
)