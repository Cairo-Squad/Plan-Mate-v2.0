package logic.model

import data.dto.UserType
import java.util.UUID

data class User(
    val id: UUID,
    val name: String,
    val password: String,
    val type: UserType
)