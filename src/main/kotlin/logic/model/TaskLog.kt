package logic.model

import data.dto.UserAction
import java.time.LocalDateTime
import java.util.*

data class TaskLog(
    val taskId: UUID,
    val userId: UUID,
    val action: UserAction,
    val time: LocalDateTime = LocalDateTime.now()
)