package logic.model

import java.util.*

sealed class UserAction {
    data class CreateProject(val projectId: UUID, val changes: String) : UserAction()
    data class EditProject(val projectId: UUID, val changes: String) : UserAction()
    data class DeleteProject(val projectId: UUID, val changes: String) : UserAction()
    data class CreateTask(val taskId: UUID, val changes: String) : UserAction()
    data class EditTask(val taskId: UUID, val changes: String) : UserAction()
    data class DeleteTask(val taskId: UUID, val changes: String) : UserAction()
}

