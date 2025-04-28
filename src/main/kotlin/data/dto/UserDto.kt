package data.dto

import java.util.UUID

data class UserDto(
    val id: UUID? = null,
    val name: String? = null,
    val password: String? = null,
    val type: UserType = UserType.MATE
)