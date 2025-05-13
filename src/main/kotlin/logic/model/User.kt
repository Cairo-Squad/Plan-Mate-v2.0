package logic.model

import java.util.UUID

data class User(
    val id: UUID ?=null,
    val name: String? = null,
    val password: String? = null,
    val type: UserType? = null
)