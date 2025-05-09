package data.dto

import java.util.*

sealed class UserAction {
    data class EditProject(val projectId: UUID, val changes: String) : UserAction()
    data class EditTask(val taskId: UUID, val changes: String) : UserAction()
}

