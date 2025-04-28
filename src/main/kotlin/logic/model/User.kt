package logic.model

import data.dto.UserType
import java.util.UUID

data class User(
    val id: UUID = UUID.randomUUID(),
    val name: String,
    val password: String,
    val type: UserType
)