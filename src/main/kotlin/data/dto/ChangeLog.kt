package data.dto

import java.util.UUID

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


