package data.util

import logic.model.UserAction

class LogMessage {

    fun UserAction.logMessage(): String = when (this) {
        is UserAction.EditProject -> "Project with ID $projectId edited. Changes: $changes."
        is UserAction.EditTask -> "Task with ID $taskId updated. Changes: $changes."
        else ->"Other action performed"
    }
}