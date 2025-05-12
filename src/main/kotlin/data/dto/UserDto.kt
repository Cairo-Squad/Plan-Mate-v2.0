package data.dto

import java.util.UUID

data class UserDto(
    val id: UUID?,
    val name: String,
    val password: String,
    val type: UserType = UserType.MATE
)