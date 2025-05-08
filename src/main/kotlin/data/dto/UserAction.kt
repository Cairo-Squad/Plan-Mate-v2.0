package data.dto

import java.util.*

sealed class UserAction {
    abstract fun logMessage(): String

    data class DeleteProject(val projectId: UUID) : UserAction() {
        override fun logMessage() = "Project with ID $projectId deleted."
    }

    data class EditProjectTitle(val oldName: String, val newName: String) : UserAction() {
        override fun logMessage() = "Project title changed from '$oldName' to '$newName'."
    }

    data class CreateProject(val name: String, val projectId: UUID) : UserAction() {
        override fun logMessage() = "Project '$name' (ID: $projectId) created."
    }

    data class CreateTask(val taskName: String, val taskId: UUID, val projectId: UUID) : UserAction() {
        override fun logMessage() = "Task '$taskName' (ID: $taskId) created in project ID: $projectId."
    }

    data class EditProject(val projectId: UUID, val changes: String) : UserAction() {
        override fun logMessage() = "Project with ID $projectId edited. Changes: $changes."
    }

    data class EditTask(val taskId: UUID, val changes: String) : UserAction() {
        override fun logMessage() = "Task with ID $taskId updated. Changes: $changes."
    }

    data class EditEntity(
        val entityType: EntityType,
        val entityId: UUID,
        val entityTitle: String,
        val changedField: String,
        val previousValue: String,
        val newValue: String
    ) : UserAction() {
        override fun logMessage(): String =
            "User changed $entityType '$entityTitle' ($entityId) - $changedField: '$previousValue' → '$newValue'."
    }
}
