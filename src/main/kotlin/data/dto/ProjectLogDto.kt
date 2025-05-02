package data.dto

import java.time.LocalDateTime
import java.util.*

class ProjectLogDto (
    val projectId: UUID,
    val userId: UUID,
    val action: UserAction,
    val time: LocalDateTime = LocalDateTime.now()
)